// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.step.finscholar.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseException;
import com.google.gson.Gson;
import com.google.step.finscholar.data.ServletConstantValues;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/** This class handles writing to and reading from a Cloud Firestore database. */
public class FirebaseStorageManager {
  /** This is used to convert Objects into JSON strings to send to the frontend. */
  private static Gson gson = new Gson();

  /** Formatters to use with String.format(). */
  private static final String ADDED_NEW_DOC_FORMATTER = "%s%s%s%s";
  private static final String EXCEPTION_DNE_FORMATTER = "%s%s%s";
  private static final String EXCEPTION_FORMATTER = "%s%s";

  /** Logger that sends logs to the Cloud Project console. */
  private static final Logger log = Logger.getLogger(FirebaseStorageManager.class.getName());

  /** 
   * This method stores an object as a DocumentReference (a data point) 
   *   inside a Collection (of DocumentReferences) in Firestore. 
   * @param database - This is the database I want to write my data point to.
   * @param collectionToWriteTo - This is the collection of Documents I want to write to.
   * @param object - The object that will be stored as a new Document in the collectionToWriteTo.
   *   It doesn't matter what type the object is as DocumentReference.set() is type-agnostic and will
   *   store any serializable object. This object is required to have a public, no-parameter constructor.
   *   Also, the instance variables for that object should not be declared as final. 
   *   There are no other requirements for the object.
   * @param documentID - The optional id for storing the document.
   */
  public static void storeDocument(Firestore database, String collectionToWriteTo, Object object, String documentID) throws FirebaseException {
    // Access the correct collection.
    CollectionReference collectionRef = database.collection(collectionToWriteTo);

    // Access/create the new document.
    DocumentReference documentRef;
    if (documentID.equals(ServletConstantValues.DEFAULT_VALUE)) {
      documentRef = collectionRef.document();
    } else {
      documentRef = collectionRef.document(documentID);
    }

    // Update the document with a new object.
    ApiFuture<WriteResult> future = documentRef.set(object);
    try {
      // Log that a new document has been added to database.
      String message = String.format(ADDED_NEW_DOC_FORMATTER, ServletConstantValues.NEW_DOCUMENT_ADDED, 
          collectionToWriteTo, ServletConstantValues.AT, future.get().getUpdateTime().toString());
      log.info(message);

    } catch (Exception e) {
      // Throws a FirebaseException if unsuccessful in adding new document.
      String message = String.format(EXCEPTION_FORMATTER, ServletConstantValues.UNABLE_TO_WRITE_TO_FIRESTORE , collectionToWriteTo);
      throw new FirebaseException(message, e);
    }
  }

  /**
   * Stores multiple objects to the Firestore database.
   * @param database
   * @param collectionToWriteTo
   * @param objects
   */
  public static void storeMultipleDocuments(Firestore database, String collectionToWriteTo, List<?> objects) throws FirebaseException {
    for (Object object : objects) {
      try {
        storeDocument(database, collectionToWriteTo, object, ServletConstantValues.DEFAULT_VALUE);
      } catch (Exception e) {
        throw new FirebaseException(ServletConstantValues.UNABLE_TO_WRITE_TO_FIRESTORE + collectionToWriteTo, e);
      }
    }
  }

  /**
   * This method retrieves a specified datapoint from the database based on which Collection 
   *    it is located in and a document ID. Then it converts the database's response to JSON.
   * @param database - The database I want to retrieve my data from.
   * @param collectionToGetFrom - The collection of documents I want to retrieve my data from.
   * @param documentID - The unique ID of the datapoint I want to retrieve.
   * @return - The JSON string representing the datapoint I just retrieved.
   */
  public static String getDocument(Firestore database, String collectionToGetFrom, String documentID) throws FirebaseException {
    // Retrieve a reference to the document representing the datapoint I want to retrieve.
    DocumentReference documentReference = database.collection(collectionToGetFrom).document(documentID);

    // Get a "Future" for the document, which will be used to generate a DocumentSnapshot of the data point.
    ApiFuture<DocumentSnapshot> snapshotFuture = documentReference.get();
    DocumentSnapshot document;
    try {
      // Retrieve a document snapshot of the data point.
      document = snapshotFuture.get();

      if (document.exists()) {
        // If the document snapshot exists, then convert the snapshot to a serializeable Object class.
        Object objectFromDatabase = document.toObject(Object.class);

        // Convert the object to JSON.
        return gson.toJson(objectFromDatabase);
      } else {
        // Throws a Firebase Exception if the document does not exist.
        String message = String.format(EXCEPTION_DNE_FORMATTER, ServletConstantValues.DOCUMENT, documentID, ServletConstantValues.DNE);
        throw new FirebaseException(message);
      }
    } catch (Exception e) {
      // Throws a FirebaseException if we can't connect to firestore.
      throw new FirebaseException(ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE, e);
    }
  }

  /**
   * This method retrieves an entire collection of firestore documents (data points).
   * @param database - The database to retrieve from.
   * @param collectionToGetFrom - The collection to retrieve from.
   * @return - The collection of objects converted as a json string.
   */
  public static String getCollection(Firestore database, String collectionToGetFrom) throws FirebaseException {
    // Retrieve a "future", which will generate a reference to the collection I want to retrieve.
    ApiFuture<QuerySnapshot> future = database.collection(collectionToGetFrom).get();

    try {
      // Now retrieve a snapshot of all documents in the collection.
      List<QueryDocumentSnapshot> documents = future.get().getDocuments();

      // Convert the documents to objects.
      List<Object> objects = new ArrayList<Object>();
      for (QueryDocumentSnapshot document : documents) {
        objects.add(document.toObject(Object.class));
      }

      // Convert the objects to JSON.
      return gson.toJson(objects);
    } catch (Exception e) {
       // Throws a FirebaseException if we can't connect to firestore.
       throw new FirebaseException(ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE, e);
    }
  }

  /**
   * Adds support for infinite scroll by batching queries into pages of a limited size.
   * Either we send in the first requested batch, or we send the next batch after one has already been sent.
   * @param database - The database to retrieve from.
   * @param collectionToGetFrom - The name of the collection to retrieve the batch from.
   * @param batchSizeLimit - The maximum size of the batch, which is the size of a new page of data in infinite scroll.
   * @param lastDocID - The ID of the last object I sent from the most recent batch.
   * @param parameterToSortBy - The parameter I want to presort my collection by.
   * @return - The json string representing the new batch of documents to be sent to the frontend.
   * @throws FirebaseException
   */
  public static String getCollectionBatch(Firestore database, String collectionToGetFrom, int batchSizeLimit,  String lastDocID, String parameterToSortBy) throws FirebaseException {
    CollectionReference collectionReference = database.collection(collectionToGetFrom);

    // If the lastDocID is DEFAULT_VALUE (""), then we know this is the first batch to send.
    // Else simply get the next batch in the collection.
    if (lastDocID.equals(ServletConstantValues.DEFAULT_VALUE)) {
      return getFirstBatch(collectionReference, batchSizeLimit, parameterToSortBy);
    } else {
      return getNextBatch(database, collectionReference, batchSizeLimit, lastDocID, parameterToSortBy);
    }
  }


  /**
   * Helper method for getCollectionBatch().
   * Retrieve the first batch in the collection collectionReference of size batchSizeLimit.
   * @param collectionReference - A reference to the collection I want to retrieve from.
   * @param batchSizeLimit - The maximum size of the batch, which is the size of a new page of data in infinite scroll.
   * @param parameterToSortBy - The parameter to sort the list of documents by.
   * @return - The json string representing the first batch in a request for all of the documents in a collection.
   * @throws FirebaseException
   */
  public static String getFirstBatch(CollectionReference collectionReference, int batchSizeLimit, String parameterToSortBy) throws FirebaseException {
    // We have the option here to sort the collection by specific parameter beforehand (great for supporting sort-type queries later on).
    // Setup the new Query.
    Query page = (parameterToSortBy.equals(ServletConstantValues.DEFAULT_VALUE) ? collectionReference.limit(batchSizeLimit) : collectionReference.orderBy(parameterToSortBy).limit(batchSizeLimit));
    return getCollectionQuery(page);
  }


  /**
   * Helper method for getCollectionBatch().
   * Retrieve the next batch in the collection collectionReference of size batchSizeLimit.
   * @param database - The database to retrieve from.
   * @param collectionReference - A reference to the collection I want to retrieve from.
   * @param batchSizeLimit - The maximum size of the batch, which is the size of a new page of data in infinite scroll.
   * @param lastDocID - The ID of the last object I sent from the most recent batch.
   * @param parameterToSortBy - The parameter to sort the list of documents by.
   * @return - The json string representing the next batch in a request for all of the documents in a collection.
   * @throws FirebaseException
   */
  public static String getNextBatch(Firestore database, CollectionReference collectionReference, int batchSizeLimit, String lastDocID, String parameterToSortBy) throws FirebaseException {
    // First retrieve the last document I sent.
    DocumentReference documentReference = collectionReference.document(lastDocID);

    // Get a "Future" for the document, which will be used to generate a DocumentSnapshot of the data point.
    ApiFuture<DocumentSnapshot> snapshotFuture = documentReference.get();
    DocumentSnapshot document;
    try {
      // Retrieve a document snapshot of the last data point.
      document = snapshotFuture.get();
    } catch (Exception e) {
      // Throws a FirebaseException if we can't connect to firestore.
      throw new FirebaseException(ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE, e);
    }

    // If the last document I sent still exists, then retrieve a batch of size=batchSizeLimit documents 
    //   that occur after the last doc in the collection.
    if (document.exists()) {
      Query page = (parameterToSortBy.equals(ServletConstantValues.DEFAULT_VALUE) ? collectionReference.limit(batchSizeLimit) : collectionReference.orderBy(parameterToSortBy).limit(batchSizeLimit)).startAfter(document);
      return getCollectionQuery(page);

    } else {
      // Throws a Firebase Exception if the document does not exist.
      String message = ServletConstantValues.DOCUMENT + lastDocID + ServletConstantValues.DNE;
      throw new FirebaseException(message);
    }
  }

  /**
   * Helper method: can be reused for any Query that needs to be made to a collection as long as the Query is constructed beforehand.
   * This method retrieves a query from a collection.
   * @param page - The query to retrieve.
   * @return - The collection of objects converted as a json string.
   */
  public static String getCollectionQuery(Query page) throws FirebaseException {
    // Retrieve a "future", which will generate a reference to the query results I want to retrieve.
    ApiFuture<QuerySnapshot> future = page.get();
    try {
      // Now retrieve a snapshot of all documents in the collection query.
      List<QueryDocumentSnapshot> documents = future.get().getDocuments();
      // Convert the objects to JSON.
      return convertSnapshotListToJson(documents);
    } catch (Exception e) {
       // Throws a FirebaseException if we can't connect to firestore.
       throw new FirebaseException(ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE, e);
    }
  }

  /**
   * Helper method: Converts any Lists of QueryDocumentSnapshot objects to a json string.
   * @param documents - The documents to convert to json.
   * @return - The json representing multiple documents.
   */
  public static String convertSnapshotListToJson(List<QueryDocumentSnapshot> documents) {
    // Convert the documents to objects.
    List<Object> objects = new ArrayList<Object>();
    for (QueryDocumentSnapshot document : documents) {
      objects.add(document.toObject(Object.class));
    }
    // Convert the objects to JSON.
    return gson.toJson(objects);
  }

  /**
   * This method retrieves the size of a collection. Used for batching.
   * @param database - The database to check.
   * @param collectionToGetFrom - The collection to check.
   * @return - The number of entries in the collection.
   * @throws FirebaseException
   */
  public static int getCollectionSize(Firestore database, String collectionToGetFrom) throws FirebaseException {
    // Retrieve a "future", which will generate a reference to the collection I want to retrieve.
    ApiFuture<QuerySnapshot> future = database.collection(collectionToGetFrom).get();

    try {
      // Now retrieve the size of the collection.
      return future.get().size();
    } catch (Exception e) {
      throw new FirebaseException(ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE, e);
    }
  }
}
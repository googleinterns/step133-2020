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
  private static final String ADDED_NEW_DOC_FORMATTER = "New document added to %s at %s.";
  private static final String EXCEPTION_DNE_FORMATTER = "Document with id: %s does not exist.";
  private static final String EXCEPTION_COLLECTION_FORMATTER = "Unable to write to this collection: %s.";

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
  public static void storeDocument(Firestore database, String collectionToWriteTo, Object object, String documentID) 
    throws FirebaseException {
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
      String message = String.format(ADDED_NEW_DOC_FORMATTER, 
          collectionToWriteTo, future.get().getUpdateTime().toString());
      log.info(message);

    } catch (Exception e) {
      // Throws a FirebaseException if unsuccessful in adding new document.
      String message = String.format(EXCEPTION_COLLECTION_FORMATTER, collectionToWriteTo);
      throw new FirebaseException(message, e);
    }
  }

  /**
   * Stores multiple objects to the Firestore database.
   * @param database
   * @param collectionToWriteTo
   * @param objects
   */
  public static void storeMultipleDocuments(Firestore database, String collectionToWriteTo, List<?> objects) 
    throws FirebaseException {
    for (Object object : objects) {
      try {
        storeDocument(database, collectionToWriteTo, object, ServletConstantValues.DEFAULT_VALUE);
      } catch (Exception e) {
        String message = String.format(EXCEPTION_COLLECTION_FORMATTER, collectionToWriteTo);
        throw new FirebaseException(message, e);
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
  public static String getDocument(Firestore database, String collectionToGetFrom, String documentID) 
    throws FirebaseException {
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
        String message = String.format(EXCEPTION_DNE_FORMATTER, documentID);
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
} 
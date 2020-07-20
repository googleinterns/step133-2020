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

import javax.servlet.Servlet;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseException;
import com.google.gson.Gson;
import com.google.step.finscholar.data.ServletConstantValues;


/** This class handles writing to and reading from a Cloud Firestore database. */
public class FirebaseStorageManager {
  /** This is used to convert Objects into JSON strings to send to the frontend. */
  private static Gson gson = new Gson();
  private static final String ADDED_NEW_DOC_FORMATTER = "%s %s %s %t";
  
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
      // Print to console which Collection I added to and when.
      String message = String.format(ADDED_NEW_DOC_FORMATTER, ServletConstantValues.NEW_DOCUMENT_ADDED + 
      collectionToWriteTo + ServletConstantValues.AT + future.get().getUpdateTime());
      
    } catch (Exception e) {
      // Throws a FirebaseException if unsuccessful in adding new document.
      String message = ServletConstantValues.UNABLE_TO_WRITE_TO_FIRESTORE + collectionToWriteTo;
      throw new FirebaseException(message, e);
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
        String message = ServletConstantValues.DOCUMENT + documentID + ServletConstantValues.DNE;
        throw new FirebaseException(message);
      }
    } catch (Exception e) {
      // Throws a FirebaseException if we can't connect to firestore.
      throw new FirebaseException(ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE, e);
    }
  }
}
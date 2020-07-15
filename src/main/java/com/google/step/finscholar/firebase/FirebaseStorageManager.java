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
import com.google.cloud.firestore.WriteResult;
import com.google.gson.Gson;

public class FirebaseStorageManager {
  private static Gson gson = new Gson();
  
  public static void storeDocument(Firestore database, String collectionToWriteTo, Object object) {
    // Access the correct collection.
    CollectionReference collectionRef = database.collection(collectionToWriteTo);
    
    // Create a new document in the collection.
    DocumentReference documentRef = collectionRef.document();

    // Update the document with a new object.
    ApiFuture<WriteResult> result = documentRef.set(object);

    // Print to console whenever the document has been successfully added to Firestore.
    try {
      System.out.println("Update time : " + result.get().getUpdateTime());
    } catch(Exception e) {
      System.out.printf("An error adding a new document to Firestore occurred: %s.", e);
    }
  }

  public static String getDocument(Firestore database, String collectionToGetFrom, String documentID, Class<?> objectClass) {
    
    DocumentReference documentReference = database.collection(collectionToGetFrom).document(documentID);
    ApiFuture<DocumentSnapshot> snapshotFuture = documentReference.get();
    DocumentSnapshot document;
    try {
      document = snapshotFuture.get();
      if(document.exists()) {
        System.out.println("Document exists");
        Object objectFromDatabase = document.toObject(objectClass);
        String json = gson.toJson(objectFromDatabase);
        return json;
      } else {
        System.out.println("Document does not exist.");
      }
    } catch (Exception e) {
      // Do something with the exception.
      System.out.println("Error: " + e.toString());
    }
    return "";
  }
}
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

package com.google.step.finscholar.servlets;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.step.finscholar.data.College;
import com.google.step.finscholar.data.CollegeData;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.firebase.FirebaseAppManager;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns colleges and their associated data. */
@WebServlet("/college-data")
public class CollegeServlet extends HttpServlet {
  private Firestore database;
  private Gson gson;

  @Override
  public void init(ServletConfig config) throws ServletException {
    try {
      database = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
      gson = new Gson();
    } catch (IOException e) {
      throw new ServletException(e);
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Access the correct collection.
    CollectionReference collectionRef = database.collection(ServletConstantValues.COLLEGE_COLLECTION_NAME);
    
    // Create a new document in the collection.
    DocumentReference documentRef = collectionRef.document();

    // Update the document with a new object.
    ApiFuture<WriteResult> result = documentRef.set(CollegeData.COLLEGE);

    // Get the document.

    DocumentReference dukeReference = database.collection(ServletConstantValues.COLLEGE_COLLECTION_NAME).document("Duke");
    ApiFuture<DocumentSnapshot> snapshotFuture = dukeReference.get();
    DocumentSnapshot document;
    College collegeFromDatabase = null;
    try {
      document = snapshotFuture.get();
      if(document.exists()) {
        System.out.println("Document exists");
        collegeFromDatabase = document.toObject(College.class);
      } else {
        System.out.println("Document does not exist.");
      }
    } catch (Exception e) {
      // Do something with the exception.
      System.out.println("Error: " + e.toString());
    }
    

    // Convert the college to JSON.
    College college = CollegeData.COLLEGE;
    String json = gson.toJson(collegeFromDatabase);
    
    // Send the list of colleges as the response.
    response.setContentType(ServletConstantValues.JSON_CONTENT_TYPE);
    response.getWriter().println(json);
  }
}

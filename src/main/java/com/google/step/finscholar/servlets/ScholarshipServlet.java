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

import static com.google.step.finscholar.data.ServletConstantValues.DEFAULT_VALUE;
import static com.google.step.finscholar.data.ServletConstantValues.JSON_CONTENT_TYPE;
import static com.google.step.finscholar.data.ServletConstantValues.SCHOLARSHIP_COLLECTION_NAME;
import static com.google.step.finscholar.data.ServletConstantValues.UNABLE_TO_LOAD_FIREBASE;
import static com.google.step.finscholar.data.ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE;
import static com.google.step.finscholar.data.Utils.getStringParameter;
import static com.google.step.finscholar.firebase.FirebaseStorageManager.getDocument;

import com.google.step.finscholar.firebase.FirebaseAppManager;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseException;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Optional;

/** The servlet handling all requests related to scholarships. */
@WebServlet("/scholarship-data")
public class ScholarshipServlet extends HttpServlet {

  String ID = "id";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Firestore database = null;
    Optional<String> id = getStringParameter(request, ID);
    
    database = FirestoreClient.getFirestore(FirebaseAppManager.getApp());

    if (database != null) {
      response.setContentType(JSON_CONTENT_TYPE);
      try {
        response.getWriter().println(getDocument(database, SCHOLARSHIP_COLLECTION_NAME, id));
      } catch(FirebaseException e) {
        response.sendError(HttpServletResponse.SC_NO_CONTENT, UNABLE_TO_READ_FROM_FIRESTORE + e);  
      }
    } else {
      response.sendError(HttpServletResponse.SC_BAD_GATEWAY, UNABLE_TO_LOAD_FIRESTORE + e);
    }
  }
}
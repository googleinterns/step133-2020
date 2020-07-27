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
import static com.google.step.finscholar.data.ServletConstantValues.INVALID_INT_PARAMETER;
import static com.google.step.finscholar.data.ServletConstantValues.JSON_CONTENT_TYPE;
import static com.google.step.finscholar.data.ServletConstantValues.SCHOLARSHIP_COLLECTION_NAME;
import static com.google.step.finscholar.data.ServletConstantValues.UNABLE_TO_LOAD_FIREBASE;
import static com.google.step.finscholar.data.ServletConstantValues.UNABLE_TO_READ_FROM_FIRESTORE;

import static com.google.step.finscholar.data.Utils.getIntParameter;
import static com.google.step.finscholar.data.Utils.getStringParameter;
import static com.google.step.finscholar.firebase.FirebaseStorageManager.getCollectionBatch;

import com.google.step.finscholar.firebase.FirebaseAppManager;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseException;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;
import java.lang.NumberFormatException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@WebServlet("/scholarship-list")
public class ScholarshipListServlet extends HttpServlet {

  private static String ITEMS_PER_BATCH = "numberOfItems";
  private static String ID_OF_LAST_ITEM = "idOfLastItem";
  private static String SORT_BY = "sortBy";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Firestore database = null;
    Optional<Integer> itemsPerBatch = Optional.empty();
    try {
      database = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
      itemsPerBatch = getIntParameter(request, ITEMS_PER_BATCH);
    } catch (NumberFormatException numberException) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
          INVALID_INT_PARAMETER + numberException);
    }

    if (database != null) {
      try {
        Optional<String> idOfLastItem = getStringParameter(request, ID_OF_LAST_ITEM);
        Optional<String> sortBy = getStringParameter(request, SORT_BY);
        response.setContentType(JSON_CONTENT_TYPE);
        response.getWriter().println(
            getCollectionBatch(database, SCHOLARSHIP_COLLECTION_NAME, itemsPerBatch, idOfLastItem, sortBy));
      } catch (FirebaseException firebaseException) {
        response.sendError(HttpServletResponse.NO_CONTENT, 
            UNABLE_TO_READ_FROM_FIRESTORE + firebaseException);
      } 
    } else {
      response.sendError(HttpServletResponse.SC_BAD_GATEWAY, 
          UNABLE_TO_LOAD_FIRESTORE + firebaseException);
    }
  }
} 
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

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.step.finscholar.data.College;
import com.google.step.finscholar.data.CollegeData;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.firebase.FirebaseAppManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns colleges and their associated data. */
@WebServlet("/college-data")
public class CollegeServlet extends HttpServlet {
  private FirebaseDatabase database;
  private Gson gson;

  @Override
  public void init(ServletConfig config) throws ServletException {
    try {
      database = FirebaseDatabase.getInstance(FirebaseAppManager.getApp());
      gson = new Gson();
    } catch (IOException e) {
      throw new ServletException(e);
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Convert the college to JSON.
    College college = CollegeData.COLLEGE;
    String json = gson.toJson(college);
    
    // Send the list of colleges as the response.
    response.setContentType(ServletConstantValues.JSON_CONTENT_TYPE);
    response.getWriter().println(json);
  }
}

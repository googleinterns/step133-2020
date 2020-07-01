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

import com.google.gson.Gson;
import com.google.step.finscholar.data.College;
import com.google.step.finscholar.data.CollegeData;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** Servlet that returns colleges and their associated data. */
@WebServlet("/college-data")
public class CollegeServlet extends HttpServlet {

  public static final String JSON_CONTENT_TYPE = "application/json;";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    List<College> listOfColleges = CollegeData.COLLEGES;
    String json = convertToJson(listOfColleges);
    
    // Send the list of comments as the response.
    response.setContentType(JSON_CONTENT_TYPE);
    response.getWriter().println(json);
  }

  /**
   * Converts an List<College> to a json string.
   * @param listOfColleges - List<College> that needs to be converted.
   * @return - Newly converted JSON string.
   */
  private String convertToJson(List<College> listOfColleges) {
    Gson gson = new Gson();
    String json = gson.toJson(listOfColleges);
    return json;
  }
}

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

import static com.google.step.finscholar.data.ServletConstantValues.JSON_CONTENT_TYPE;	

import com.google.gson.Gson;	
import com.google.step.finscholar.data.Scholarship;	
import com.google.step.finscholar.data.ScholarshipSamples;	
import java.io.IOException;	
import java.util.List;	
import javax.servlet.annotation.WebServlet;	
import javax.servlet.http.HttpServlet;	
import javax.servlet.http.HttpServletRequest;	
import javax.servlet.http.HttpServletResponse;	

/** The servlet handling all requests related to scholarships. */	
@WebServlet("/scholarship-data")	
public class ScholarshipServlet extends HttpServlet {	

  private final Gson gson = new Gson();	

  @Override	
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {	

    List<Scholarship> scholarships = List.of(ScholarshipSamples.scholarshipSampleOne,	
                                             ScholarshipSamples.scholarshipSampleTwo);	
    response.setContentType(JSON_CONTENT_TYPE);	
    response.getWriter().println(gson.toJson(scholarships));	

  }	
} 
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

package com.google.step.finscholar.data;

import javax.servlet.http.HttpServletRequest;

public class Utils {

  /**
   * @param request       The current HTTP request being handled
   * @param name          The parameter name
   * @param defaultValue  The default string if the field does not exist in request
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client.
   */
  public static String getStringParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);

    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  /**
   * @param request       The current HTTP request being handled
   * @param name          The parameter name
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client.
   */
  public static int getIntParameter(HttpServletRequest request, String name) {
    String value = request.getParameter(name);
    int parsedValue = 0;
    try {
      parsedValue = Integer.parseInt(value);
    } catch(Exception e) {
      throw(e);
    }
    return parsedValue;
  }
}
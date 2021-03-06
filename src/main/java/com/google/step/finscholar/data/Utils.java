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

import java.lang.NumberFormatException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/** The class containing all commonly used helper functions. */
public class Utils {

  /**
   * @param request       The current HTTP request being handled.
   * @param name          The parameter name.
   * @return the request parameter in Optional, or Optional.empty if the parameter
   *         was not specified by the client.
   */
  public static Optional<String> 
      getStringParameter(HttpServletRequest request, String name) {
    Optional<String> paramValue = Optional.ofNullable(request.getParameter(name));
    return paramValue.isPresent() 
                     ? paramValue.get().isEmpty() 
                         ? Optional.empty()
                         : paramValue
                      : paramValue;
  }

  /**
   * @param request       The current HTTP request being handled.
   * @param name          The parameter name.
   * @return the request parameter in Optional, or the default value if the parameter
   *         was not specified by the client.
   */
  public static Optional<Integer> getIntParameter(HttpServletRequest request, String name) 
      throws NumberFormatException {
    Optional<String> paramValue = Optional.ofNullable(request.getParameter(name));
    return paramValue.isPresent() 
                     ? paramValue.get().isEmpty() 
                         ? Optional.empty() 
                         : Optional.ofNullable(Integer.parseInt(paramValue.get()))
                      : Optional.empty();
  }

  /**
   * @param request       The current HTTP request being handled.
   * @param name          The parameter name.
   * @return the request parameter in Optional, or Optional.empty() if the parameter
   *         was not specified by the client.
   */
  public static Optional<Boolean> getBooleanParameter(HttpServletRequest request, String name) {
    Optional<String> paramValue = Optional.ofNullable(request.getParameter(name));
    // If the parameter exists but the value is empty string, return optional.empty().
    return paramValue.isPresent()
                    ? paramValue.get().isEmpty()
                        ? Optional.empty()
                        : Optional.ofNullable(Boolean.parseBoolean(paramValue.get()))
                    : Optional.empty();
  }
} 
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

/** The enumorator for the smain ethnic groups in the U.S.. */ 
public enum Ethnicity {

  HISPANIC("Hispanic"),
  WHITE("White"),
  BLACK_OR_AFRICAN_AMERICAN("Black or African American"),
  AMERICAN_INDIAN("American Indian"),
  ALASKA_NATIVE ("Alaska Native"),
  ASIAN("Asian"),
  PACIFIC_ISLANDER("Native Hawaiian or Other Pacific Islander");

  private String ethnicity;

  private Ethnicity(String ethnicity) {
    this.ethnicity = ethnicity;
  }

  /** @return The string associated with this constant. */
  public String getValue() {
    return this.ethnicity;
  }
    
}
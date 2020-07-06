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

/** The enumerator for genders. */
public enum Gender {
  
    MALE("male"),
    FEMALE("female"),
    TRANSGENDER("transgender"),
    NON_BINARY("non-binary"),
    GENDER_NEUTRAL("gender neutral");

    private String gender;

    private Gender(String gender) {
      this.gender = gender;
    }

    public String getValue() {
      return this.gender;
    }

}
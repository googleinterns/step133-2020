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

public class TestObject {

  private String one;
  private String two;

  public TestObject() {

  }

  public TestObject(String one, String two) {
    this.one = one;
    this.two = two;
  }

  public void setOne(String newOne) {
    this.one = newOne;
  }

  public void setTwo(String newTwo) {
    this.two = newTwo;
  }

  public String getOne() {
    return this.one;
  }

  public String getTwo() {
    return this.two;
  }
  
}
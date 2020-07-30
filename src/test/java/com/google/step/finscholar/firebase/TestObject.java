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


package com.google.step.finscholar.firebase;

import com.google.gson.Gson;

/** 
 * This simple object is used in testing our Firebase methods' behaviour. 
 * All objects stored in Firestore must be a POJO (Plain Old Java Object). This means they must have
 *   private non-final instance variables. An empty no-parameter constructor,
 *   a non-empty constructor, and setters and getters for each instance
 *   variable. As long as an object is a POJO, the functionality applies to every
 *   other POJO object the same way. This also applies to POJO objects that are instantiated 
 *   using the builder pattern.
 */
public class TestObject {

  private String one;
  private String two;
  private static Gson gson = new Gson();

  public TestObject() {}

  /**
   * @param one - An arbitrary string for the first private instance variable.
   * @param two - An arbitrary string for the second private instance variable.
   */
  public TestObject(String one, String two) {
    this.one = one;
    this.two = two;
  }

  /**
   * The required setter for "one" to make this object into a POJO.
   * @param newOne
   * @return - This object.
   */
  public TestObject setOne(String newOne) {
    this.one = newOne;
    return this;
  }

  /**
   * The required setter for "two" to make this object into a POJO.
   * @param newOne
   * @return This object.
   */
  public TestObject setTwo(String newTwo) {
    this.two = newTwo;
    return this;
  }

  /**
   * The required getter for "one" to make this object into a POJO.
   * @return - The value of the "one" private instance variable.
   */
  public String getOne() {
    return this.one;
  }

  /**
   * The required getter for "two" to make this object into a POJO.
   * @return - The value of the "two" private instance variable.
   */
  public String getTwo() {
    return this.two;
  }

  @Override
  public String toString() {
    return gson.toJson(this);
  }
} 
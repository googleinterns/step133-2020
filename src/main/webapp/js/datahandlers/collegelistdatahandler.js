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

/** 
 * @fileoverview The data controller which fetches college data 
 * from backend and reformats the data. 
 */

goog.module('datahandlers.collegelistdatahandler');

const {COLLEGE_API_KEY} = goog.require('datahandlers.config');
const {CollegeQueryBuilder} = google.require('datahandlers.collegequerybuilder');
const {NAME, ACCEPTANCE_RATE, ACT_SCORE, ID} = 
  goog.require('datahandlers.collegequerybuilder');
/**
 * The data controller which fetches college data 
 * from backend and reformats the data. 
 */
class CollegeListDataHandler {

  constructor() {
  }

  /**
   * This converts a json string to a js object map.
   * @param {*} json - The json representing a list of colleges.
   * @return - The list of js object maps.
   * @private
   */
  convertJsonToObject_(json) {
    let results = json["results"];
    return results.map(element => this.converter_(element));
  }

  /**
   * Convert json string for single college to js object map.
   * @param {*} element - The json string.
   * @return {*} - JS object map representing a single college.
   */
  converter_(element) {
    return {
        name : element[NAME],
        acceptance : element[ACCEPTANCE_RATE].toString(),
        act : element[ACT_SCORE].toString(),
        id : element[ID].toString()
    };
  }

  /**
   * Fetch a batch of college object from DoE API.
   * @param {number} batchIndex - Page number to extract.
   * @param {number} itemsPerBatch - The number of objects requested.
   * @param {number=} lastIndex - Unused parameter that ensures the method.
   *   headers for getNextBatch() stay consistent across all listdatahandlers.
   */
  async getNextBatch(batchIndex, itemsPerBatch, lastIndex) {
    console.log('Batch to load: ' + batchIndex);
    const url = CollegeQueryBuilder.buildCollectionEndpoint(batchIndex, itemsPerBatch);
    const response = await fetch(url);
    const json = await response.json();
    return this.convertJsonToObject_(json);
  }
}

exports = {CollegeListDataHandler}
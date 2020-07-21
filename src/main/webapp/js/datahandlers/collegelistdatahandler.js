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

const COLLEGE_LIST_ENDPT = 
  'https://api.data.gov/ed/collegescorecard/v1/schools.json?';
const AND = "&";
const COMMA = ',';
const EQUAL = '=';
const QUERY_FIELDS = '_fields=';
const COLLEGES = 'school.degrees_awarded.predominant=2,3';
const ID = 'id';
const NAME =  'school.name'
const ACCEPTANCE_RATE = 'admissions.admission_rate.overall';
const ACT_SCORE = 'admissions.act_scores.midpoint.cumulative';

/**
 * The data controller which fetches college data 
 * from backend and reformats the data. 
 */
class CollegeListDataHandler {

  constructor() {}

  buildURL() {

  }

  /**
   * Fetch a batch of college object from DoE API.
   * @param {number} batchIndex The index of the batch to be fetched.
   * @param {number} itemsPerBatch The number of objects requested.
   */
  async getNextBatch(batchIndex, itemsPerBatch) {

  }
}

exports = {CollegeListDataHandler}
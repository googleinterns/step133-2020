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

const COLLEGE_LIST_ENDPT = 
  'https://api.data.gov/ed/collegescorecard/v1/schools.json?';
const AND = '&';
const COMMA = ',';
const PAGE_SIZE_FIELD = 'per_page';
const PAGE = 'page';
const EQUAL = '=';
const NOT = '__not';
const API_KEY_FIELD = 'api_key=';
const NULL = 'null';
const QUERY_FIELDS = '_fields=';
const COLLEGES = 'school.degrees_awarded.predominant=2,3';
const ID = 'id';
const NAME =  'school.name'
const ACCEPTANCE_RATE = '2018.admissions.admission_rate.overall';
const ACT_SCORE = '2018.admissions.act_scores.midpoint.cumulative';
const ACCEPTANCE_RANGE = '__range=0..0.50';
const ACT_RANGE = '__range=25..36';

/**
 * The data controller which fetches college data 
 * from backend and reformats the data. 
 */
class CollegeListDataHandler {

  constructor() {
    /** The current page to retrieve from */
    this._currentPage = 0;
  }

  /**
   * Build a URL to fetch from the College Scorecard API.
   * @param {number} itemsPerBatch - The number of items to query per batch.
   * @return {string} - The URL to query from.
   * @private
   */
  buildURL_(itemsPerBatch) {
    return COLLEGE_LIST_ENDPT.concat(COLLEGES, AND, QUERY_FIELDS, ID, COMMA, NAME, COMMA, 
      ACCEPTANCE_RATE, COMMA, ACT_SCORE, AND, PAGE_SIZE_FIELD, EQUAL, 
      itemsPerBatch.toString(), AND, PAGE, EQUAL, this._currentPage.toString(), 
      AND, ACCEPTANCE_RATE, ACCEPTANCE_RANGE, AND, ACT_SCORE, ACT_RANGE, AND, 
      API_KEY_FIELD, COLLEGE_API_KEY);
  }
  /**
   * This converts a json string to a js object map.
   * @param {*} json - The json representing a list of colleges.
   * @return - The list of js object maps.
   * @private
   */
  convertJsonToObject_(json) {
    const collegeList = [];
    const results = json["results"];

    results.forEach(element => {
      collegeList.push({
        'name' : element[NAME],
        'acceptance' : element[ACCEPTANCE_RATE].toString(),
        'act' : element[ACT_SCORE].toString(),
        'id' : element[ID].toString()
      });
    });
    console.log(collegeList);
    return collegeList;
  }

  /**
   * Fetch a batch of college object from DoE API.
   * @param {number=} batchIndex - Unused parameter that ensures the method
   *   headers for getNextBatch() stay consistent across all listdatahandlers.
   * @param {*} itemsPerBatch - The number of objects requested.
   * @param {number=} lastIndex - Unused parameter that ensures the method
   *   headers for getNextBatch() stay consistent across all listdatahandlers.
   */
  async getNextBatch(batchIndex, itemsPerBatch, lastIndex) {
    const url = this.buildURL_(itemsPerBatch);
    const response = await fetch(url);
    const json = await response.json();
    this._currentPage++;
    console.log(json);
    return this.convertJsonToObject_(json);
  }
}

exports = {CollegeListDataHandler}
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

const {CollegeQueryBuilder} = goog.require('datahandlers.collegequerybuilder');
const {ListDataHandler} = goog.require('datahandlers.listdatahandler');
const {NAME, ACCEPTANCE_RATE, ANNUAL_COST, ID} = 
  goog.require('datahandlers.collegequerybuilder');
const {integerWithCommas} = goog.require('datahandlers.utils');
const DOLLAR_SIGN = '$';

/**
 * The data controller which fetches college data 
 * from backend and reformats the data. 
 */
class CollegeListDataHandler extends ListDataHandler {

  constructor() {
    super()
  }

  /** 
   * @returns {Promise<number>} 
   * The total number of scholarship stored in backend. 
   */
  async getTotalNumber() {
    return 250;
  }

  /**
   * Converts list items from Json to objects.
   * @param {Object} element The college or scholarhsip list item data.
   * @returns {!Array<string>}
   */
  formatListItem_(element){
    return [
      element[ID].toString(),
      element[NAME],
      element[ACCEPTANCE_RATE].toString(),
      DOLLAR_SIGN.concat(integerWithCommas(element[ANNUAL_COST])),
    ];
  }

  /**
   * @param {number} batchIndex The index of last batch rendered.
   * @param {number} itemsPerBatch Number of items requested.
   * @param {string} lastIndex Index of the last item in the list.
   * @return The url with query information.
   */
  getPath_(batchIndex, itemsPerBatch, lastIndex) {
    return CollegeQueryBuilder.buildCollectionEndpoint(batchIndex, itemsPerBatch);
  }
}

exports = {CollegeListDataHandler}
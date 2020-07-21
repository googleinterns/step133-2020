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
 * @fileoverview The data controller which fetches scholarship data
 * from backend and reformats the data.
 */

goog.module('datahandlers.scholarshiplistdatahandler');

const AND = '&';
const AMOUNT_PER_YEAR = 'amountPerYear';
const EMPTY_STRING = '';
const END_OF_STRING = 23;
const EQUALS = '=';
const ID = 'id';
const INDEX_OF_LAST_ITEM = 'indexOfLastItem';
const NUMBER_OF_ITEMS = 'numberOfItems';
const QUERY_START = '?';
const SCHOLARSHIP_LIST_ENDPT = '/scholarship-list';
const SCHOLARSHIP_NAME = 'scholarshipName';
const UNKNOWN = 'Unknown';

/**
 * The data controller which fetches scholarship data
 * from backend and reformats the data.
 */
class ScholarshipListDataHandler {
  constructor() {}

  /** @returns The total number of scholarship stored in backend. */
  async getTotalNumber() {
    return 15; // Presetting page length not supported yet.
  }

  /**
   * @param {number} itemsPerBatch NUmber of items requested.
   * @param {string} lastIndex Index of the last item in the list.
   */
  async getNextBatch(itemsPerBatch, lastIndex) {
    let scholarshipList = [];
    const queryString = QUERY_START + NUMBER_OF_ITEMS + EQUALS + itemsPerBatch + AND
        + INDEX_OF_LAST_ITEM + EQUALS + lastIndex;
    try {
      let responseList = await fetch(SCHOLARSHIP_LIST_ENDPT + queryString);
      scholarshipList = await responseList.json();
      scholarshipList = scholarshipList.map(e => this.formatListItem(e));
    } catch(e) {
      console.log(e);
      throw e;
    }	    
    return {scholarships: scholarshipList};	
  }

  /** 
   * @param {*} item this scholarship Json object. 
   * @return The scholarship list item.
   */
  formatListItem(item) {
    const amount = (item[AMOUNT_PER_YEAR] || UNKNOWN);
    return {
      scholarshipName: item[SCHOLARSHIP_NAME],
      id: item[ID],
      amountPerYear: amount.substring(0, END_OF_STRING),
      schools: EMPTY_STRING,
    };
  }

}

exports = {ScholarshipListDataHandler}

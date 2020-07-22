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

const AMOUNT_PER_YEAR = 'amountPerYear';
const ELLIPSIS = '...';
const EMPTY_STRING = '';
const END_OF_STRING = 23;
const ID = 'id';
const INDEX_OF_LAST_ITEM = 'indexOfLastItem';
const UNKNOWN = 'unknown';
const NUMBER_OF_ITEMS = 'numberOfItems';
const SCHOLARSHIP_LIST_ENDPOINT = '/scholarship-list';
const SCHOLARSHIP_NAME = 'scholarshipName';

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
    const queryString = `?${NUMBER_OF_ITEMS}=${itemsPerBatch}&
        ${INDEX_OF_LAST_ITEM}=${lastIndex}`;
    try {
      let responseList = await fetch(SCHOLARSHIP_LIST_ENDPOINT + queryString);
      scholarshipList = await responseList.json();
      scholarshipList = scholarshipList.map(e => this.formatListItem(e));
    } catch(e) {
      console.log(e);
      throw e;
    }	   
    return {
        batchofitems: {
          type: 'scholarship',
          items: scholarshipList,
      },
    };	
  }

  /**
   * @param {*} item The schoarship object.
   * @returns The formatted scholarship list item.
   */
  formatListItem(item) {
    const amount = (item[AMOUNT_PER_YEAR] || UNKNOWN);
    return [
      item[ID], 
      item[SCHOLARSHIP_NAME],
      amount.substring(0, END_OF_STRING) + ELLIPSIS,
      ELLIPSIS,
    ];
  }
}

exports = {ScholarshipListDataHandler}
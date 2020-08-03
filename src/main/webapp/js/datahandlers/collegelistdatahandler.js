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

const {ACCEPTANCE_RATE, ACT_SCORE, CITY, CollegeQueryBuilder, ID, NAME, STATE} = goog.require('datahandlers.collegequerybuilder');
const {ListDataHandler} = goog.require('datahandlers.listdatahandler');

const METADATA = 'metadata';
const TOTAL = 'total';

/**
 * The data controller which fetches college data
 * from backend and reformats the data.
 */
class CollegeListDataHandler extends ListDataHandler {
  constructor() {
    super();
  }

  /**
   * @return {?Promise<number>}
   * The total number of scholarship stored in backend.
   */
  async getTotalNumber() {
    try {
      let data = await fetch(CollegeQueryBuilder.buildCollectionEndpoint(0, 1));
      let number = await data.json();
      return parseInt(number[METADATA][TOTAL], /** radix= */ 10);
    } catch(e) {
      throw new Error(`Cannot get total number from server ${e}`);
    }
  }

  /**
   * Converts list items from Json to objects.
   * @param {?Object} element The college or scholarship list item data.
   * @return {!Array<string>}
   * @override
   * @protected
   */
  formatListItem(element) {
    return [
      element[ID].toString(),
      element[NAME],
      `Acceptance Rate: ${(element[ACCEPTANCE_RATE]*100)
                                .toFixed(/** precision= */ 2)}%`,
      `${element[CITY]}, ${element[STATE]}`,
      `Average ACT Score: ${element[ACT_SCORE]}`,
    ];
  }

  /**
   * @param {number} batchIndex The index of last batch rendered.
   * @param {number} itemsPerBatch Number of items requested.
   * @param {string} lastIndex Index of the last item in the list.
   * @param {string} sortParam The parameter to sort by.
   * @param {string} sortDirection The sort direction/order.
   * @return {string} The url with query information.
   * @override
   * @protected
   */
  getPath(batchIndex, itemsPerBatch, lastIndex, sortParam, sortDirection) {
    return CollegeQueryBuilder.buildSortedCollectionEndpoint(
        batchIndex, itemsPerBatch,
        sortParam, sortDirection);
  }
}

exports = {CollegeListDataHandler};

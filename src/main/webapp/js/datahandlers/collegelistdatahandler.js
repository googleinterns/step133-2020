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

const {ACCEPTANCE_RATE, ACT_SCORE, CollegeQueryBuilder, ID, NAME} = goog.require('datahandlers.collegequerybuilder');
const {ListDataHandler} = goog.require('datahandlers.listdatahandler');
const {convertToPercent, concatTitleToValue} = goog.require('datahandlers.utils');

const METADATA = 'metadata';
const TOTAL = 'total';
const ACCEPTANCE_TITLE = 'Acceptance Rate:';
const ACT_TITLE = 'Average ACT Score:';

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
      concatTitleToValue(ACCEPTANCE_TITLE, convertToPercent(element[ACCEPTANCE_RATE])),
      concatTitleToValue(ACT_TITLE, element[ACT_SCORE].toString()),
    ];
  }

  /**
   * @param {number} batchIndex The index of last batch rendered.
   * @param {number} itemsPerBatch Number of items requested.
   * @param {string} lastIndex Index of the last item in the list.
   * @return {string} The url with query information.
   * @override
   * @protected
   */
  getPath(batchIndex, itemsPerBatch, lastIndex) {
    return CollegeQueryBuilder.buildCollectionEndpoint(
        batchIndex, itemsPerBatch);
  }
}

exports = {CollegeListDataHandler};

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

const {ListDataHandler} = goog.require('datahandlers.listdatahandler');

const AMOUNT_PER_YEAR = 'amountPerYear';
const ELLIPSIS = '...';
const END_OF_STRING = 23;
const ID = 'id';
const SCHOLARSHIP_NAME = 'scholarshipName';
const SCHOOL_NAME = 'school names';
const UNKNOWN = 'unknown';

/**
 * The data controller which fetches scholarship data
 * from backend and reformats the data.
 */
class ScholarshipListDataHandler extends ListDataHandler {
  /**
   * @return {?Promise<number>}
   * The total number of scholarship stored in backend.
   */
  async getTotalNumber() {
    // TODO: fetch from backend.
    return 35;  // Presetting page length not supported yet.
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
    return `/scholarship-list?numberOfItems=${itemsPerBatch}&idOfLastItem=${
        lastIndex}`;
  }

  /**
   * @param {!Object} item The scholarship object.
   * @return {!Array<string>} The formatted scholarship list item.
   * @protected
   * @override
   */
  formatListItem(item) {
    let amount = (item[AMOUNT_PER_YEAR] || UNKNOWN);  
    return [
      item[ID],
      item[SCHOLARSHIP_NAME],
      SCHOOL_NAME,
      amount,
    ];
  }
}

exports = {ScholarshipListDataHandler};

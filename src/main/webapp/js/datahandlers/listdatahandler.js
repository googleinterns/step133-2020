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

/** @fileoverview This class loads scholarship data from the servlet. */

goog.module('datahandlers.listdatahandler');


/** 
 * This class loads scholarship/college data from the backend and formats it for soy templates.  
 */
class ListDataHandler {

  /** 
   * @returns {Promise<number>} 
   * The total number of scholarship stored in backend. 
   */
  async getTotalNumber() {
    return 15;
  }

  /**
   * Returns the url with query information
   * @abstract
   * @private
   */
  getPath_() {}

  /**
   * Converts list items from Json to objects.
   * @abstract
   * @private
   */
  formatListItem_(){}

     /**
   * @param {number} batchIndex The index of last batch rendered.
   * @param {number} itemsPerBatch Number of items requested.
   * @param {string} lastIndex Index of the last item in the list.
   * @return {Promise<{
   *  type: string,
   *  items: !Array<!Array<string>>
   * }>}
   */
  async getNextBatch(type) {
    try {
      const responseList = await fetch(this.getPath_());
      const listJson = await responseList.json();
      const listItems = listJson.map(e => this.formatListItem_(e));
      return {
        type: type,
        items: listItems,
      };	
    } catch(e) {
      console.log(e);
      throw e;
    }	   
  }
}

exports = {ListDataHandler};
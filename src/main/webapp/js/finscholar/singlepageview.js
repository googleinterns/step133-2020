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

/** @fileoverview This class renders view for scholarship. */

goog.module('finscholar.singlepageview');

const GoogDom = goog.require('goog.dom');

/**
 * Class for scholarship page view.
 * @public
 */
class SinglePageView {

  /**
   * @constructor
   */
  constructor(dataHandler, template) {
    /** 
     * @private @const {!ScholarshipDataHandler | CollegePageDataHandler} dataHandler_ 
     * The object fetches and formats scholarship data.
     */
    this.dataHandler_ = dataHandler;
    /**
     * @private @const {function(?):Element}
     */
    this.template_ = template;
  }

  /**
   * Render the single scholarship/college page.
   * @public
   * @param {!Element} container 
   * The DOM container for one scholarship/college page.
   * @param {number} id The id of the scholarhsip/college.
   */
  async renderView(container, id) {
    let formattedData = undefined;
    try {
      formattedData = await this.dataHandler_.fetchAndFormatData(id);
    } catch (e) {
      console.log(e);
      throw new Error(`Cannot get data for object ${id}, message: ${e}`);
    }
    try {
      container.innerHTML = this.template_(formattedData);      
    } catch(e) {
      console.log(e);
      throw new Error(`Failed to generate html: ${e}`);
    }
  }
}

exports = {SinglePageView};


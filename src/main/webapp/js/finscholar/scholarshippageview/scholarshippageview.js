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

goog.module('finscholar.scholarshippageview');

const GoogDom = goog.require('goog.dom');
const {scholarshippage} = goog.require('example.templates.scholarshippageviews');
const {ScholarshipDataHandler} = goog.require('datahandlers.scholarshipdatahandler');

/**
 * Class for scholarship page view.
 * @public
 */
class ScholarshipPageView {

  /**
   * @constructor
   */
  constructor() {
    /** 
     * @private @const {!ScholarshipDataHandler} dataHandler_ 
     * The object fetches and formats scholarship data.
     */
    this.dataHandler_ = new ScholarshipDataHandler();
  }

  /**
   * Render the scholarship page.
   * @public
   * @param {string} id The string uuid of the scholarship object to be rendered.
   * @param {!Element} container - The DOM element for single scholarship page.
   */
  async renderScholarship(id, container) {
    let scholarshipData = undefined;
    try {
      scholarshipData = await this.dataHandler_.fetchAndFormatSingleScholarshipData(id);
    } catch (e) {
      console.log(e);
      // Throws the error to the caller, and the caller will render an error page instead.
      throw new Error(`Cannot get data for scholarship ${id}, message: ${e}`);
    }
    try {
      container.innerHTML = scholarshippage({scholarship: scholarshipData});      
    } catch(e) {
      console.log(e);
      // Throws the error to the caller, and the caller will render an error page instead.
      throw new Error(`Failed to generate html: ${e}`);
    }
  }

}

exports = {ScholarshipPageView};


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

const {BasicView} = goog.require('basicview');
const {ScholarshipDataHandler} = goog.require('datahandlers.scholarshipdatahandler');
const {scholarshippage} = goog.require('example.templates.scholarshippageviews');


/** Class for scholarship page view. */
class ScholarshipPageView extends BasicView {
  constructor() {
    super();
    /**
     * @private @const {!ScholarshipDataHandler} dataHandler_
     * The object fetches and formats scholarship data.
     */
    this.dataHandler_ = new ScholarshipDataHandler();

    this.fetchData_();
  }

  /**
   * Fetches data and sets up the view.
   * @private
   */
  async fetchData_() {
    // In the prototype, the id is set to 0 by default. Later we'll pass in id
    // as parameter.
    const id = '0';
    let scholarshipData = undefined;
    try {
      scholarshipData =
          await this.dataHandler_.fetchAndFormatSingleScholarshipData(id);
    } catch (e) {
      console.log(e);
      // Throws the error to the caller, and the caller will render an error
      // page instead.
      throw new Error(`Cannot get data for scholarship ${id}, message: ${e}`);
    }
    try {
      super.setCurrentContent(scholarshippage({scholarship: scholarshipData}));
    } catch (e) {
      console.log(e);
      // Throws the error to the caller, and the caller will render an error
      // page instead.
      throw new Error(`Failed to generate html: ${e}`);
    }
  }
}

exports = {ScholarshipPageView};

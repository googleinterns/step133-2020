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

/** @fileoverview Base class for College View. */

goog.module('finscholar.collegepageview');

const googDom = goog.require('goog.dom');
const {CollegeDataHandler} = goog.require('datahandlers.collegepage');
const {SinglePageView} = goog.require('finscholar.singlepageview');
const {collegepage, scholarshiplist} = goog.require('finscholar.collegepageview.templates');

const SCHOLARSHIP_DIV_NAME = 'scholarships';

/** Class for the college page view. */
class CollegePageView extends SinglePageView {
  constructor() {
    super(new CollegeDataHandler(), collegepage);

    /** @private {?Element} The container for scholarship buttons. */
    this.scholarshipContainer_ = null;
  }

  /** @override */
  async renderView() {
    try {
      await super.renderView();
      this.scholarshipContainer_ = googDom.getElement(SCHOLARSHIP_DIV_NAME);
      await this.renderScholarships_();
    } catch(e) {
      throw new Error(`Error rendering views for colleges ${e}`);
    }
  }
 
  /** @private Rander buttons for each scholarship related to current college. */
  async renderScholarships_() {
    try {
      const nameAndIdList = await (/** @type {!CollegeDataHandler} */(this.dataHandler))
                                .findScholarships(this.id);
      this.scholarshipContainer_.innerHTML = 
          scholarshiplist({scholarships : nameAndIdList});
    } catch(e) {
      throw new Error(`Cannot get scholarship data or render scholarship buttons ${e}`);
    }
  }
}

exports = {CollegePageView};

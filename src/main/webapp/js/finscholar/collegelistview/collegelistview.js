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

/** @fileoverview The mini controller for college list view. */

goog.module('finscholar.collegelistview');

const {CommonListView} = goog.require('finscholar.commonlistview');
const {CollegeListDataHandler} = goog.require('datahandlers.collegelistdatahandler');

/** The mini controller for college list view. */
class CollegeListView extends CommonListView {
  
  constructor() {
    super(new CollegeListDataHandler(), '0');
  }

  /**
   * Renders a college list view to the container.
   * @param {!Element} container The HTML container to load the view.
   */
  renderView(container) {
    try {
      await super.init(container);
    } catch(e) {
      console.log(e);
      throw e;
    }
  }
}

exports = {CollegeListView}
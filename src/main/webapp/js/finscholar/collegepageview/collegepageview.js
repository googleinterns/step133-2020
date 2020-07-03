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

const {loadCollegeData} = goog.require('finscholar.collegepageview.datahandler');
const {collegepage} = goog.require('finscholar.collegepageview.templates');
const GoogDom = goog.require('goog.dom');


/**
 * Class for the college page view.
 * @public
 */
class CollegePageView {
  constructor(element) {
    this.element = element;
  }

  async renderPage() {
    await loadCollegeData(this.element);
  }

}

exports = {CollegePageView};

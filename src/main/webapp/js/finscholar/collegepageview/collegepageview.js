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

const GoogDom = goog.require('goog.dom');
const {loadCollegeData} = goog.require('datahandlers.collegepage');

/** Class for the college page view. */
class CollegePageView {
  constructor() {}

  /** Render the college page. */
  async renderView(element) {
    await loadCollegeData(element);
  };
}

exports = {CollegePageView};
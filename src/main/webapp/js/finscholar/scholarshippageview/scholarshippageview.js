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

const {ScholarshipDataHandler} = goog.require('datahandlers.scholarshipdatahandler');
const {SinglePageDataHandler} = goog.require('datahandlers.singlepagedatahandler');
const {SinglePageView} = goog.require('finscholar.singlepageview');
const {scholarshippage} = goog.require('example.templates.scholarshippageviews');

/** Class for scholarship page view. */
class ScholarshipPageView extends SinglePageView {
    
  constructor() {
    super(new ScholarshipDataHandler(), scholarshippage);
  }
}

exports = {ScholarshipPageView};

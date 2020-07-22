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

/** @fileoverview The mini controller for scholarship list view. */

goog.module('finscholar.scholarshiplistview');

const {BasicView} = goog.require('basicview');
const {scholarshiplist} = goog.require('finscholar.scholarshiplistview.templates');

/** The mini controller for scholarship list view. */
class ScholarshipListView extends BasicView {
  constructor() {
    super();
    // TODO: In MVP, we'll add member variables such as pages, the array of
    // scholarship etc.
    super.setCurrentContent(scholarshiplist());
  }
}

exports = {ScholarshipListView};

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

/** @fileoverview Factory for all the views. */

goog.module('finscholar.viewfactory');

const {BasicView} = goog.require('basicview');
const {HomePageController} = goog.require('finscholar.homepagecontroller');
const {ScholarshipListView} = goog.require('finscholar.scholarshiplistview');
const {ScholarshipPageView} = goog.require('finscholar.scholarshippageview');


/**
 * Factory for the navbar listviews.
 * @param {number} index The index mapping to the tab.
 * @return {!BasicView} Object with the view data.
 */
const navbarViewFactory = (index) => {
  let view;
  switch (index) {
    case 0:
      view = new HomePageController();
      break;
    case 1:
      view = new ScholarshipListView();
      break;
    case 2:
      view = new ScholarshipListView();
      break;
    case 3:
      view = new ScholarshipPageView();
      break;
    default:
      view = new HomePageController();
      break;
  }
  return /** @type {!BasicView} */ (view);
};

exports = {navbarViewFactory};

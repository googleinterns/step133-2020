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

/** @fileoverview Base class for the page controller. */

goog.module('finscholar.homepagecontroller');

const {homepage} = goog.require('finscholar.homepagecontroller.templates');
const {singleScholarship} = goog.require('example.templates.singleScholarship');
const GoogDom = goog.require('goog.dom');
const {PageController} = goog.require('pagecontroller');

/**
 * Class for the home page controller.
 * @public
 */
class HomePageController extends PageController {

  constructor() {
    super();
    this.data = { scholarship: {
    introduction: {
      scholarshipName: 'test name',
      schoolsList: ['a', 'b', 'c'],
      URL: 'https://www.google.com',
      introduction: 'This is the introduction of the scholarship',
    },
    requirements: {
      academicRequirements: ['gpa >= 3.0', 'attendance'],
      ethnicityRaceRequirements: ['all races', 'aliens'],
      genderRequirements: ['non-binary'],
      nationalOriginRequirements: ['India', 'Thailand'],
      locationRequirements: ['South East Asia'],
      financialRequirements: ['net income <= $9,200'],
      otherRequirements: [],
    },
    applicationNotes: {
      isRenewable: false,
      numberOfYears: 1,
      amountPerYear: "full tuition and room & board",
    },
   },
  };
  }

  getContent() {
    return homepage();
  }

  renderSection(index) {
    GoogDom.getElement('content').innerHTML = singleScholarship(this.data);

  }
}

exports = {HomePageController};

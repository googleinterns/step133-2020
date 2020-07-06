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

/** @fileoverview Main entry point for the app. */

goog.module('finscholar');

const GoogDom = goog.require('goog.dom');
const {HomePageController} = goog.require('finscholar.homepagecontroller');
const {CollegePageView} = goog.require('finscholar.collegepageview');

/**
 * Initialize the main web page. 
 */
const init = async () => {
  const homeController = new HomePageController();
  console.log(GoogDom);
  GoogDom.getElement('main').innerHTML = homeController.content;

  // Render the college page by loading in the data and rendering the 
  // associated soy template.
  try {
    await renderCollege(GoogDom.getElement('college'));
  } catch(err) {
    alert('Failed to initialize page. College page could not be rendered.');
    console.log(err);
  }
};

/**
 * Render the college page.
 * @param {*} element - The DOM element to attach the college page to.
 */
const renderCollege = async (element) => {
  const collegePage = new CollegePageView(element);
  await collegePage.renderPage();
};

window['onload'] = init;

goog.exportSymbol('onload', init);

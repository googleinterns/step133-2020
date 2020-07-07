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

const JsactionActionFlow = goog.require('jsaction.ActionFlow');
const JsactionDispatcher = goog.require('jsaction.Dispatcher');
const JsactionEventContract = goog.require('jsaction.EventContract');
const GoogDom = goog.require('goog.dom');
const GoogSoy = goog.require('goog.soy');
const {homepage} = goog.require('finscholar.homepagecontroller.templates');
const {PageController} = goog.require('pagecontroller');
const {ScholarshipPageView} = goog.require('finscholar.scholarshippageview');

/**
 * Class for the home page controller.
 * @public
 */
class HomePageController extends PageController {
  constructor() {
    super();
    /**
     * @private @constant
     * @type {!ScholarshipPageView} 
     * The object loading single Scholarship page.
     */
    this.scholarshipPageHandler_ = new ScholarshipPageView(GoogDom.getElement('content'));
    // this.renderScholarshipPage.bind(this);
  }

  /**
   * @return {!GoogSoy.data.SanitizedHtml} The rendered HTML of the common framework.
   */
  getContent() {
    return homepage();
  }

  /**
   * Renders a view for a scholarship object, which is specified by the uuid.
   * @public
   * @param {string} id The uuid of the scholarship to be rendered.
   */
  async renderScholarshipPage(id) {
    console.log(this);
    try {
      await this.scholarshipPageHandler_.renderScholarship(id);
    } catch(e) {
      alert(e);
    }
  }
}

exports = {HomePageController};

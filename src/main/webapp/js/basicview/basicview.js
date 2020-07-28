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

/** Basic view class for all view objects. */

goog.module('basicview');

const googDom = goog.require('goog.dom');
const googSoy = goog.require('goog.soy');
const {root} = goog.require('basicview.templates');

/** @const {string} */
const DEFAULT_PAGE_TITLE = 'finscholar';

/** Basic view class for all view objects. */
class BasicView {
  constructor() {
    /** @private @type {string} */
    this.pageTitle_ = DEFAULT_PAGE_TITLE;

    /** @private @type {?googSoy.data.SanitizedHtml} */
    this.currentContent_ = null;
  }

  /**
   * Returns the current page's title.
   * @return {string} Title.
   */
  getPageTitle() {
    return this.pageTitle_;
  }

  /**
   * @param {string} title
   */
  setPageTitle(title) {
    this.pageTitle_ = title;
  }

  /**
   * Returns the main content of the page and null if blank.
   * @return {?googSoy.data.SanitizedHtml}
   */
  getCurrentContent() {
    return this.currentContent_;
  }

  /**
   * Returns the raw content element.
   * @return {!Element}
   */
  getCurrentContentElement() {
    return /** @type {!Element} */ (googDom.getElement('content'));
  }

  /**
   * @param {?googSoy.data.SanitizedHtml} currentContent
   */
  setCurrentContent(currentContent) {
    this.currentContent_ = currentContent;
  }

  /**
   * Helper function for resetting and updating the whole page. Note that this
   * will reset any JSAction listeners registered.
   */
  resetAndUpdate() {
    googDom.getDocument().documentElement.innerHTML =
        root(/** @type {!root.Params} */ (
            {pageTitle: this.pageTitle_, content: this.currentContent_}));
  }

  /**
   * Public method for updating the current view.
   */
  async renderView() {
    this.resetAndUpdate();
  }
}

exports = {BasicView};

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

/** @fileoverview Base class for Error Page View. */

goog.module('finscholar.errorpageview');

const GoogDom = goog.require('goog.dom');

/**
 * Class for the error page view.
 * @public
 */
class ErrorPageView {
  /**
   * @constructor
   * @param {Element} element - The DOM element to render the college page to.
   * @param {Error} error - The error message for the error that was thrown when something failed to load.
   */
  constructor(element, error) {
    /** @private {Element} - The DOM element where the CollegePageView will be rendered. */
    this.element_ = element;
    this.error_ = error;
  }

  /**
   * @public 
   * @return {Element} - The DOM element associated with this view. 
   */
  get element() {
    return this.element_;
  }

  /**
   * @public 
   * @return {Error} - The error message for the error that was thrown when something failed to load. 
   */
  get error() {
    return this.error_;
  }

  /**
   * Render the error page.
   * @public
   */
  async renderError() {
   // TODO: Render error page.
  };
}

exports = {CollegePageView};
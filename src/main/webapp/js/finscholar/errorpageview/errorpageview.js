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
const {errorpage} = goog.require('finscholar.errorpageview.templates');

/** Class for the error page view. */
class ErrorPageView {
  constructor() {}

  /**
   * Render the error page.
   * @param {Element} element - The element to render the error page to.
   * @param {string} newOccurrence - Message explaining what happened.
   * @param {string} newAction - Action to be taken upon receiving the error message.
   * @param {string} newErrorMessage - The error message that was thrown when the error occurred.
   */
  async renderErrorPage(element, newOccurrence, newAction, newErrorMessage) {
    const data = {
      occurrence : newOccurrence,
      action : newAction,
      errorMessage : newErrorMessage
    }
    const html = errorpage(data);
    element.innerHTML = html;
  };
}

exports = {CollegePageView};
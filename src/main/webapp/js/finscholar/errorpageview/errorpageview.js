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

const {BasicView} = goog.require('basicview');
const {errorpage} = goog.require('finscholar.errorpageview.templates');
/**
 * @typedef {{
 *   occurrence: string,
 *   action: string,
 *   errorMessage: string
 * }}
 */
let ErrorData;

/**
 * @typedef {{
 *   occurrence: string,
 *   action: string,
 *   errorMessage: string
 * }}
 */
let ErrorData;

/** Class for the error page view. */
class ErrorPageView extends BasicView {
  /**
   * @param {!ErrorData=} data Object containing error info.
   */
  constructor(data) {
    super();

    /** @private @type {!ErrorData|undefined} */
    this.data_ = data;

    const html = errorpage(/** @type {!ErrorData} */ (this.data_));
    super.setCurrentContent(html);
  }

  /**
   * Updates the data object. Useful for running before rendering view.
   * @param {!ErrorData} data New error info.
   */
  updateError(data) {
    this.data_ = data;
  }
}

exports = {
  ErrorPageView,
  ErrorData
};

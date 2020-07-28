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

goog.module('finscholar.singlepageview');

const {BasicView} = goog.require('basicview');
const {SinglePageDataHandler} = goog.require('datahandlers.singlepagedatahandler');

/**
 * Class for single scholarship/college page view.
 * @public
 */
class SinglePageView extends BasicView {

  constructor(dataHandler, template) {
    super();

    /** 
     * @private @const {!SinglePageDataHandler} dataHandler_ 
     * The object fetches and formats scholarship data.
     */
    this.dataHandler_ = dataHandler;

    /**
     * @private @const {!function(?): googSoy.data.SanitizedHtml}
     */
    this.template_ = template;

    /** @private @type {string} */
    this.id_ = '';
  }

  /**
   * Sets the id of the college or scholarship.
   * @param {string} id
   */
  setId(id) {
    this.id_ = id;
  }


  /**
   * Render the single scholarship/college page.
   * @override
   */
  async renderView() {
    let formattedData = undefined;
    try {
      formattedData = await this.dataHandler_.fetchAndFormatData(this.id_);
    } catch (e) {
      console.log(e);
      throw new Error(`Cannot get data for object ${this.id_}, message: ${e}`);
    }
    try {
      super.setCurrentContent(this.template_(formattedData));      
      super.resetAndUpdate();
    } catch(e) {
      console.log(e);
      throw new Error(`Failed to generate html: ${e}`);
    }
  }
}

exports = {SinglePageView};

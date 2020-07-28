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

/** @fileoverview This class loads scholarship data from the servlet. */

goog.module('datahandlers.singlepagedatahandler');


/**
 * This class loads scholarship/college data from the backend and formats it for
 * soy templates.
 * @abstract
 */
class SinglePageDataHandler {
  /**
   * Fetch the scholarship/college data with the id and format it.
   * @param {string} id The uuid of the scholarship/college data.
   * @return {?Object} The formatted scholarship/college JS object map.
   */
  async fetchAndFormatData(id) {
    let data = undefined;
    try {
      data = await this.fetchJson_(id);
      if (data === undefined) {
        throw new Error('Cannot get data from remote.');
      }

      return this.convertFromJsonToTemplate(data);
    } catch (e) {
      console.log(e);
      throw (`Failed to fetch scholarship object ${e}`);
    }
  }

  /**
   * @param {*} jsonResponse
   * @return {!Object} Json data needed for rendering page.
   * @protected
   */
  getJsonData(jsonResponse) {
    return /** @type {!Object} */ (jsonResponse);
  }

  /**
   * @param {string} id
   * @returns {string} path
   * @abstract
   * @protected
   */
  getRequestPath(id) {}

  /**
   * This method converts from scholarship JSON object to a JS object map,
   *  which will be used to render the scholarship page soy template.
   * @param {*} data - The JSON object to be converted.
   * @return {?Object} - The object map representing a scholarship's data.
   * @abstract
   * @protected
   */
  async convertFromJsonToTemplate(data) {}


  /**
   * Fetch request to the data servlet and return the JSON response.
   * @param {string} id The uuid of the schedule.
   * @return {?Object} - The JSON response.
   * @private
   */
  async fetchJson_(id) {
    const response = await fetch(this.getRequestPath(id));
    let data = undefined;
    if (response.ok) {
      try {
        data = await response.json();
        return this.getJsonData(data);
      } catch (e) {
        console.log(e);
        throw new Error(`Failed to parse response from server: ${e}`);
      }
    } else {
      const warning = `Failed to get response from server: 
          ${response.statusText}. Status: ${response.status}`;
      console.log(warning);
      throw new Error(warning);
    }
  };
}

exports = {SinglePageDataHandler};

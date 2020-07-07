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

goog.module('datahandlers.scholarshipdatahandler');

/** This class loads scholarship data from backend and format it for soy template. */
class ScholarshipDataHandler {

  /**
   * @constructor
   */
  constructor() {
    /** 
     * This constant is the endpoint to send a fetch request to. 
     * @private @const
     */
    this.SCHOLARSHIP_ENDPOINT_ = '/scholarship-data';
  }

  /**
   * This method converts from scholarship JSON object to a JS object map, 
   *  which will be used to render the scholarship page soy template.
   * @param {*} data - The JSON object to be converted.
   * @return {Object} - The object map representing a scholarship's data.
   * @private
   */
  async convertFromJsonToTemplate_(data) {
    const SEPARATOR = ', ';
    return {
      generalInfo: {
        scholarshipName: data['scholarshipName'], 
        scholarshipUUID: data['scholarshipUUID'], 
        schoolsList: data['schoolsList'].join(SEPARATOR),
        introduction: data['introduction']['value'] || 'N/A', 
        URL: data['URL'],
      },
      requirements: {
        academicRequirements: data['academicRequirements'].join(SEPARATOR) || 'N/A', 
        ethnicityRaceRequirements: data['ethnicityRaceRequirements'].join(SEPARATOR) || 'N/A', 
        financialRequirements: data['financialRequirements'].join(SEPARATOR) || 'N/A',
        genderRequirements: data['genderRequirements'].join(SEPARATOR) || 'N/A',
        locationRequirements: data['locationRequirements'].join(SEPARATOR) || 'N/A',
        nationalOriginRequirements: data['nationalOriginRequirements'].join(SEPARATOR) || 'N/A',
        otherRequirements: data['otherRequirements'].join(SEPARATOR) || 'N/A',
      },
      applicationNotes: {
        amountPerYear: data['amountPerYear']['value'] || 'unknown',
        applicationProcess: data['applicationProcess']['value'] || 'unknown',
        isRenewable: data['isRenewable']['value'] || 'unknown',
        numberOfYears: data['numberOfYears']['value'],
      }, 
    };
  };

  /**
   * Fetch the scholarship data with the specified uuid and format it.
   * @param {string} id The uuid of the scholarship data.
   * @return The formatted scholarship JS object map.
   */
  async fetchAndFormatSingleScholarshipData(id) {
    let data = undefined;
    try {
      data = await this.fetchScholarshipJson(id);
    } catch (e) {
      console.log(e);
      throw(e);
    }
    
    // If data is undefined, bring user to an error page.
    // This check will be done in ScholarshipPageView.
    // Later after we support querying by uuid, data will be a acholarship object.
    return this.convertFromJsonToTemplate_(data[id]);
  }

  /**
   * Fetch request to the data servlet and return the JSON response.
   * @param {string} id The uuid of the schedule.
   * @return {*} - The JSON response.
   * @private
   */
  async fetchScholarshipJson(id) {
    const response = await fetch(this.SCHOLARSHIP_ENDPOINT_, {'id': id });
    let data = undefined;
    if (response.ok) {
      try {
        data = await response.json();
        return data;
      } catch (e) {
        console.log(e);
        throw new Error(`Failed to parse response from server: ${e}`);
      }
    } else {
      console.log(e);
      throw new Error(`Failed to get response from server: 
          ${response.statusText}. Status: ${response.status}`);
    }
  };
}

exports = {ScholarshipDataHandler};

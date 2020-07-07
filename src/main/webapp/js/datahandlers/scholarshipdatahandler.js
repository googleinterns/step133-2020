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

class ScholarshipDataHandler {


  constructor() {
    /** 
     * This constant is the endpoint to send a fetch request to. 
     * @private @const
     */
    this.ENDPOINT_ = '/scholarship-data';
  }

  /**
   * This method converts from scholarship JSON object to a JS object map, 
   *  which will be used to render the scholarship page soy template.
   * @private
   * @param {*} data - The JSON object to be converted.
   * @return {Object} - The object map representing a college's data.
   */
  async convertFromJsonToTemplate_(data) {
    let key = undefined;
    // for (key in data) {
    //   if (data[key] === {value: null}) {
    //     delete data.key;
    //   }
    // }
    const d = {
      generalInfo: {
        scholarshipName: data['scholarshipName'], 
        scholarshipUUID: data['scholarshipUUID'], 
        introduction: data['introduction'], 
        URL: data['URL'],
      },
      requirements: {
        academicReuirements: data['academicRequirement'], 
        ethnicityRaceRequirements: data['ethnicityRaceRequirements'], 
        financialRequirements: data['financialRequirements'],
        genderRequirements: data['genderRequirements'],
        locationrequirements: data['locationrequirements'],
        nationalOriginRequirements: data['nationalOriginRequirements'],
        otherRequirements: data['otherRequirements'],
      },
      applicationNotes: {
        amountPerYear: data['amountPerYear'],
        applicationProcess: data['applicationProcess'],
        isRenewable: data['isRenewable'],
        numberOfYears: data['numberOfYears'],
      }, 
    };
    console.log(d);
    return d;
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
      throw(e);
    }
    
    // If data is undefined, bring user to an error page.
    // This case will be handled in ScholarshipPageView.
    return this.convertFromJsonToTemplate_(data[0]);
    
  }

  /**
   * Fetch request to the data servlet and return the JSON response.
   * @private
   * @param {string} id The uuid of the schedule.
   * @return {*} - The JSON response.
   */
  async fetchScholarshipJson(id) {
    const response = await fetch(this.ENDPOINT_, {'id': id });
    let data = undefined;
    if (response.ok) {
      try {
        data = await response.json();
        console.log(data);
        return data;
      } catch (e) {
        throw new Error(`Failed to parse response from server: ${e}`);
      }
    } else {
      throw new Error(`Failed to get response from server: 
          ${response.statusText}. Status: ${response.status}`);
    }
  };

}

exports = {ScholarshipDataHandler};
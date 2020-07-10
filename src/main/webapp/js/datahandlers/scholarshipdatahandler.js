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
const {Map: SoyMap} = goog.require('soy.map');

/** This class loads scholarship data from the backend and formats it for soy templates.  */
class ScholarshipDataHandler {

  /** 
   * @return The string path of the endpoint to send a fetch request to. 
   * @private 
   */
  get scholarshipEndPoint_() {
    return '/scholarship-data';
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
    const NALIST = ['N/A'];
    const requirementsMap = new Map();
    requirementsMap.set('Academic Requirements', 
                        (data['academicRequirements'] || NALIST).join(SEPARATOR));
    requirementsMap.set('Ethnicity Race Requirements', 
                        (data['ethnicityRaceRequirements'] || NALIST).join(SEPARATOR));
    requirementsMap.set('Financial Requirements', 
                        (data['financialRequirements'] || NALIST).join(SEPARATOR));
    requirementsMap.set('Gender Requirements', 
                        (data['genderRequirements'] || NALIST).join(SEPARATOR));
    requirementsMap.set('Location Requirements', 
                        (data['locationRequirements'] || NALIST).join(SEPARATOR));
    requirementsMap.set('National Origin Requirements', 
                        (data['nationalOriginRequirements'] || NALIST).join(SEPARATOR));
    requirementsMap.set('Other Requirements', 
                        (data['otherRequirements'] || NALIST).join(SEPARATOR));
    return {
      generalInfo: {
        scholarshipName: data['scholarshipName'], 
        scholarshipUUID: data['scholarshipUUID'], 
        schoolsList: (data['schoolsList'] || ['N/A']).join(SEPARATOR),
        introduction: data['introduction'] || 'N/A', 
        URL: data['URL'],
      },
      requirements: requirementsMap,
      applicationNotes: {
        amountPerYear: data['amountPerYear'] || 'unknown',
        applicationProcess: data['applicationProcess'] || 'unknown',
        isRenewable: data['isRenewable'] || 'unknown',
        numberOfYears: data['numberOfYears'],
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
      data = await this.fetchScholarshipJson_(id);
    } catch (e) {
      console.log(e);
      throw(`Failed to fetch scholarship object ${e}`);
    }
    
    // If data is undefined, bring user to an error page.
    // This case will be handled in ScholarshipPageView.
    // Later after we support querying by uuid, data will be a acholarship object.
    if (data === undefined) {
      // This error will be handled and logged by the caller.
      throw new Error('Cannot get data from remote.');
    }
    //In actual project here the data will just be one scholarship object.
    return this.convertFromJsonToTemplate_(data[id]);
  }

  /**
   * Fetch request to the data servlet and return the JSON response.
   * @param {string} id The uuid of the schedule.
   * @return {*} - The JSON response.
   * @private
   */
  async fetchScholarshipJson_(id) {
    const response = await fetch(this.scholarshipEndPoint_, {'id': id });
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
      const WARNING = `Failed to get response from server: 
          ${response.statusText}. Status: ${response.status}`;
      console.log(WARNING);
      throw new Error(WARNING);
    }
  };
}

exports = {ScholarshipDataHandler};

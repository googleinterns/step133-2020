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
const NA_ = 'N/A';
const SEPARATOR_ = ', ';
const SCHOLARSHIP_ENDPOINT_ = '/scholarship-data';
const {addSpaceToCamelCase} = goog.require('datahandlers.utils');

/** This class loads scholarship data from the backend and formats it for soy templates.  */
class ScholarshipDataHandler {

  /**
   * This method converts from scholarship JSON object to a JS object map, 
   *  which will be used to render the scholarship page soy template.
   * @param {*} data - The JSON object to be converted.
   * @return {Object} - The object map representing a scholarship's data.
   * @private
   */
  async convertFromJsonToTemplate_(data) {
    const requirementsMap = new Map();
    const REQUIREMENTS_ = ['academicRequirements', 
                           'ethnicityRaceRequirements', 
                           'financialRequirements', 
                           'genderRequirements',
                           'locationRequirements',
                           'nationalOriginRequirements',
                           'otherRequirements']
    let requirement = undefined;
    for (requirement of REQUIREMENTS_) {
      if (REQUIREMENTS_[requirement] != undefined) {
        requirementsMap.set(addSpaceToCamelCase(requirement), REQUIREMENTS_[requirement].join(SEPARATOR_));
      } else {
        requirement.set(addSpaceToCamelCase(requirement), NA_);
      }
    }
    return {
      generalInfo: {
        scholarshipName: data['scholarshipName'], 
        scholarshipUUID: data['scholarshipUUID'], 
        schoolsList: (data['schoolsList'] || [NA_]).join(SEPARATOR_),
        introduction: data['introduction'], 
        URL: data['URL'],
      },
      requirements: requirementsMap,
      applicationNotes: {
        amountPerYear: data['amountPerYear'],
        applicationProcess: data['applicationProcess'],
        isRenewable: data['isRenewable'],
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

      if (data === undefined) {
        throw new Error('Cannot get data from remote.');
      }

      return this.convertFromJsonToTemplate_(data[id]);
    } catch (e) {
      console.log(e);
      throw(`Failed to fetch scholarship object ${e}`);
    }
  }

  /**
   * Fetch request to the data servlet and return the JSON response.
   * @param {string} id The uuid of the schedule.
   * @return {*} - The JSON response.
   * @private
   */
  async fetchScholarshipJson_(id) {
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
      const warning = `Failed to get response from server: 
          ${response.statusText}. Status: ${response.status}`;
      console.log(warning);
      throw new Error(warning);
    }
  };
}

exports = {ScholarshipDataHandler};
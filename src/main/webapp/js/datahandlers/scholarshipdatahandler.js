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
const {addSpaceToCamelCase} = goog.require('datahandlers.utils');

const NA = 'N/A';
const ID_QUERY_PARAM = '?id=';
const REQUIREMENTS = [
  'academicRequirements', 'ethnicityRaceRequirements', 'financialRequirements',
  'genderRequirements', 'locationRequirements', 'nationalOriginRequirements',
  'otherRequirements'
];
const SEPARATOR = ', ';
const SCHOLARSHIP_ENDPOINT = '/scholarship-data';

/**
 * This class loads scholarship data from the backend and formats it for soy
 * templates.
 */
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

    let requirement = undefined;
    for (requirement of REQUIREMENTS) {
      if (REQUIREMENTS[requirement] != undefined) {
        requirementsMap.set(
            addSpaceToCamelCase(requirement),
            REQUIREMENTS[requirement].join(SEPARATOR));
      } else {
        requirementsMap.set(addSpaceToCamelCase(requirement), NA);
      }
    }

    let key = undefined;
    for (key in data) {
      if (data.key == undefined) {
        data.key = NA;
      }
    }

    return {
      scholarship: {
        generalInfo: {
          scholarshipName: data['scholarshipName'],
          scholarshipUUID: data['scholarshipUUID'],
          schoolsList: data['schoolsList'],
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
      },
    };
  }

  /**
   * Fetch the scholarship data with the specified uuid and format it.
   * @param {number} id The uuid of the scholarship data.
   * @return The formatted scholarship JS object map.
   */
  async fetchAndFormatData(id) {
    let data = undefined;
    try {
      data = await this.fetchScholarshipJson_(id);

      if (data === undefined) {
        throw new Error('Cannot get data from remote.');
      }

      return this.convertFromJsonToTemplate_(data[id]);
    } catch (e) {
      console.log(e);
      throw (`Failed to fetch scholarship object ${e}`);
    }
  }

  /**
   * Fetch request to the data servlet and return the JSON response.
   * @param {number} id The uuid of the schedule.
   * @return {*} - The JSON response.
   * @private
   */
  async fetchScholarshipJson_(id) {
    const response = await fetch(SCHOLARSHIP_ENDPOINT + ID_QUERY_PARAM + id);
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
  }
}

exports = {ScholarshipDataHandler};

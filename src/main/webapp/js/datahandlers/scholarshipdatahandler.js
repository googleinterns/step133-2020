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
const {SinglePageDataHandler} = goog.require('datahandlers.singlepagedatahandler');

const NA = 'N/A';
const REQUIREMENTS =
    new Map()
        .set('academicRequirements', 'Academic Requirements')
        .set('ethnicityRaceRequirements', 'Ethnicity Race Requirements')
        .set('financialRequirements', 'Financial Requirements')
        .set('genderRequirements', 'Gender Requirements')
        .set('locationRequirements', 'Location Requirements')
        .set('nationalOriginRequirements', 'National Origin Requirements')
        .set('otherRequirements', 'Other Requirements');
const SEPARATOR = ', ';
const SCHOLARSHIP_ENDPOINT = '/scholarship-data';

/**
 * This class loads scholarship data from the backend and formats it for soy
 * templates.
 */
class ScholarshipDataHandler extends SinglePageDataHandler {
  /**
   * This method converts from scholarship JSON object to a JS object map,
   *  which will be used to render the scholarship page soy template.
   * @param {*} data - The JSON object to be converted.
   * @return {?Object} - The object map representing a scholarship's data.
   * @override
   * @protected
   */
  async convertFromJsonToTemplate(data) {
    const requirementsAndValue = new Map();

    let requirement = undefined;
    for (requirement of Array.from(REQUIREMENTS.keys())) {
      if (data[requirement] != undefined) {
        requirementsAndValue.set(
            REQUIREMENTS.get(requirement), data[requirement].join(SEPARATOR));
      } else {
        requirementsAndValue.set(REQUIREMENTS.get(requirement), NA);
      }
    }

    return {
      scholarship: {
        generalInfo: {
          scholarshipName: data['scholarshipName'] || NA,
          scholarshipUUID: data['scholarshipUUID'] || NA,
          schoolsList: data['schoolsList'] || NA,
          introduction: data['introduction'] || NA,
          URL: data['URL'] || NA,
        },
        requirements: requirementsAndValue,
        applicationNotes: {
          amountPerYear: data['amountPerYear'] || NA,
          applicationProcess: data['applicationProcess'] || NA,
          isRenewable: data['isRenewable'] || NA,
          numberOfYears: data['numberOfYears'] || NA,
        },
      },
    };
  };

  /**
   * @param {string} id
   * @returns {string} path
   * @override
   * @protected
   */
  getRequestPath(id) {
    return `${SCHOLARSHIP_ENDPOINT}?id=${id}`;
  }
}

exports = {ScholarshipDataHandler};

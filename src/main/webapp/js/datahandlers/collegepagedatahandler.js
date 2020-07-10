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

/**
 * @fileoverview This class handles populating the college page view with data
 * from the servlet.
 */

goog.module('datahandlers.collegepage');
const {collegepage} = goog.require('finscholar.collegepageview.templates');
const {ErrorData, ErrorPageView} = goog.require('finscholar.errorpageview');

/** This constant is the endpoint to send a fetch request to. */
const COLLEGE_SERVLET_ENDPOINT = '/college-data';

/**
 * @typedef {{
 *   schoolName: !string,
 *   institutionType: !string,
 *   acceptanceRate: !number,
 *   averageACTScore: !number,
 *   totalCostAttendance: !number,
 *   netCostForFirstQuintile: !number,
 *   netCostForSecondQuintile: !number,
 *   netCostForThirdQuintile: !number,
 *   netCostForFourthQuintile: !number,
 *   netCostForFifthQuintile: !number,
 *   cumulativeMedianDebt: !number
 * }}
 */
let CollegeObject;

/**
 * This method converts from JSON to a JS object map,
 *  which will be used to populate the college page soy template.
 * @param {!CollegeObject|undefined} json - The JSON object to be converted.
 * @return {!Promise<!CollegeObject>} - The object map representing a college's data.
 * @private
 */
const convertFromJsonToTemplate_ = async (json) => {
  if (json !== undefined) {
    return /** @type {!CollegeObject} */ ({
      schoolName: json['schoolName'],
      institutionType: json['institutionType'],
      acceptanceRate: json['acceptanceRate'],
      averageACTScore: json['averageACTScore'],
      totalCostAttendance: json['totalCostAttendance'],
      netCostForFirstQuintile: json['netCostForFirstQuintile'],
      netCostForSecondQuintile: json['netCostForSecondQuintile'],
      netCostForThirdQuintile: json['netCostForThirdQuintile'],
      netCostForFourthQuintile: json['netCostForFourthQuintile'],
      netCostForFifthQuintile: json['netCostForFifthQuintile'],
      cumulativeMedianDebt: json['cumulativeMedianDebt']
    });
  } else {
    throw new Error('JSON object undefined.');
  }
};

/**
 * Fetch request to the data servlet and return the JSON response.
 * @return {!Promise<!CollegeObject|undefined>} - The JSON response.
 * @private
 */
const loadCollegeJson_ = async () => {
  const response = await fetch(COLLEGE_SERVLET_ENDPOINT);
  if (response.ok) {
    return /** @type {!CollegeObject|undefined} */ (await response.json());
  } else {
    throw new Error(`${response.statusText}. Status: ${response.status}.`);
  }
};

/**
 * Render the soy template's html by attaching it to the desired DOM element.
 * @param {!Element} element - The DOM element where the template should be rendered to.
 */
const loadCollegeData =
    async (element) => {
  let json = undefined;

  try {
    json = await loadCollegeJson_();
  } catch (err) {
    const occurrence =
        'A network error has occurred. Failed to load college data.';
    const action = 'Please reload the page or select a different college.';
    console.log(`${occurrence} Error Message: ${err}.`);
    const errorPage = new ErrorPageView(/** @type {!ErrorData|undefined} */ ({
      occurrence: occurrence,
      action: action,
      errorMessage: err.toString()
    }));
    errorPage.renderView(element);
  }

  try {
    const data = await convertFromJsonToTemplate_(json);
    const html = collegepage(data);
    element.innerHTML = html;
  } catch (err) {
    const occurrence =
        'A database error occurred. Failed to render college data.';
    const action = `The database failed to retrieve the college.
      This college may not exist. Please reload the page or select a different college.`;
    const errorPage = new ErrorPageView(/** @type {!ErrorData|undefined} */ ({
      occurrence: occurrence,
      action: action,
      errorMessage: err.toString()
    }));
    errorPage.renderView(element);
    console.log(`${occurrence} Error Message: ${err}.`);
  }
}

exports = {loadCollegeData};

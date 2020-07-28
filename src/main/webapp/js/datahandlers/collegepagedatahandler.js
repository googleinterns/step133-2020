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

/** This constant is the endpoint to send a fetch request to. */
const COLLEGE_SERVLET_ENDPOINT = '/college-data';
const ERROR_MESSAGE_JSON_UNDEFINED = 'JSON object undefined.';
const OCCURRENCE_JSON_UNDEFINED =
    'A network error has occurred. Failed to load college data.';
const ACTION_JSON_UNDEFINED =
    'Please reload the page or select a different college.';
const OCCURRENCE_SERVER_ERROR =
    'A database error occurred. Failed to render college data.';
const ACTION_SERVER_ERROR =
    'The database failed to retrieve the college. This college may not ' +
    'exist. Please reload the page or select a different college.';


/**
 * @typedef {{
 *   schoolName: string,
 *   institutionType: string,
 *   acceptanceRate: number,
 *   averageACTScore: number,
 *   totalCostAttendance: number,
 *   netCostForFirstQuintile: number,
 *   netCostForSecondQuintile: number,
 *   netCostForThirdQuintile: number,
 *   netCostForFourthQuintile: number,
 *   netCostForFifthQuintile: number,
 *   cumulativeMedianDebt: number
 * }}
 */
let CollegeObject;

/**
 * This method converts from JSON to a JS object map,
 *  which will be used to populate the college page soy template.
 * @param {!CollegeObject} json - The JSON object to be converted.
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
    throw new Error(ERROR_MESSAGE_JSON_UNDEFINED);
  }
};

/**
 * Fetch request to the data servlet and return the JSON response.
 * @return {!Promise<!CollegeObject>} - The JSON response.
 * @private
 */
const loadCollegeJson_ = async () => {
  const response = await fetch(COLLEGE_SERVLET_ENDPOINT);
  if (response.ok) {
    return /** @type {!CollegeObject} */ (await response.json());
  } else {
    throw new Error(`${response.statusText}. Status: ${response.status}.`);
  }
};

/**
 * Render the soy template's html by attaching it to the desired DOM element.
 * @param {!Element} element - The DOM element where the template should be rendered to.
 */
const loadCollegeData = async (element) => {
  let json = undefined;

  try {
    json = await loadCollegeJson_();
  } catch (error) {
    loadErrorPage_(
        element, OCCURRENCE_JSON_UNDEFINED, ACTION_JSON_UNDEFINED, error);
  }

  try {
    const data = await convertFromJsonToTemplate_(json);
    const html = collegepage(data);
    element.innerHTML = html;
  } catch (error) {
    loadErrorPage_(
        element, OCCURRENCE_SERVER_ERROR, ACTION_SERVER_ERROR, error);
  }
};

/**
 * Loads the error page.
 * @private
 */
const loadErrorPage_ = (element, occurrence, action, error) => {
  //  const errorPage = new ErrorPageView(/** @type {!ErrorData|undefined} */ ({
  //    occurrence: occurrence,
  //    action: action,
  //    errorMessage: error.toString()
  //  }));
  //  errorPage.renderView(element);
};

exports = {loadCollegeData};

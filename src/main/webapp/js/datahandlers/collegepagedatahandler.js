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

/** @fileoverview This class handles populating the college page view with data from the servlet. */

goog.module('datahandlers');
const {collegepage} = goog.require('finscholar.collegepageview.templates');

/** This constant is the endpoint to send a fetch request to. */
const ENDPOINT = "/college-data";

/**
 * This method converts from JSON to a JS object map, 
 *  which will be used to populate the college page soy template.
 * @private
 * @param {*} json - The JSON object to be converted.
 * @return {Object} - The object map representing a college's data.
 */
const convertFromJsonToTemplate = async (json) => {
  return {
    schoolName : json["schoolName"],
    institutionType: json["institutionType"],
    acceptanceRate: json["acceptanceRate"],
    averageACTScore: json["averageACTScore"],
    totalCostAttendance: json["totalCostAttendance"],
    netCostForFirstQuintile: json["netCostForFirstQuintile"],
    netCostForSecondQuintile: json["netCostForSecondQuintile"],
    netCostForThirdQuintile: json["netCostForThirdQuintile"],
    netCostForFourthQuintile: json["netCostForFourthQuintile"],
    netCostForFifthQuintile: json["netCostForFifthQuintile"],
    cumulativeMedianDebt: json["cumulativeMedianDebt"]
  };
};

/**
 * Fetch request to the data servlet and return the JSON response.
 * @private
 * @return {*} - The JSON response.
 */
const loadCollegeJson = async () => {
    const response = await fetch(ENDPOINT);
    if(response.ok) {
      return await response.json();
    } else {
      throw new Error(`${response.statusText}. Status: ${response.status}`);
    }
};

/**
 * Render the soy template's html by attaching it to the desired DOM element.
 * @public
 * @param {Element} element - The DOM element where the template should be rendered to.
 */
const loadCollegeData = async (element) => {
  let json = undefined;

  try {
    json = await loadCollegeJson();
  } catch(err) {
    alert(`Failed to load college data: ${err}`);
  }

  const data = await convertFromJsonToTemplate(json);
  const html = collegepage(data);
  element.innerHTML = html;
}

exports = {loadCollegeData};
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

const {ACCEPTANCE_RATE, ACT_SCORE, CollegeQueryBuilder, FIFTH_NET_COST, FIRST_NET_COST, FOURTH_NET_COST, MEDIAN_DEBT, NAME, SECOND_NET_COST, THIRD_NET_COST} = goog.require('datahandlers.collegequerybuilder');
const {SinglePageDataHandler} = goog.require('datahandlers.singlepagedatahandler');
const {convertToPercent} = goog.require('datahandlers.utils');

const RESULTS = 'results';

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

class CollegeDataHandler extends SinglePageDataHandler {
  constructor() {
    super();
  }

  /**
   * @param {string} id
   * @returns {string} path
   * @override
   * @protected
   */
  getRequestPath(id) {
    return CollegeQueryBuilder.buildSingleQueryEndpoint(id);
  }

  /**
   * @param {*} json
   * @return {!Object} Json data needed for rendering page.
   * @override
   * @protected
   */
  getJsonData(json) {
    return /** @type {!Object} */ (json[RESULTS][0]);
  }

  /**
   * This method converts from scholarship JSON object to a JS object map,
   *  which will be used to render the scholarship page soy template.
   * @param {*} element - The JSON object to be converted.
   * @return {?Object} - The object map representing a scholarship's data.
   * @override
   * @protected
   */
  convertFromJsonToTemplate(element) {
    return {
      schoolName: element[NAME],
      acceptanceRate: convertToPercent(element[ACCEPTANCE_RATE]),
      averageACTScore: element[ACT_SCORE].toString(),
      netCostForFirstQuintile: element[FIRST_NET_COST].toString(),
      netCostForSecondQuintile: element[SECOND_NET_COST].toString(),
      netCostForThirdQuintile: element[THIRD_NET_COST].toString(),
      netCostForFourthQuintile: element[FOURTH_NET_COST].toString(),
      netCostForFifthQuintile: element[FIFTH_NET_COST].toString(),
      cumulativeMedianDebt: element[MEDIAN_DEBT].toString()
    };
  }
}

/**
 * Loads the error page.
 */
const loadErrorPage_ = (element, occurrence, action, error) => {
  // TODO: This is being rewritten.
};

exports = {CollegeDataHandler};

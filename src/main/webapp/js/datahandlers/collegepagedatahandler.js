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

const {CollegeQueryBuilder, ID, NAME, ACCEPTANCE_RATE, ACT_SCORE, ANNUAL_COST, 
    FIRST_NET_COST, SECOND_NET_COST, THIRD_NET_COST, FOURTH_NET_COST, 
    FIFTH_NET_COST, MEDIAN_DEBT} = goog.require('datahandlers.collegequerybuilder');
const {SinglePageDataHandler} = goog.require('datahandlers.singlepagedatahandler');
const {convertToDollar} = goog.require('datahandlers.utils');
const RESULTS = 'results';

class CollegeDataHandler extends SinglePageDataHandler {
  constructor() {
    super();
  }

  /**
   * @param {string} id
   * @returns {string} path
   * @override
   * @private
   */
  getRequestPath_(id) {
    return CollegeQueryBuilder.buildSingleQueryEndpoint(id);
  }

  /**
   * @param {Object} jsonResponse
   * @returns {Object} Json data needed for rendering page.
   * @override
   * @private
   */
  getJsonData_(json) {
    return json[RESULTS][0];
  }

  /**
   * This method converts from scholarship JSON object to a JS object map, 
   *  which will be used to render the scholarship page soy template.
   * @param {*} data - The JSON object to be converted.
   * @return {Object} - The object map representing a scholarship's data.
   * @override
   * @private
   */
  convertFromJsonToTemplate_(element) {
    return {
      schoolName : element[NAME],
      annualCost : convertToDollar(element[ANNUAL_COST]),
      acceptanceRate : element[ACCEPTANCE_RATE].toString(),
      averageACTScore : element[ACT_SCORE].toString(),
      netCostForFirstQuintile : 
          convertToDollar(element[FIRST_NET_COST]),
      netCostForSecondQuintile : 
          convertToDollar(element[SECOND_NET_COST]),
      netCostForThirdQuintile : 
          convertToDollar(element[THIRD_NET_COST]),
      netCostForFourthQuintile : 
          convertToDollar(element[FOURTH_NET_COST]),
      netCostForFifthQuintile : 
          convertToDollar(element[FIFTH_NET_COST]),
      cumulativeMedianDebt : 
          convertToDollar(element[MEDIAN_DEBT])
    }
  }
};

/**
 * Loads the error page.
 */
const loadErrorPage_ = (element, occurrence, action, error) => {
  // TODO: This is being rewritten.
};

exports = {CollegeDataHandler};

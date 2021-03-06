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

const {ACCEPTANCE_RATE, ACT_SCORE, ANNUAL_COST, ANNUAL_COST_PUBLIC, CITY,
    CollegeQueryBuilder, FIFTH_NET_COST, FIFTH_NET_COST_PUBLIC, FIRST_NET_COST, 
    FIRST_NET_COST_PUBLIC, FOURTH_NET_COST, FOURTH_NET_COST_PUBLIC, MEDIAN_DEBT, NAME, 
    SECOND_NET_COST, SECOND_NET_COST_PUBLIC, STATE, THIRD_NET_COST, THIRD_NET_COST_PUBLIC, TYPE} = 
        goog.require('datahandlers.collegequerybuilder');
const {SinglePageDataHandler} = goog.require('datahandlers.singlepagedatahandler');
const {convertToDollar, convertToPercent} = goog.require('datahandlers.utils');
const FIND_SCHOLARSHIP_ENDPOINT = '/find-scholarship';
const RESULTS = 'results';
const SCHOLARSHIP_NAME = 'scholarshipName';
const PUBLIC = 1;

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
      schoolName : element[NAME],
      annualCost : (element[TYPE] == PUBLIC) ? 
          convertToDollar(element[ANNUAL_COST_PUBLIC]) : 
          convertToDollar(element[ANNUAL_COST]),
      location : `${element[CITY]}, ${element[STATE]}`,
      acceptanceRate : convertToPercent(element[ACCEPTANCE_RATE]),
      averageACTScore : element[ACT_SCORE].toString(),
      netCostForFirstQuintile : (element[TYPE] == PUBLIC) ? 
          convertToDollar(element[FIRST_NET_COST_PUBLIC]) :
          convertToDollar(element[FIRST_NET_COST]),
      netCostForSecondQuintile : (element[TYPE] == PUBLIC) ?
          convertToDollar(element[SECOND_NET_COST_PUBLIC]) :
          convertToDollar(element[SECOND_NET_COST]),
      netCostForThirdQuintile : (element[TYPE] == PUBLIC) ?
          convertToDollar(element[THIRD_NET_COST_PUBLIC]) :
          convertToDollar(element[THIRD_NET_COST]),
      netCostForFourthQuintile : (element[TYPE] == PUBLIC) ?
          convertToDollar(element[FOURTH_NET_COST_PUBLIC]) :
          convertToDollar(element[FOURTH_NET_COST]),
      netCostForFifthQuintile : (element[TYPE] == PUBLIC) ?
          convertToDollar(element[FIFTH_NET_COST_PUBLIC]) :
          convertToDollar(element[FIFTH_NET_COST]),
      cumulativeMedianDebt : convertToDollar(element[MEDIAN_DEBT])
    };
  };

  /**
   * @param {string} id 
   * @return {!Promise<!Array<{
   * id: string,
   * name: string
   * }>>}
   * Find all scholarships related to a college by college id.
   */
  async findScholarships(id) {
    try {
      let scholarshipResponse = await fetch(`${FIND_SCHOLARSHIP_ENDPOINT}?id=${id}`);
      let scholarshipJson = await scholarshipResponse.json();
      if (scholarshipJson == undefined || scholarshipJson == []) {
        throw new Error('Scholarship Json is Empty');
      }
      return scholarshipJson.map((e) => this.formatScholarshipListButton_(e));
    } catch(e) {
      throw new Error(`Cannot get scholarship json ${e}`);
    }
  }

  /**
   * @param {!Object} elem
   * @returns {{
   * id: string,
   * name: string
   * }} elem The scholarship element to be formatted.
   * @private
   */
  formatScholarshipListButton_(elem) {
    return {
      'name' : elem[SCHOLARSHIP_NAME],
      'id' : elem['id'],
    };
  }
}

exports = {CollegeDataHandler};

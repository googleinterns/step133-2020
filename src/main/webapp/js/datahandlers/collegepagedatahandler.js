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

class CollegeDataHandler {
  constructor() {}

  /** 
   * @returns {Promise<number>} 
   * The max number of colleges to render. 
   */
  async getTotalNumber() {
    return 200; 
  }

  /**
   * Fetch the college data with the specified uuid and format it.
   * @param {string} id The uuid of the college data.
   * @return The formatted college JS object map.
   */
  async fetchAndFormatData(id) {
    const url = CollegeQueryBuilder.buildSingleQueryEndpoint(id);
    const response = await fetch(url);
    const json = await response.json();
    return this.convertJsonToObject_(json);
  }

  convertJsonToObject_(json) {
    let results = json["results"];
    let college = results[0];
    return this.converter_(college);
  }

  converter_(element) {
    return {
      schoolName : element[NAME],
      acceptanceRate : element[ACCEPTANCE_RATE].toString(),
      averageACTScore : element[ACT_SCORE].toString(),
      netCostForFirstQuintile : element[FIRST_NET_COST].toString(),
      netCostForSecondQuintile : element[SECOND_NET_COST].toString(),
      netCostForThirdQuintile : element[THIRD_NET_COST].toString(),
      netCostForFourthQuintile : element[FOURTH_NET_COST].toString(),
      netCostForFifthQuintile : element[FIFTH_NET_COST].toString(),
      cumulativeMedianDebt : element[MEDIAN_DEBT].toString()
    }
  }

}
exports = {CollegeDataHandler};

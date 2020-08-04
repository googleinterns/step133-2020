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
 * @fileoverview The builder class that builds URL endpoints to retrieve queries
 * from the DoE College Scorecard API.
 */

goog.module('datahandlers.collegequerybuilder');

// Constants used to build API queries.
const {COLLEGE_API_KEY} = goog.require('datahandlers.config');
const COLLEGE_LIST_ENDPT =
    'https://api.data.gov/ed/collegescorecard/v1/schools.json?';
const AND = '&';
const CITY = 'school.city';
const COMMA = ',';
const PAGE_SIZE_FIELD = 'per_page';
const PAGE = 'page';
const EQUAL = '=';
const API_KEY_FIELD = 'api_key=';
const QUERY_FIELDS = '_fields=';
const SORT = 'sort=';
const ASCENDING = ':asc';
const DESCENDING = ':desc';
const COLLEGES = 'school.degrees_awarded.predominant=2,3';
const PRIVATE = 'school.ownership_peps=2';
const ID = 'id';
const NAME =  'school.name';
const ACCEPTANCE_RATE = 'latest.admissions.admission_rate.overall';
const ACT_SCORE = 'latest.admissions.act_scores.midpoint.cumulative';
const ACCEPTANCE_RANGE = '__range=0..1.0';
const ACT_RANGE = '__range=0..36';
const ANNUAL_COST =
    'latest.cost.avg_net_price.private';
const FIRST_NET_COST = 
    'latest.cost.net_price.private.by_income_level.0-30000';
const SECOND_NET_COST =
    'latest.cost.net_price.private.by_income_level.30001-48000';
const THIRD_NET_COST =
    'latest.cost.net_price.private.by_income_level.48001-75000';
const FOURTH_NET_COST =
    'latest.cost.net_price.private.by_income_level.75001-110000';
const FIFTH_NET_COST =
    'latest.cost.net_price.private.by_income_level.110001-plus';
const MEDIAN_DEBT = 'latest.aid.median_debt.completers.overall';
const STATE = 'school.state';

const SORT_PARAMS_MAP = 
    new Map()
        .set(NAME, 'School Name')
        .set(ACCEPTANCE_RATE, 'Acceptance Rate')
        .set(ACT_SCORE, "Average ACT Score");


class CollegeQueryBuilder {
  constructor() {}

  /**
   * Query an entire set of colleges by batchIndex and itemsPerBatch.
   * @param {number} batchIndex - The index of the next page to retrieve.
   * @param {number} itemsPerBatch - The number of colleges to retrieve.
   * @return {string}
   */
  static buildCollectionEndpoint(batchIndex, itemsPerBatch) {
    return COLLEGE_LIST_ENDPT.concat(
        COLLEGES, AND, PRIVATE, AND, QUERY_FIELDS, ID, COMMA, NAME, COMMA,
        ACCEPTANCE_RATE, COMMA, ACT_SCORE,COMMA, CITY, COMMA, STATE, AND, 
        PAGE_SIZE_FIELD, EQUAL, itemsPerBatch.toString(), AND, PAGE, EQUAL, 
        batchIndex.toString(), AND, ACCEPTANCE_RATE, ACCEPTANCE_RANGE, AND, 
        ACT_SCORE, ACT_RANGE, AND, API_KEY_FIELD, COLLEGE_API_KEY);
  }

  /**
   * Query an entire set of colleges by batchIndex and itemsPerBatch.
   * @param {number} batchIndex - The index of the next page to retrieve.
   * @param {number} itemsPerBatch - The number of colleges to retrieve.
   * @param {string} sortParam - The parameter to sortBy.
   * @param {string} sortDirection - Sort by ascending or descending.
   * @return {string} - The sorted query endpoint.
   */
  static buildSortedCollectionEndpoint(batchIndex, itemsPerBatch, sortParam, sortDirection) {
    return COLLEGE_LIST_ENDPT.concat(COLLEGES, AND, PRIVATE, AND, QUERY_FIELDS, 
      ID, COMMA, NAME, COMMA, ACCEPTANCE_RATE, COMMA, ACT_SCORE, AND, 
      PAGE_SIZE_FIELD, EQUAL, itemsPerBatch.toString(), AND, PAGE, 
      EQUAL, batchIndex.toString(), AND, ACCEPTANCE_RATE, ACCEPTANCE_RANGE, 
      AND, ACT_SCORE, ACT_RANGE, AND, SORT, sortParam.toString(), sortDirection.toString(), 
      AND, API_KEY_FIELD, COLLEGE_API_KEY);
  }

  /**
   * Query a single college by ID.
   * @param {string} id - The ID of the college to retrieve.
   * @return {string} - The endpoint for a single college query.
   */
  static buildSingleQueryEndpoint(id) {
    return COLLEGE_LIST_ENDPT.concat(
        ID, EQUAL, id.toString(), AND, QUERY_FIELDS, ID, COMMA, NAME, COMMA,
        ACCEPTANCE_RATE, COMMA, ACT_SCORE, COMMA, ANNUAL_COST, COMMA,
        FIRST_NET_COST, COMMA, SECOND_NET_COST, COMMA, THIRD_NET_COST, COMMA,
        FOURTH_NET_COST, COMMA, FIFTH_NET_COST, COMMA, MEDIAN_DEBT, AND,
        API_KEY_FIELD, COLLEGE_API_KEY);
  }
}

exports = {
  ID,
  NAME,
  ACCEPTANCE_RATE,
  ACT_SCORE,
  ANNUAL_COST,
  ASCENDING,
  DESCENDING,
  CITY,
  FIRST_NET_COST,
  SECOND_NET_COST,
  THIRD_NET_COST,
  FOURTH_NET_COST,
  FIFTH_NET_COST,
  MEDIAN_DEBT,
  SORT_PARAMS_MAP,
  STATE,
  CollegeQueryBuilder
};

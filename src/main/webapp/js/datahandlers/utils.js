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

/** @fileoverview This file contains util functions. */

goog.module('datahandlers.utils');

/** 
 * Converts camelcase string to phrases.
 * @param {string} str The camelcase string.
 * @returns {string} The formatted string.
 */
const addSpaceToCamelCase = (str) => {
  let result = str.replace( /([A-Z])/g, " $1" );
  return result.charAt(0).toUpperCase() + result.slice(1);
};

/**
 * Converts decimal to a string representing a percent.
 * @param {number} num - The decimal to convert.
 * @returns {string} - The string representation of the number as a percent.
 */
const convertToPercent = (num) => {
  num *= 100;
  return num.toFixed(/** precision= */ 2).concat(' %');
};

/**
 * Concatenates a title to a value: used to format fields in list view.
 * @param {string} title - The title to concatenate.
 * @param {string} value - The value to concatenate.
 * @returns {string} - The formatted string.
 */
 const concatTitleToValue = (title, value) => {
   return title.concat(` ${value}`);
 };

exports = {addSpaceToCamelCase, convertToPercent, concatTitleToValue};
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
 * @returns The formatted string.
 */
const addSpaceToCamelCase = (str) => {
  let result = str.replace( /([A-Z])/g, " $1" );
  return result.charAt(0).toUpperCase() + result.slice(1);
}

/**
 * This method returns a string representing a number with commas.
 * @param {number} num - The number to convert.
 * @returns {!string} - The string representation of the number with commas.
 */
const integerWithCommas = (num) => {
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

exports = {addSpaceToCamelCase, integerWithCommas};
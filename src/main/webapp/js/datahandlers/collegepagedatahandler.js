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

goog.module('finscholar.collegepageview.datahandler');
const {collegepage} = goog.require('finscholar.collegepageview.templates');

const ENDPOINT = "/college-data";

const convertFromJsonToTemplate = async (json) => {

  const data = {
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

  return data;
};

const loadCollegeJson = async () => {
  try{
    const response = await fetch(ENDPOINT);
    let json = await response.json();
    return json;
  } catch(err) {
    alert('Failed to retrieve data from a single college.');
    console.log(err);
  }
};

const loadCollegeData = async (element) => {
  let json = await loadCollegeJson();
  let data = await convertFromJsonToTemplate(json);
  console.log(data);
  const html = collegepage(data);
  element.innerHTML = html;
}

exports = {loadCollegeData};
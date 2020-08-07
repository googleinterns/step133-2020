#!/usr/bin/env python3
#
# Copyright 2019 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License..
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Program to extract number 
# of rows using Python 
import xlrd 
from Scholarship import ScholarshipBuilder

methodList = [ScholarshipBuilder.setId, ScholarshipBuilder.setSchoolsList, 
              ScholarshipBuilder.setName, ScholarshipBuilder.setIntroduction,
              ScholarshipBuilder.setYear, ScholarshipBuilder.setAmountPerYear,
              ScholarshipBuilder.setNumberOfYears, ScholarshipBuilder.setEthnicityRaceRequirements,
              ScholarshipBuilder.setLocationRequirements, ScholarshipBuilder.setFinancialRequirements,
              ScholarshipBuilder.setAcademicRequirements, ScholarshipBuilder.setOtherRequirements,
              ScholarshipBuilder.setIsRenewable, ScholarshipBuilder.setApplicationProcess,
              ScholarshipBuilder.setURL, ScholarshipBuilder.setSchoolsNameToIDMap]

#parse scholarship data from excel
# sheet: the index of sheet from 0
# start: the first row to parse
# number: total number of scholarships needs to be parsed
def parseDataFrom(sheet, start, number):

    scholarships = []
    # Give the location of the file 
    loc = ("Scholarships.xlsx") 
  
    wb = xlrd.open_workbook(loc) 
    sheet = wb.sheet_by_index(sheet) 
    for row in range(start, start+number):
        scholarship = {}
        for cell in range(0, 15):
            cellValue = sheet.cell_value(row, cell)
            if cellValue:
                methodList[cell](scholarship, cellValue)
        print(scholarship)
        ScholarshipBuilder.setSchoolsNameToIDMap(scholarship)
        scholarships.append(scholarship)
    return scholarships

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

class ScholarshipBuilder:
    @staticmethod
    def setName(scholarship, name):
        scholarship["scholarshipName"] = name

    @staticmethod
    def setId(scholarship, id):
        scholarship["id"] = str(int(id))
    
    @staticmethod
    def setSchoolsList(scholarship, schoolsList):
        scholarship["schoolsList"] = schoolsList.split(",")
    
    @staticmethod
    def setURL(scholarship, url):
        scholarship["URL"] = url
    
    @staticmethod
    def setYear(scholarship, year):
        scholarship["year"] = year
    
    @staticmethod
    def setIntroduction(scholarship, intro):
        scholarship["introduction"] = intro
    
    @staticmethod
    def setIsRenewable(scholarship, isRenewable):
        scholarship["isRenewable"] = isRenewable == "yes" or isRenewable == "Yes"
    
    @staticmethod
    def setNumberOfYears(scholarship, years):
        scholarship["numberOfYears"] = years

    @staticmethod
    def setAmountPerYear(scholarship, amount):
        scholarship["amountPerYear"] = amount
    
    @staticmethod
    def setLocationRequirements(scholarship, locationRequirements):
        scholarship["locationRequirements"] = locationRequirements.split(';')

    @staticmethod
    def setAcademicRequirements(scholarship, academicRequirements):
        scholarship["academicRequirements"] = academicRequirements.split(';')

    @staticmethod
    def setEthnicityRaceRequirements(scholarship, ethnicityRaceRequirements):
        scholarship["ethnicityRaceRequirements"] = ethnicityRaceRequirements.split(';')

    @staticmethod
    def setGenderRequirements(scholarship, genders):
        scholarship["genderRequriements"] = genders.split(';')
    
    @staticmethod
    def setNationalOriginRequirements(scholarship, nations):
        scholarship["nationalOriginRequirements"] = nations.split(';')
    
    @staticmethod
    def setOtherRequirements(scholarship, otherRequirements):
        scholarship["otherRequirements"] = otherRequirements.split(';')
    
    @staticmethod
    def setFinancialRequirements(scholarship, financialRequirements):
        scholarship["financialRequirements"] = financialRequirements.split(';')

    @staticmethod
    def setApplicationProcess(scholarship, applicationProcess):
        scholarship["applicationProcess"] = applicationProcess

    @staticmethod
    def setSchoolsNameToIDMap(scholarship):
        nameToIDMap = {}
        for school in scholarship["schoolsList"]:
            nameToIDMap[school] = ""
        scholarship["schoolIdList"] = nameToIDMap


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

package com.google.step.finscholar.data;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class ScholarshipTest {

  private Scholarship testScholarship;

  private static final String TEST_NAME = "Scholarship Name";
  private static final List<UUID> TEST_SCHOOLS = List.of(UUID.randomUUID());
  private static final UUID TEST_ID = UUID.randomUUID();
  private static final String EMPTY_URL = "";

  @Test
  public void fullScholarshipForHispanicFemaleAndTrans() {

    List<String> GPA = List.of("maintain 'respectable GPA'");
    String AMOUNT = "full tuition";
    List<String> GENDERS = List.of(Gender.FEMALE.getValue(), Gender.TRANSGENDER.getValue());
    List<String> ETHNICITY = List.of(Ethnicity.HISPANIC.getValue());
    testScholarship = new Scholarship(
        new ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL)
        .setAcademicRequirements(GPA)
        .setAmountPerYear(AMOUNT)
        .setGenderRequirements(GENDERS)
        .setEthnicityRaceRequirements(ETHNICITY));
    Assert.assertEquals(ETHNICITY, testScholarship.getEthnicityRaceRequirements());
    Assert.assertEquals(GENDERS, testScholarship.getGenderRequirements());
    Assert.assertEquals(GPA, testScholarship.getAcademicRequirements());
    Assert.assertEquals(AMOUNT, testScholarship.getAmountPerYear().get());
  }

  @Test
  public void scholarshipWithIntroAndApplicationProcess() {
    int YEARS = 4;
    String APPLICARION = "Test parameter for application setter.";
    String INTRO = "Test parameter for introduction.";
    testScholarship = new Scholarship(
        new ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL)
            .setNumberOfYears(YEARS)
            .setApplicationProcess(APPLICARION)
            .setIntroduction(INTRO));
    Assert.assertEquals(YEARS, (int) testScholarship.getNumberOfYears().get());
    Assert.assertEquals(INTRO, testScholarship.getIntroduction().get());
    Assert.assertEquals(APPLICARION, testScholarship.getApplicationProcess().get());
  }

  @Test
  public void renewableScholarshipWithFinancialAndLocationRequirements() {
    List<String> FINANCE = List.of("Year net income less than $1,0000");
    List<String> LOCATION = List.of("the South Pole");
    testScholarship = new Scholarship(
        new ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);
        .testScholarship.setIsRenewable(true)
        .setFinancialRequirements(FINANCE)
        .setLocationRequirements(LOCATION));
    Assert.assertEquals(true, testScholarship.getIsRenewable().get());
    Assert.assertEquals(FINANCE, testScholarship.getFinancialRequirements());
    Assert.assertEquals(LOCATION, testScholarship.getLocationRequirements());
  }

  @Test
  public void scholarshipWithNationalOriginAndOtherRequirements(){
    String NATION = "South Africa";
    String REQUIREMENT1 = "other requirement 1";
    String REQUIREMENT2 = "other requirement 2";
    List<String> REQUIREMENTS = List.of(REQUIREMENT1, REQUIREMENT2);
    List<String> NATIONS = List.of(NATION);
    testScholarship = new Scholarship(
        new ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);
        .setNationalOriginRequirements(NATIONS)
        .setOtherRequirements(REQUIREMENTS));
    Assert.assertEquals(NATIONS, testScholarship.getNationalOriginRequirements());
    Assert.assertEquals(REQUIREMENTS, testScholarship.getOtherRequirements());
  }
}
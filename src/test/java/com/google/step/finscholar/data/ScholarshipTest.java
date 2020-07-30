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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ScholarshipTest {

  private Scholarship testScholarship;

  private static final String TEST_NAME = "Scholarship Name";
  private static final List<UUID> TEST_SCHOOLS =
      Collections.unmodifiableList(Arrays.asList(UUID.randomUUID()));
  private static final UUID TEST_ID = UUID.randomUUID();
  private static final String EMPTY_URL = "";

  @Test
  public void fullScholarshipForHispanicFemaleAndTrans() {

    List<String> GPA = Collections.unmodifiableList(Arrays.asList("maintain 'respectable GPA'"));
    String AMOUNT = "full tuition";
    List<String> GENDERS =
        Collections.unmodifiableList(
            Arrays.asList(Gender.FEMALE.getValue(), Gender.TRANSGENDER.getValue()));
    List<String> ETHNICITY =
        Collections.unmodifiableList(Arrays.asList(Ethnicity.HISPANIC.getValue()));
    testScholarship =
        new Scholarship.ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL)
            .setAcademicRequirements(GPA)
            .setAmountPerYear(AMOUNT)
            .setGenderRequirements(GENDERS)
            .setEthnicityRaceRequirements(ETHNICITY)
            .build();
    Assert.assertEquals(ETHNICITY, testScholarship.getEthnicityRaceRequirements());
    Assert.assertEquals(GENDERS, testScholarship.getGenderRequirements());
    Assert.assertEquals(GPA, testScholarship.getAcademicRequirements());
    Assert.assertEquals(AMOUNT, testScholarship.getAmountPerYear());
  }

  @Test
  public void scholarshipWithIntroAndApplicationProcess() {
    int YEARS = 4;
    String APPLICARION = "Test parameter for application setter.";
    String INTRO = "Test parameter for introduction.";
    testScholarship =
        new Scholarship.ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL)
            .setNumberOfYears(YEARS)
            .setApplicationProcess(APPLICARION)
            .setIntroduction(INTRO)
            .build();
    Assert.assertEquals(YEARS, (int) testScholarship.getNumberOfYears());
    Assert.assertEquals(INTRO, testScholarship.getIntroduction());
    Assert.assertEquals(APPLICARION, testScholarship.getApplicationProcess());
  }

  @Test
  public void renewableScholarshipWithFinancialAndLocationRequirements() {
    List<String> FINANCE =
        Collections.unmodifiableList(Arrays.asList("Year net income less than $1,0000"));
    List<String> LOCATION = Collections.unmodifiableList(Arrays.asList("the South Pole"));
    testScholarship =
        new Scholarship.ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL)
            .setIsRenewable(true)
            .setFinancialRequirements(FINANCE)
            .setLocationRequirements(LOCATION)
            .build();
    Assert.assertEquals(true, testScholarship.getIsRenewable());
    Assert.assertEquals(FINANCE, testScholarship.getFinancialRequirements());
    Assert.assertEquals(LOCATION, testScholarship.getLocationRequirements());
  }

  @Test
  public void scholarshipWithNationalOriginAndOtherRequirements() {
    String NATION = "South Africa";
    String REQUIREMENT1 = "other requirement 1";
    String REQUIREMENT2 = "other requirement 2";
    List<String> REQUIREMENTS =
        Collections.unmodifiableList(Arrays.asList(REQUIREMENT1, REQUIREMENT2));
    List<String> NATIONS = Collections.unmodifiableList(Arrays.asList(NATION));
    testScholarship =
        new Scholarship.ScholarshipBuilder(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL)
            .setNationalOriginRequirements(NATIONS)
            .setOtherRequirements(REQUIREMENTS)
            .build();
    Assert.assertEquals(NATIONS, testScholarship.getNationalOriginRequirements());
    Assert.assertEquals(REQUIREMENTS, testScholarship.getOtherRequirements());
  }
}

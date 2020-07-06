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

  @Before
  public void setup() {
    testScholarship = new Scholarship(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);

  }
  
  @Test
  public void fullScholarshipForHispanicFemaleAndTrans() {

    List<String> gpaRequirement = List.of("maintain 'respectable GPA'");
    String amount = "full tuition";
    List<String> genders = List.of(Gender.FEMALE.getValue(), Gender.TRANSGENDER.getValue());
    List<String> ethnicity = List.of(Ethnicity.HISPANIC.getValue());
    testScholarship.setAcademicRequirements(gpaRequirement)
                   .setAmountPerYear(amount)
                   .setGenderRequirements(genders)
                   .setEthnicityRaceRequirements(ethnicity);
    Assert.assertEquals(ethnicity, testScholarship.getEthnicityRaceRequirements());
    Assert.assertEquals(genders, testScholarship.getGenderRequirements());
    Assert.assertEquals(gpaRequirement, testScholarship.getAcademicRequirements());
    Assert.assertEquals(amount, testScholarship.getAmountPerYear().get());
  }

  @Test
  public void scholarshipWithIntroAndApplicationProcess() {
    int years = 4;
    String applicationProcess = "Test parameter for application setter.";
    String intro = "Test parameter for introduction.";
    testScholarship.setNumberOfYears(years)
                   .setApplicationProcess(applicationProcess)
                   .setIntroduction(intro);
    Assert.assertEquals(years, (int) testScholarship.getNumberOfYears().get());
    Assert.assertEquals(intro, testScholarship.getIntroduction().get());
    Assert.assertEquals(applicationProcess, testScholarship.getApplicationProcess().get());
  }

  @Test
  public void renewableScholarshipWithFinancialAndLocationRequirements() {
    List<String> finance = List.of("Year net income less than $1,0000");
    List<String> location = List.of("the South Pole");
    testScholarship.setIsRenewable(true)
                   .setFinancialRequirements(finance)
                   .setLocationRequirements(location);
    Assert.assertEquals(true, testScholarship.getIsRenewable().get());
    Assert.assertEquals(finance, testScholarship.getFinancialRequirements());
    Assert.assertEquals(location, testScholarship.getLocationRequirements());
  }

  @Test
  public void scholarshipWithNationalOriginAndOtherRequirements(){
    String nation = "South Africa";
    String requirement1 = "other requirement 1";
    String requirement2 = "other requirement 2";
    List<String> otherRequirements = List.of(requirement1, requirement2);
    List<String> nations = List.of(nation);
    testScholarship.setNationalOriginRequirements(nations)
                   .setOtherRequirements(otherRequirements)
    Assert.assertEquals(nations, testScholarship.getNationalOriginRequirements());
    Assert.assertEquals(otherRequirements, testScholarship.getOtherRequirements());
  }
}
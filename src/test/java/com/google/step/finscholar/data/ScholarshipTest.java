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
    testScholarship = new Scholarship(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);
    testScholarship.setAcademicRequirements(GPA)
                   .setAmountPerYear(AMOUNT)
                   .setGenderRequirements(GENDERS)
                   .setEthnicityRaceRequirements(ETHNICITY);
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
    testScholarship = new Scholarship(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);
    testScholarship.setNumberOfYears(YEARS)
                   .setApplicationProcess(APPLICARION)
                   .setIntroduction(INTRO);
    Assert.assertEquals(YEARS, (int) testScholarship.getNumberOfYears().get());
    Assert.assertEquals(INTRO, testScholarship.getIntroduction().get());
    Assert.assertEquals(APPLICARION, testScholarship.getApplicationProcess().get());
  }

  @Test
  public void renewableScholarshipWithFinancialAndLocationRequirements() {
    List<String> FINANCE = List.of("Year net income less than $1,0000");
    List<String> LOCATION = List.of("the South Pole");
    testScholarship = new Scholarship(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);
    testScholarship.setIsRenewable(true)
                   .setFinancialRequirements(FINANCE)
                   .setLocationRequirements(LOCATION);
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
    testScholarship = new Scholarship(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);
    testScholarship.setNationalOriginRequirements(NATIONS)
                   .setOtherRequirements(REQUIREMENTS);
    Assert.assertEquals(NATIONS, testScholarship.getNationalOriginRequirements());
    Assert.assertEquals(REQUIREMENTS, testScholarship.getOtherRequirements());
  }

  @Test
  public void testGetIsRenewable() {
    Assert.assertEquals(true, scholarshipData1.getIsRenewable().get());
  }

  @Test (expected = NoSuchElementException.class)
  public void testGetIsRenewableFailure() {
    customScholarship.getIsRenewable().get();
  }

  @Test
  public void testGetURL() {
    Assert.assertEquals(EMPTY_URL, customScholarship.getURL());
  }

  @Test
  public void testGetSchoolsList() {
    Assert.assertEquals(TEST_SCHOOLS, customScholarship.getSchoolsList());
  }

  @Test
  public void testGetAcademicRequirements() {
    List<String> expected = List.of("maintain 'respectable GPA'");
    Assert.assertEquals(expected, scholarshipData1.getAcademicRequirements());
  }

  @Test
  public void testSetAndGetIntroduction() {
    String expected = "Test.";
    Assert.assertEquals(Optional.empty(), scholarshipData1.getIntroduction());
    Assert.assertEquals(expected, scholarshipData1.setIntroduction(expected).getIntroduction().get());
  }

  @Test
  public void testSetGetNumberOfYears() {
    int expected = 4;
    Assert.assertEquals(Optional.empty(), customScholarship.getNumberOfYears());
    Assert.assertEquals(expected, 
        (int) customScholarship.setNumberOfYears(expected).getNumberOfYears().get());
  }

  @Test
  public void testSetGetGenderRequirements() {
    List<String> genders = List.of(DemographicCategories.FEMALE, DemographicCategories.TRANSGENDER);
    Assert.assertEquals(List.of(), customScholarship.getGenderRequirements());
    Assert.assertEquals(genders, 
        customScholarship.setGenderRequirements(genders).getGenderRequirements());
  }

  @Test
  public void testSetGetApplicationProcess() {
    String expected = "Test parameter for application setter and getter.";
    Assert.assertEquals(Optional.empty(), customScholarship.getApplicationProcess());
    Assert.assertEquals(expected,
        customScholarship.setApplicationProcess(expected).getApplicationProcess().get());
  }

  @Test
  public void TestSetGetFinancialRequirements(){
    List<String> expected = List.of(DemographicCategories.LOW_INCOME);
    Assert.assertEquals(List.of(), customScholarship.getGenderRequirements());
    Assert.assertEquals(expected, 
        customScholarship.setGenderRequirements(expected).getGenderRequirements());
  }

  @Test
  public void TestSetGetLocationRequirements(){
    String testLocation = "Location A";
    List<String> expected = List.of(testLocation);
    Assert.assertEquals(List.of(), customScholarship.getLocationRequirements());
    Assert.assertEquals(expected, 
        customScholarship.setLocationRequirements(expected).getLocationRequirements());
  }

  @Test
  public void TestSetGetNationalOriginRequirements(){
    String testNation = "Country A";
    List<String> expected = List.of(testNation);
    Assert.assertEquals(List.of(), customScholarship.getNationalOriginRequirements());
    Assert.assertEquals(expected, 
        customScholarship.setNationalOriginRequirements(expected).getNationalOriginRequirements());
  }

  @Test
  public void TestSetGetOtherRequirements(){
    String testString1 = "requirement 1";
    String testString2 = "requirement 2";
    List<String> expected = List.of(testString1, testString2);
    Assert.assertEquals(List.of(), customScholarship.getOtherRequirements());
    Assert.assertEquals(expected, 
        customScholarship.setOtherRequirements(expected).getOtherRequirements());
  }

}
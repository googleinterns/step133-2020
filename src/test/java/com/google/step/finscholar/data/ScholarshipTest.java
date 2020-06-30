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
  private Scholarship scholarshipData1;
  private Scholarship scholarshipData2;
  private Scholarship customScholarship;

  private static final String TEST_NAME = "Scholarship Name";
  private static final List<UUID> TEST_SCHOOLS = List.of(UUID.randomUUID());
  private static final UUID TEST_ID = UUID.randomUUID();
  private static final String EMPTY_URL = "";
   
  @Before
  public void setup() {
    scholarshipData1 = ScholarshipSamples.scholarshipSampleOne;
    scholarshipData2 = ScholarshipSamples.scholarshipSampleTwo;
    customScholarship =  new Scholarship(TEST_NAME, TEST_ID, TEST_SCHOOLS, EMPTY_URL);
  }

  @Test
  public void testGetName() {
    String expected1 = "Reginaldo Howard Memorial Scholars";
    String expected2 = "Cornelius Vanderbilt Scholarship";
    Assert.assertEquals(expected1, scholarshipData1.getScholarshipName());
    Assert.assertEquals(expected2, scholarshipData2.getScholarshipName());
    Assert.assertEquals(TEST_NAME, customScholarship.getScholarshipName());
  }

  @Test
  public void testGetUUID() {
    Assert.assertEquals(TEST_ID, customScholarship.getScholarshipUUID());
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
  public void testSetGetAcademicRequirements() {
    List<String> expected = List.of("maintain 'respectable GPA'");
    Assert.assertEquals(expected, scholarshipData1.getAcademicRequirements());
    Assert.assertEquals(List.of(), customScholarship.getAcademicRequirements());
    Assert.assertEquals(expected, 
        customScholarship.setAcademicRequirements(expected).getAcademicRequirements());
  }

    @Test
  public void testSetGetAmountPerYear() {
    String expected = "full tuition";
    Assert.assertEquals(Optional.empty(), scholarshipData1.getAmountPerYear());
    Assert.assertEquals(expected, 
        customScholarship.setAmountPerYear(expected).getAmountPerYear().get());
  }

  @Test
  public void testSetGetApplicationProcess() {
    String expected = "Test parameter for application setter and getter.";
    Assert.assertEquals(Optional.empty(), customScholarship.getApplicationProcess());
    Assert.assertEquals(expected,
        customScholarship.setApplicationProcess(expected).getApplicationProcess().get());
  }

  @Test
  public void testSetGetEthnicityRaceRequirements() {
    List<String> expected1 = List.of(DemographicCategories.BLACK_OR_AFRICAN_AMERICAN);
    Assert.assertEquals(expected1, scholarshipData1.getEthnicityRaceRequirements());
    List<String> expected2 = List.of(DemographicCategories.HISPANIC);
    Assert.assertEquals(expected2, 
        scholarshipData2.setEthnicityRaceRequirements(expected2).getEthnicityRaceRequirements());
  }

  @Test
  public void TestSetGetFinancialRequirements(){
    List<String> expected = List.of(DemographicCategories.LOW_INCOME);
    Assert.assertEquals(List.of(), customScholarship.getGenderRequirements());
    Assert.assertEquals(expected, 
        customScholarship.setGenderRequirements(expected).getGenderRequirements());
  }

  @Test
  public void testSetGetGenderRequirements() {
    List<String> genders = List.of(DemographicCategories.FEMALE, DemographicCategories.TRANSGENDER);
    Assert.assertEquals(List.of(), customScholarship.getGenderRequirements());
    Assert.assertEquals(genders, 
        customScholarship.setGenderRequirements(genders).getGenderRequirements());
  }

  @Test
  public void testSetGetIntroduction() {
    String expected = "Test parameter for introduction.";
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
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
  public void testEthnicityRaceRequirements() {
    List<String> expected = List.of(DemographicCategories.BLACK_OR_AFRICAN_AMERICAN);
    Assert.assertEquals(expected, scholarshipData1.getEthnicityRaceRequirements());
  }

  @Test
  public void testAmountPerYear() {
    Assert.assertEquals(Optional.empty(), scholarshipData1.getAmountPerYear());
  }

  @Test
  public void testGetUUID() {
    Assert.assertEquals(TEST_ID, customScholarship.getScholarshipUUID());
  }
}
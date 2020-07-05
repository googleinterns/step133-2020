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

package com.google.step.finscholar;


import com.google.step.finscholar.data.College;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** */
@RunWith(JUnit4.class)
public final class CollegeTest {
 
  private College college;

  @Before
  public void setUp() {
    college = new College("Duke");
  }

  @Test
  public void schoolNameCorrectlySet() {
    String expected = "Duke";
    String actual = college.getSchoolName();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void isUUIDGenerated() {
    UUID actual = college.getCollegeUuid();
    Assert.assertNotNull(actual);
  }

  @Test
  public void institutionTypeCorrectlySet() {
    String expected = "Public";
    college.setInstitutionType("Public");
    String actual = college.getInstitutionType();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void acceptanceRateCorrectlySet() {
    double expected = 0.07;
    college.setAcceptanceRate(0.07);
    double actual = college.getAcceptanceRate();
    Assert.assertEquals(expected, actual, 0);
  }

  @Test
  public void ACTScoreCorrectlySet() {
    double expected = 34;
    college.setAverageACTScore(34);
    double actual = college.getAverageACTScore();
    Assert.assertEquals(expected, actual, 0);
  }

  @Test
  public void usersListCorrectlySet() {
    List<UUID> users = new ArrayList<>();

    for(int i = 0; i < 5; i++) {
      users.add(UUID.randomUUID());
    }

    college.setUsersUUIDList(users);
    List<UUID> actual = college.getUsersUUIDList();
    Assert.assertEquals(users, actual);
  }

  @Test
  public void totalCostCorrectlySet() {
    int expected = 75000;
    college.setTotalCostAttendance(75000);
    int actual = college.getTotalCostAttendance();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void firstNetCostCorrectlySet() {
    int expected = 2000;
    college.setNetCostForFirstQuintile(2000);
    int actual = college.getNetCostForFirstQuintile();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void secondNetCostCorrectlySet() {
    int expected = 5000;
    college.setNetCostForSecondQuintile(5000);
    int actual = college.getNetCostForSecondQuintile();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void thirdNetCostCorrectlySet() {
    int expected = 10000;
    college.setNetCostForThirdQuintile(10000);
    int actual = college.getNetCostForThirdQuintile();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void fourthNetCostCorrectlySet() {
    int expected = 20000;
    college.setNetCostForFourthQuintile(20000);
    int actual = college.getNetCostForFourthQuintile();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void fifthNetCostCorrectlySet() {
    int expected = 40000;
    college.setNetCostForFifthQuintile(40000);
    int actual = college.getNetCostForFifthQuintile();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void medianDebtCorrectlySet() {
    int expected = 40000;
    college.setCumulativeMedianDebt(40000);
    int actual = college.getCumulativeMedianDebt();
    Assert.assertEquals(expected, actual);
  }
 
}
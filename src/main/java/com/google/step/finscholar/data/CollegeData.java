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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Holder for hardcoded College objects. */
public final class CollegeData {

  public static final List<College> COLLEGES = createColleges();

  private static List<College> createColleges() {
    List<College> colleges = new ArrayList<College>();

    List<UUID> users = new ArrayList<UUID>();
    users.add(UUID.randomUUID());
    users.add(UUID.randomUUID());
    
    College school = new College("Duke University");
    school.setInstitutionType("Private")
      .setAcceptanceRate(0.07)
      .setAverageACTScore(33)
      .setUsersUUIDList(users)
      .setTotalCostAttendance(75000)
      .setNetCostForFirstQuintile(2000)
      .setNetCostForSecondQuintile(5000)
      .setNetCostForThirdQuintile(10000)
      .setNetCostForFourthQuintile(20000)
      .setNetCostForFifthQuintile(30000)
      .setCumulativeMedianDebt(5000);
    colleges.add(school);
    return colleges;
  }
}
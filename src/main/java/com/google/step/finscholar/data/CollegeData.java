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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Holder for hardcoded College objects. */
public final class CollegeData {
  public CollegeData () {}

  public static final List<College> COLLEGES = createColleges();

  private static List<College> createColleges() {
    List<College> colleges = new ArrayList<College>();
    College school = new College("Duke University");
    school.setInstitutionType("Private");
    school.setAcceptanceRate(0.07);
    school.setAverageACTScore(33);
    List<UUID> users = new ArrayList<UUID>();
    users.add(UUID.randomUUID());
    users.add(UUID.randomUUID());
    school.setUsersUUIDList(users);
    school.setTotalCostAttendance(75000);
    school.setNetCostForFirstQuintile(2000);
    school.setNetCostForSecondQuintile(5000);
    school.setNetCostForThirdQuintile(10000);
    school.setNetCostForFourthQuintile(20000);
    school.setNetCostForFifthQuintile(30000);

    for(int i = 0; i < 5; i++) {
      colleges.add(school);
    }
    return colleges;
  }
}
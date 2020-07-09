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

/** The class with hard coded scholarship objects. */
public class ScholarshipSamples {

  private static final String NAME_1 = "Reginaldo Howard Memorial Scholars";
  // Here it should be the UUID for Duke University, but we don't have the data yet;
  private static final List<UUID> SCHOOL_NAMES_1 =
      Collections.unmodifiableList(Arrays.asList(UUID.randomUUID()));
  private static final UUID UUID_1 = UUID.randomUUID();
  private static final String SAMPLE_1_URL =
      "http://ousf.duke.edu/about-reggie-howard-scholars-program";
  private static final List<String> ACADEMIC_REQUIREMENTS_1 =
      Collections.unmodifiableList(Arrays.asList("maintain 'respectable GPA'"));
  private static final List<String> ETHNICITY_1 =
      Collections.unmodifiableList(Arrays.asList(Ethnicity.BLACK_OR_AFRICAN_AMERICAN.getValue()));

  private static final String NAME_2 = "Cornelius Vanderbilt Scholarship";
  private static final List<UUID> SCHOOL_NAMES_2 =
      Collections.unmodifiableList(Arrays.asList(UUID.randomUUID()));
  private static final UUID UUID_2 = UUID.randomUUID();
  private static final String SAMPLE_2_URL =
      "https://www.vanderbilt.edu/scholarships/signature.php";
  private static final List<String> ACADEMIC_REQUIREMENTS_2 =
      Collections.unmodifiableList(Arrays.asList("Recipient maintains at least a 3.0 GPA"));
  private static final String SUMMARY_2 =
      "The Cornelius Vanderbilt Scholarship began in 2007with a gift from the Sartain Lanier"
          + " Family Foundation of Atlanta designated to unite and strengthen Vanderbilt’s"
          + " existing full-tuition academic merit scholarships under the aegis of a coordinated"
          + " and cohesive scholarship.";
  private static final String AMOUNT_2 =
      "Full tuition, plus a one-time summer stipend for an immersive experience"
          + "following the sophomore or junior year.";

  public static Scholarship scholarshipSampleOne =
      new Scholarship.ScholarshipBuilder(NAME_1, UUID_1, SCHOOL_NAMES_1, SAMPLE_1_URL)
          .setIsRenewable(true)
          .setAcademicRequirements(ACADEMIC_REQUIREMENTS_1)
          .setEthnicityRaceRequirements(ETHNICITY_1)
          .build();

  public static Scholarship scholarshipSampleTwo =
      new Scholarship.ScholarshipBuilder(NAME_2, UUID_2, SCHOOL_NAMES_2, SAMPLE_2_URL)
          .setIsRenewable(true)
          .setAcademicRequirements(ACADEMIC_REQUIREMENTS_2)
          .setIntroduction(SUMMARY_2)
          .setAmountPerYear(AMOUNT_2)
          .build();
}

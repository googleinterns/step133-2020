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

/** The Scholarship Class. */
public class Scholarship {

  private final String scholarshipName;
  private final UUID scholarshipUUID;
  private final List<UUID> schoolsList;
  private final String amountPerYear;
  private final String applicationProcess;
  private final Integer numberOfYears;
  private final List<String> ethnicityRaceRequirements;
  private final List<String> genderRequirements;
  private final List<String> nationalOriginRequirements;
  private final List<String> locationRequirements;
  private final List<String> financialRequirements;
  private final List<String> academicRequirements;
  private final List<String> otherRequirements;
  private final String introduction;
  private final Boolean isRenewable;
  private final String URL;

  private Scholarship(ScholarshipBuilder builder) {
    this.scholarshipName = builder.scholarshipName;
    this.scholarshipUUID = builder.scholarshipUUID;
    this.schoolsList = builder.schoolsList;
    this.URL = builder.URL;
    this.amountPerYear = builder.amountPerYear;
    this.applicationProcess = builder.applicationProcess;
    this.numberOfYears = builder.numberOfYears;
    this.ethnicityRaceRequirements = builder.ethnicityRaceRequirements;
    this.genderRequirements = builder.genderRequirements;
    this.nationalOriginRequirements = builder.nationalOriginRequirements;
    this.academicRequirements = builder.academicRequirements;
    this.locationRequirements = builder.locationRequirements;
    this.financialRequirements = builder.financialRequirements;
    this.otherRequirements = builder.otherRequirements;
    this.isRenewable = builder.isRenewable;
    this.introduction = builder.introduction;
  }

  public static class ScholarshipBuilder {
    private String scholarshipName;
    private UUID scholarshipUUID;
    private List<UUID> schoolsList;
    private String amountPerYear;
    private String applicationProcess;
    private Integer numberOfYears;
    private List<String> ethnicityRaceRequirements;
    private List<String> genderRequirements;
    private List<String> nationalOriginRequirements;
    private List<String> locationRequirements;
    private List<String> financialRequirements;
    private List<String> academicRequirements;
    private List<String> otherRequirements;
    private String introduction;
    private Boolean isRenewable;
    private String URL;

    public ScholarshipBuilder(String scholarshipName, UUID scholarshipUUID, List<UUID> schoolsList, String URL) {
      this.scholarshipName = scholarshipName;
      this.scholarshipUUID = scholarshipUUID;
      this.schoolsList = schoolsList;
      this.URL = URL;
    }

    /** @return The scholarship object constructed with this builder. */
    public Scholarship build() {
      return new Scholarship(this);
    }

    /**
     * The setter for academicRequirements.
     * @param academicRequirements
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setAcademicRequirements(List<String> academicRequirements) {
      this.academicRequirements = academicRequirements;
      return this;
    }

    /**
     * The setter for amountPerYear.
     * @param amount2
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setAmountPerYear(String amountPerYear) {
      this.amountPerYear = amountPerYear;
      return this;
    }

    /**
     * The setter for applicationProcess.
     * @param applicationProcess
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setApplicationProcess(String applicationProcess) {
      this.applicationProcess = applicationProcess;
      return this;
    }

    /**
     * The setter for ethnicityRaceRequirements.
     * @param ethnicityRaceRequirements
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setEthnicityRaceRequirements(List<String> ethnicityRaceRequirements) {
      this.ethnicityRaceRequirements = ethnicityRaceRequirements;
      return this;
    }

    /**
     * The setter for financial requirements.
     * @param financialRequirements
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setFinancialRequirements(List<String> financialRequirements) {
      this.financialRequirements = financialRequirements;
      return this;
    }

    /**
     * The setter for genderRequirement.
     * @param genderRequirements A list of genders.
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setGenderRequirements(List<String> genderRequirements) {
      this.genderRequirements = genderRequirements;
      return this;
    }

    /**
     * The setter for location requirements.
     * @param locationRequirements A list of locations.
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setLocationRequirements(List<String> locationRequirements) {
      this.locationRequirements = locationRequirements;
      return this;
    }

    /**
     * The setter for nationalOriginRequirements.
     * @param nationalOriginRequirements A list of nationalities.
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setNationalOriginRequirements(List<String> nationalOriginRequirements) {
      this.nationalOriginRequirements = nationalOriginRequirements;
      return this;
    }

    /**
     * The setter for numberOfYears.
     * @param numberOfYears The number of years the scholarship is provided to one student.
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setNumberOfYears(int numberOfYears) {
      this.numberOfYears = numberOfYears;
      return this;
    }
  
    /**
     * The setter for otherRequirements.
     * @param otherRequirements A list of requirements that don't have clear category.
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setOtherRequirements(List<String> otherRequirements) {
      this.otherRequirements = otherRequirements;
      return this;
    }

    /**
     * The setter for isRenewable.
     * @param isRenewable The new isRenewable boolean value.
     * @return The updated scholarship object.
     */
    public ScholarshipBuilder setIsRenewable(boolean isRenewable) {
      this.isRenewable = isRenewable;
      return this;
    }

    /**
     * The setter for introduction.
     * @param intro The new introduction.
     * @return The renewed scholarship object.
     */
    public ScholarshipBuilder setIntroduction(String introduction) {
      this.introduction = introduction;
      return this;
    }
  }

  /** 
   * The getter of academic requirements list. 
   * @return The academic requirements list for the scholarship. 
   */
  public List<String> getAcademicRequirements() {
    return this.academicRequirements;
  }

  /**
   * The getter of applicationProcess.
   * @return The string overview of application process.
   */
  public String getApplicationProcess() {
    return this.applicationProcess;
  }

  /** 
   * The getter of the amount of money sponsored per year. 
   * @return The amount given to one individual per year.
   */
  public String getAmountPerYear() {
    return this.amountPerYear;
  }

  /** 
   * The getter of the list of ethnicity and race requirements. 
   * @return The list of ethnicity and race restrictions.
   */
  public List<String> getEthnicityRaceRequirements() {
    return this.ethnicityRaceRequirements;
  }

  /**
   * The getter of the list of financial background requirements.
   * @return The list of financial requirements.
   */
  public List<String> getFinancialRequirements() {
    return this.financialRequirements;
  }

  /**
   * The getter for gender restrictions of the scholarship.
   * @return The list of gender requirements.
   */
  public List<String> getGenderRequirements() {
    return this.genderRequirements;
  }

  /**
   * The getter for geographic restrictions of the scholarship;
   * @return The list of location requirements.
   */
  public List<String> getLocationRequirements() {
    return this.locationRequirements;
  }

  /**
   * The getter for nationality restrictions of the scholarship;
   * @return The list of national origin requirements.
   */
  public List<String> getNationalOriginRequirements() {
    return this.nationalOriginRequirements;
  }

  /**
   * The getter of the number of years the scholarship is offered to one student.
   * @return The duration of the scholarship in years.
   */
  public Integer getNumberOfYears() {
    return this.numberOfYears;
  }

  /**
   * The getter of the list of other requirements.
   * @return The list of other requirements.
   */
  public List<String> getOtherRequirements() {
    return this.otherRequirements;
  }

  /**
   * The getter for the scholarship's name.
   * @return The name of the scholarship.
   */
  public String getScholarshipName() {
    return this.scholarshipName;
  }

  /**
   * The getter for the UUID.
   * @return the UUID of the scholarship.
   */
  public UUID getScholarshipUUID() {
    return this.scholarshipUUID;
  }
  
  /**
   * The getter for the list of schools providing the scholarship.
   * @return The schools list.
   */
  public List<UUID> getSchoolsList() {
    return this.schoolsList;
  }

  /**
   * The getter of the URL to the official website for the scholarship.
   * @return The URL.
   */
  public String getURL() {
    return this.URL;
  }

  /**
   * The getter of the introduction.
   * @return The optional containing introduction.
   */
  public String getIntroduction() {
    return this.introduction;
  }

  /**
   * The getter of isRenewable.
   * @return The Optional of boolean for isRenewable.
   */
  public Boolean getIsRenewable() {
    return this.isRenewable;
  }
}
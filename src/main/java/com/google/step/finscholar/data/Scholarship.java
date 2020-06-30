package com.google.step.finscholar.data;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

/**
 * The Scholarship Class.
 */
public class Scholarship {

  private final String scholarshipName;
  private final UUID scholarshipUUID;
  private final List<UUID> schoolsList;
  // For amountPerYear, I use String instead of integer because lots of expression for amount is 
  // irregular, such as "full tuition with extra $1000 bonus", "full tuition with room & board" etc..
  private Optional<String> amountPerYear;
  private Optional<String> applicationProcess;
  private Optional<Integer> numberOfYears;
  private List<String> ethnicityRaceRequirements;
  private List<String> genderRequirements;
  private List<String> nationalOriginRequirements;
  private List<String> locationRequirements;
  private List<String> financialRequirements;
  private List<String> academicRequirements;
  private List<String> otherRequirements;
  // Introduction can be any background knowledge of the scholarship.
  private Optional<String> introduction;
  private Optional<Boolean> isRenewable;
  private final String URL;

  public Scholarship(String name, UUID uuid, List<UUID> schoolsList, String URL) {
    this.scholarshipName = name;
    this.scholarshipUUID = UUID.fromString(name);
    this.schoolsList = schoolsList;
    this.URL = URL;
    this.amountPerYear = Optional.empty();
    this.applicationProcess = Optional.empty();
    this.numberOfYears = Optional.empty();
    this.ethnicityRaceRequirements = List.of();
    this.genderRequirements = List.of();
    this.nationalOriginRequirements = List.of();
    this.locationRequirements = List.of();
    this.financialRequirements = List.of();
    this.otherRequirements = List.of();
    this.isRenewable = Optional.empty();
  }

  /** 
   * The getter of academic requiments list. 
   * @return The academic requiments list for the scholarship. 
   */
  public List<String> getAcademicRequirements() {
    return academicRequirements;
  }

  /**
   * The getter of applicationProcess.
   * @return The string overview of application process.
   */
  public Optional<String> getApplicationProcess() {
    return this.applicationProcess;
  }

  /** 
   * The getter of the amount of money sponsored per year. 
   * @return The amount given to one individual per year.
   */
  public Optional<String> getAmountPerYear() {
    return amountPerYear;
  }

  /** 
   * The getter of the list of ethnicity and race requirements. 
   * @return The list of ethnicity and race restrictions.
   */
  public List<String> getEthnicityRaceRequirements() {
    return ethnicityRaceRequirements;
  }

  /**
   * The getter of the list of finantial background requirments.
   * @return The list of finantial requirements.
   */
  public List<String> getFinancialRequirements() {
    return financialRequirements;
  }

  /**
   * The getter for gender restriction of the scholarship.
   * @return The list of gender requirement.
   */
  public List<String> getGenderRequirements() {
    return genderRequirements;
  }

  /**
   * The getter for geographic restriction of the scholarship;
   * @return The list of location requirement.
   */
  public List<String> getLocationRequirements() {
    return locationRequirements;
  }

  /**
   * The getter for nationality restriction of the scholarship;
   * @return The list of national origin requirements.
   */
  public List<String> getNationalOriginRequirements() {
    return nationalOriginRequirements;
  }

  /**
   * The getter for the number of year available to one student.
   * @return The duraton of the scholarship in year.
   */
  public Optional<Integer> getNumberOfYears() {
    return numberOfYears;
  }

  /**
   * The getter for the list of other requirements.
   * @return The list of other requirements.
   */
  public List<String> getOtherRequirements() {
    return otherRequirements;
  }

  /**
   * The getter for the scholarship's name.
   * @return The name of the scholarship.
   */
  public String getScholarshipName() {
    return scholarshipName;
  }

  /**
   * The getter for the UUID.
   * @return the UUID of the scholarship.
   */
  public UUID getScholarshipUUID() {
    return scholarshipUUID;
  }
  
  /**
   * The getter for the list of schools providing the scholarship.
   * @return The schoollist.
   */
  public List<UUID> getSchoolsList() {
    return schoolsList;
  }

  /**
   * The getter of the URL to the official website for the scholarship.
   * @return The URL.
   */
  public String getURL() {
    return URL;
  }

  /**
   * The getter of the introduction.
   * @return The optional containing introduction.
   */
  public Optional<String> getIntroduction() {
    return introduction;
  }

  /**
   * The getter of isRenewable.
   * @return The Optional of boolean for isRenewable.
   */
  public Optional<Boolean> getIsRenewable() {
    return isRenewable;
  }

  /**
   * The setter for academicRequirements.
   * @param academicRequirements
   * @return The updated scholarship object.
   */
  public Scholarship setAcademicRequirements(List<String> academicRequirements) {
    this.academicRequirements = academicRequirements;
    return this;
  }

  /**
   * The setter for amountPerYear.
   * @param amount2
   * @return The updated scholarship object.
   */
  public Scholarship setAmountPerYear(String amount2) {
    this.amountPerYear = Optional.of(amount2);
    return this;
  }

  /**
   * The setter for applicationProcess.
   * @param applicationProcess
   * @return The updated scholarship object.
   */
  public Scholarship setApplicationProcess(Optional<String> applicationProcess) {
    this.applicationProcess = applicationProcess;
    return this;
  }

  /**
   * The setter for ethnicityReaceRequirements.
   * @param ethnicityRaceRequirements
   * @return The updated scholarship object.
   */
  public Scholarship setEthnicityRaceRequirements(List<String> ethnicityRaceRequirements) {
    this.ethnicityRaceRequirements = ethnicityRaceRequirements;
    return this;
  }

  /**
   * The setter for finantial requirements.
   * @param financialRequirements
   * @return The updated scholarship object.
   */
  public Scholarship setFinancialRequirements(List<String> financialRequirements) {
    this.financialRequirements = financialRequirements;
    return this;
  }

  /**
   * The setter for genderRequirement.
   * @param genderRequirements A list of genders.
   * @return The updated scholarship object.
   */
  public Scholarship setGenderRequirements(List<String> genderRequirements) {
    this.genderRequirements = genderRequirements;
    return this;
  }

  /**
   * The setter for location requirements.
   * @param locationRequirements A list of locations.
   * @return The updated scholarship object.
   */
  public Scholarship setLocationRequirements(List<String> locationRequirements) {
    this.locationRequirements = locationRequirements;
    return this;
  }

  /**
   * The setter for nationalOriginRequirements.
   * @param nationalOriginRequirements A list of nationalities.
   * @return The updated scholarship object.
   */
  public Scholarship setNationalOriginRequirements(List<String> nationalOriginRequirements) {
    this.nationalOriginRequirements = nationalOriginRequirements;
    return this;
  }

  /**
   * The setter for numberOfYears.
   * @param numberOfYears The number of years the scholarship is provided to one student.
   * @return The updated scholarship object.
   */
  public Scholarship setNumberOfYears(int numberOfYears) {
    this.numberOfYears = Optional.of(numberOfYears);
    return this;
  }
  
  /**
   * The setter for otherRequirements.
   * @param otherRequirements A list of requirements that don't have clear category.
   * @return The updated scholarship object.
   */
  public Scholarship setOtherRequirements(List<String> otherRequirements) {
    this.otherRequirements = otherRequirements;
    return this;
  }

  /**
   * The setter for isRenewable.
   * @param isRenewable The new isRenewable boolean value.
   * @return The updated scholarship object.
   */
  public Scholarship setIsRenewable(boolean isRenewable) {
    this.isRenewable = Optional.of(isRenewable);
    return this;
  }

  /**
   * The setter for introduction.
   * @param intro The new introduction.
   * @return The renewed scholarship object.
   */
  public Scholarship setIntroduction(String intro) {
    this.introduction = Optional.of(intro);
    return this;
  }
}
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

/** An object representing a College using the Builder design pattern. */
public class College {
  private String schoolName;
  private String collegeUUID;
  private String institutionType;
  private double acceptanceRate;
  private double averageACTScore;
  private int totalCostAttendance;
  private int netCostForFirstQuintile; // $0-$30,000.
  private int netCostForSecondQuintile; // $30,001-$48,000.
  private int netCostForThirdQuintile; // $48,001-$75,000.
  private int netCostForFourthQuintile; // $75,001-$110,000.
  private int netCostForFifthQuintile; // $110,000+.
  private int cumulativeMedianDebt;

  // We need a public, no argument constructor to deserialize an object.
  public College() {
    
  }

  private College(CollegeBuilder builder) {
    this.schoolName = builder.schoolName;
    this.collegeUUID = builder.collegeUUID;
    this.institutionType = builder.institutionType;
    this.acceptanceRate = builder.acceptanceRate;
    this.averageACTScore = builder.averageACTScore;
    this.totalCostAttendance = builder.totalCostAttendance;
    this.netCostForFirstQuintile = builder.netCostForFirstQuintile;
    this.netCostForSecondQuintile = builder.netCostForSecondQuintile;
    this.netCostForThirdQuintile = builder.netCostForThirdQuintile;
    this.netCostForFourthQuintile = builder.netCostForFourthQuintile;
    this.netCostForFifthQuintile = builder.netCostForFifthQuintile;
    this.cumulativeMedianDebt = builder.cumulativeMedianDebt;
  }

  

  public static class CollegeBuilder {
    private final String schoolName; // Required 
    private final String collegeUUID;
    private String institutionType; // The rest are optional.
    private double acceptanceRate;
    private double averageACTScore;
    private int totalCostAttendance;
    private int netCostForFirstQuintile; // $0-$30,000.
    private int netCostForSecondQuintile; // $30,001-$48,000.
    private int netCostForThirdQuintile; // $48,001-$75,000.
    private int netCostForFourthQuintile; // $75,001-$110,000.
    private int netCostForFifthQuintile; // $110,000+.
    private int cumulativeMedianDebt;

    public CollegeBuilder(String schoolName, String collegeUUID) {
      this.schoolName = schoolName;
      this.collegeUUID = collegeUUID;
    }

    // Setter methods.

    /** @param newInstitutionType - Public, private, for-profit, etc. */
    public CollegeBuilder setInstitutionType(String newInstitutionType) {
      this.institutionType = newInstitutionType;
      return this;
    }

    /**
     * @param newAcceptanceRate - Acceptance Rate - Total Accepted/Total applicants.
     */
    public CollegeBuilder setAcceptanceRate(double newAcceptanceRate) {
      this.acceptanceRate = newAcceptanceRate;
      return this;
    }

    /** @param newACTScore - Median ACT Score of students accepted. */
    public CollegeBuilder setAverageACTScore(double newACTScore) {
      this.averageACTScore = newACTScore;
      return this;
    }

    /**
     * @param newTotalCost - Average total cost of attendance without financial Aid.
     */
    public CollegeBuilder setTotalCostAttendance(int newTotalCost) {
      this.totalCostAttendance = newTotalCost;
      return this;
    }

    /**
     * @param newNetCost - Average net cost for the income quintile between
     *   $0-$30,000.
     */
    public CollegeBuilder setNetCostForFirstQuintile(int newNetCost) {
      this.netCostForFirstQuintile = newNetCost;
      return this;
    }

    /**
     * @param newNetCost - Average net cost for the income quintile between
     *   $30,001-$48,000.
     */
    public CollegeBuilder setNetCostForSecondQuintile(int newNetCost) {
      this.netCostForSecondQuintile = newNetCost;
      return this;
    }

    /**
     * @param newNetCost - Average net cost for the income quintile between
     *   $48,001-$75,000.
     */
    public CollegeBuilder setNetCostForThirdQuintile(int newNetCost) {
      this.netCostForThirdQuintile = newNetCost;
      return this;
    }

    /**
     * @param newNetCost - Average net cost for the income quintile between
     *   $75,001-$110,000.
     */
    public CollegeBuilder setNetCostForFourthQuintile(int newNetCost) {
      this.netCostForFourthQuintile = newNetCost;
      return this;
    }

    /**
     * @param newNetCost - Average net cost for the income quintile between
     *   $110,000+.
     */
    public CollegeBuilder setNetCostForFifthQuintile(int newNetCost) {
      this.netCostForFifthQuintile = newNetCost;
      return this;
    }

    /**
     * @param newCumulativeMedianDebt - The accumulated median debt once the student
     *   has graduated.
     */
    public CollegeBuilder setCumulativeMedianDebt(int newCumulativeMedianDebt) {
      this.cumulativeMedianDebt = newCumulativeMedianDebt; 
      return this;
    }

    /**
     * @return - The College object just built.
     */
    public College build() {
      return new College(this);
    }
  }

  // Getter methods for the College Object.

  /** @return - Name of the college. */
  public String getSchoolName() {
    return this.schoolName;
  }

  /** @return - UUID for the college. */
  public String getCollegeUuid() {
    return this.collegeUUID;
  }

  /** @return - Institutiton Type: Public, private, for-profit, etc. */
  public String getInstitutionType() {
    return this.institutionType;
  }

  /** @return - Acceptance Rate - Total Accepted/Total applicants. */
  public double getAcceptanceRate() {
    return this.acceptanceRate;
  }

  /** @return - Median ACT Score of students accepted. */

  public double getAverageACTScore() {
    return this.averageACTScore;
  }

  /** @return - Average total cost of attendance without financial Aid. */
  public int getTotalCostAttendance() {
    return this.totalCostAttendance;
  }

  /** @return - Average net cost for the income quintile between $0-$30,000. */
  public int getNetCostForFirstQuintile() {
    return this.netCostForFirstQuintile;
  }

  /**
   * @return - Average net cost for the income quintile between $30,001-$48,000.
   */
  public int getNetCostForSecondQuintile() {
    return this.netCostForSecondQuintile;
  }

  /**
   * @return - Average net cost for the income quintile between $48,001-$75,000.
   */
  public int getNetCostForThirdQuintile() {
    return this.netCostForThirdQuintile;
  }

  /**
   * @return - Average net cost for the income quintile between $75,001-$110,000.
   */
  public int getNetCostForFourthQuintile() {
    return this.netCostForFourthQuintile;
  }

  /** @return - Average net cost for the income quintile between $110,000+. */
  public int getNetCostForFifthQuintile() {
    return this.netCostForFifthQuintile;
  }

  /** @return - The accumulated median debt once the student has graduated. */
  public int getCumulativeMedianDebt() {
    return this.cumulativeMedianDebt;
  }

}
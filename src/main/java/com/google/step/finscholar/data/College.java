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
import java.util.UUID;

/** An object representing a College. */
public final class College {

  private String schoolName;
  private UUID collegeUuid;
  private String institutionType;
  private double acceptanceRate;
  private double averageACTScore;
  private List<UUID> usersUUIDList;
  private int totalCostAttendance;
  private int netCostForFirstQuintile; // $0-$30,000.
  private int netCostForSecondQuintile; // $30,001-$48,000.
  private int netCostForThirdQuintile;  // $48,001-$75,000.
  private int netCostForFourthQuintile; // $75,001-$110,000.
  private int netCostForFifthQuintile; //$110,000+.
  private int cumulativeMedianDebt;

  // Constructor.

  public College(String schoolName) {
    this.schoolName = schoolName;
    this.collegeUuid = generateUUID();
  }

  // Private helper methods.

  private UUID generateUUID() {
    return UUID.randomUUID();
  }

  // Getter methods.

  /**
   * @return - Name of the college.
   */
  public String getSchoolName() { return this.schoolName; }

  /**
   * @return - UUID for the college.
   */
  public UUID getCollegeUuid() { return this.collegeUuid; }

  /**
   * @return - Institutiton Type: Public, private, for-profit, etc.
   */
  public String getInstitutionType() { return this.institutionType; }

  /**
   * @return - Acceptance Rate - Total Accepted/Total applicants.
   */
  public double getAcceptanceRate() { return this.acceptanceRate; }

  /**
   * @return - Median ACT Score of students accepted.
   */
  public double getAverageACTScore() { return this.averageACTScore; }

  /**
   * @return - Users associated with the college.
   */
  public List<UUID> getUsersUUIDList() { return this.usersUUIDList; }

  /**
   * @return - Average total cost of attendance without financial Aid.
   */
  public int getTotalCostAttendance() { return this.totalCostAttendance; }

  /**
   * @return - Average net cost for the income quintile between $0-$30,000.
   */
  public int getNetCostForFirstQuintile() { return this.netCostForFirstQuintile; }

  /**
   * @return - Average net cost for the income quintile between $30,001-$48,000.
   */
  public int getNetCostForSecondQuintile() { return this.netCostForSecondQuintile; }

  /**
   * @return - Average net cost for the income quintile between $48,001-$75,000.
   */
  public int getNetCostForThirdQuintile() { return this.netCostForThirdQuintile; }

  /**
   * @return - Average net cost for the income quintile between $75,001-$110,000.
   */
  public int getNetCostForFourthQuintile() { return this.netCostForFourthQuintile; }

  /**
   * @return - Average net cost for the income quintile between $110,000+.
   */
  public int getNetCostForFifthQuintile() { return this.netCostForFifthQuintile; }

  // Setter methods.

  /**
   * @param newInstitutionType - Public, private, for-profit, etc.
   */
  public void setInstitutionType(String newInstitutionType) { this.institutionType = newInstitutionType; }

  /**
   * @param newAcceptanceRate - Acceptance Rate - Total Accepted/Total applicants.
   */
  public void setAcceptanceRate(double newAcceptanceRate) { this.acceptanceRate = newAcceptanceRate; }
  
  /**
   * @param newACTScore - Median ACT Score of students accepted.
   */
  public void setAverageACTScore(double newACTScore) { this.averageACTScore = newACTScore; }

  /**
   * @param newUsersList - Users associated with the college.
   */
  public void setUsersUUIDList(List<UUID> newUsersList) { this.usersUUIDList = newUsersList; }

  /**
   * @param newTotalCost - Average total cost of attendance without financial Aid.
   */
  public void setTotalCostAttendance(int newTotalCost) { this.totalCostAttendance = newTotalCost; }

  /**
   * @param newNetCost - Average net cost for the income quintile between $0-$30,000.
   */
  public void setNetCostForFirstQuintile(int newNetCostFirstQuintile) { this.netCostForFirstQuintile = newNetCost; }

  /**
   * @param newNetCost - Average net cost for the income quintile between $30,001-$48,000.
   */
  public void setNetCostForSecondQuintile(int newNetCost) { this.netCostForSecondQuintile = newNetCost; }

  /**
   * @param newNetCost - Average net cost for the income quintile between $48,001-$75,000.
   */
  public void setNetCostForThirdQuintile(int newNetCost) { this.netCostForThirdQuintile = newNetCost; }

  /**
   * @param newNetCost - Average net cost for the income quintile between $75,001-$110,000.
   */
  public void setNetCostForFourthQuintile(int newNetCost) { this.netCostForFourthQuintile = newNetCost; }

  /**
   * @param newNetCost - Average net cost for the income quintile between $110,000+.
   */
  public void setNetCostForFifthQuintile(int newNetCost) { this.netCostForFifthQuintile = newNetCost; }
}
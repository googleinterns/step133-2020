# Program to extract number 
# of rows using Python 
import xlrd 
from Scholarship import ScholarshipBuilder

methodList = [ScholarshipBuilder.setId, ScholarshipBuilder.setSchoolsList, 
              ScholarshipBuilder.setName, ScholarshipBuilder.setIntroduction,
              ScholarshipBuilder.setYear, ScholarshipBuilder.setAmountPerYear,
              ScholarshipBuilder.setNumberOfYears, ScholarshipBuilder.setEthnicityRaceRequirements,
              ScholarshipBuilder.setLocationRequirements, ScholarshipBuilder.setFinancialRequirements,
              ScholarshipBuilder.setAcademicRequirements, ScholarshipBuilder.setOtherRequirements,
              ScholarshipBuilder.setIsRenewable, ScholarshipBuilder.setApplicationProcess,
              ScholarshipBuilder.setURL, ScholarshipBuilder.setSchoolsNameToIDMap]

#parse scholarship data from excel
# sheet: the index of sheet from 0
# start: the first row to parse
# number: total number of scholarships needs to be parsed
def parseDataFrom(sheet, start, number):

    scholarships = []
    # Give the location of the file 
    loc = ("Scholarships.xlsx") 
  
    wb = xlrd.open_workbook(loc) 
    sheet = wb.sheet_by_index(sheet) 
    for row in range(start, start+number):
        scholarship = {}
        for cell in range(0, 15):
            cellValue = sheet.cell_value(row, cell)
            if cellValue:
                methodList[cell](scholarship, cellValue)
        print(scholarship)
        ScholarshipBuilder.setSchoolsNameToIDMap(scholarship)
        scholarships.append(scholarship)
    return scholarships

class ScholarshipBuilder:
    @staticmethod
    def setName(scholarship, name):
        scholarship["scholarshipName"] = name

    @staticmethod
    def setId(scholarship, id):
        scholarship["id"] = str(int(id))
    
    @staticmethod
    def setSchoolsList(scholarship, schoolsList):
        scholarship["schoolsList"] = schoolsList.split(",")
    
    @staticmethod
    def setURL(scholarship, url):
        scholarship["URL"] = url
    
    @staticmethod
    def setYear(scholarship, year):
        scholarship["year"] = year
    
    @staticmethod
    def setIntroduction(scholarship, intro):
        scholarship["introduction"] = intro
    
    @staticmethod
    def setIsRenewable(scholarship, isRenewable):
        scholarship["isRenewable"] = isRenewable == "yes" or isRenewable == "Yes"
    
    @staticmethod
    def setNumberOfYears(scholarship, years):
        scholarship["numberOfYears"] = years

    @staticmethod
    def setAmountPerYear(scholarship, amount):
        scholarship["amountPerYear"] = amount
    
    @staticmethod
    def setLocationRequirements(scholarship, locationRequirements):
        scholarship["locationRequirements"] = locationRequirements.split(';')

    @staticmethod
    def setAcademicRequirements(scholarship, academicRequirements):
        scholarship["academicRequirements"] = academicRequirements.split(';')

    @staticmethod
    def setEthnicityRaceRequirements(scholarship, ethnicityRaceRequirements):
        scholarship["ethnicityRaceRequirements"] = ethnicityRaceRequirements.split(';')

    @staticmethod
    def setGenderRequirements(scholarship, genders):
        scholarship["genderRequriements"] = genders.split(';')
    
    @staticmethod
    def setNationalOriginRequirements(scholarship, nations):
        scholarship["nationalOriginRequirements"] = nations.split(';')
    
    @staticmethod
    def setOtherRequirements(scholarship, otherRequirements):
        scholarship["otherRequirements"] = otherRequirements.split(';')
    
    @staticmethod
    def setFinancialRequirements(scholarship, financialRequirements):
        scholarship["financialRequirements"] = financialRequirements.split(';')

    @staticmethod
    def setApplicationProcess(scholarship, applicationProcess):
        scholarship["applicationProcess"] = applicationProcess

    @staticmethod
    def setSchoolsNameToIDMap(scholarship):
        nameToIDMap = {}
        for school in scholarship["schoolsList"]:
            nameToIDMap[school] = ""
        scholarship["schoolIdList"] = nameToIDMap


const restructureScholarshipObject = (flatScholarship) => {
  return {
    'introduction' : wrapIntro(flatScholarship),
    'requirements' : removeInvalidFields(wrapRequirements(flatScholarship)),
    'applicationNote' : removeinvalidFields(wrapApplicationNote(flatScholarship)),
  };
};

const removeInvalidFields = (obj) => {
  for (key in obj) {
    if (obj[key] === [] || obj[key] === {value: null}) {
      delete obj.key;
    }
  }
}

const wrapIntro = ({scholarshipName, scholarshipUUID, introduction, URL}) => 
                      ({scholarshipName, scholarshipUUID, introduction, URL,});

const wrapRequirements = ({academicReuirements, 
                           ethnicityRaceRequirements, 
                           financialRequirements, 
                           genderRequirements,
                           locationrequirements, 
                           nationalOriginRequirements,
                           otherRequirements}) => ({
                             academicReuirements, 
                             ethnicityRaceRequirements, 
                             financialRequirements,
                             genderRequirements, 
                             locationrequirements, 
                             nationalOriginRequirements,
                             otherRequirements,
                           });
                           
const wrapApplicationNote = ({isRenewable, applicationProcess, amountPerYear, numberOfYears}) =>
                                ({amountPerYear,
                                  applicationProcess,
                                  isRenewable,
                                  numberOfYears,
                                });
const test = {
  scholarshipName : 'name',
  scholarshipUUID : 'uuid',
  scholarshipSchool : 'abc',
}

console.log(unwrapIntro(test));
# importing the requests library 
import requests 
from firebase_admin import firestore

test_list = ["Vanderbilt University", "Yale University", "Fordham University"]
# location given here 

def get_id_map(name_list, doc):
    id_dict = {}
    for name in name_list:
        URL = "https://api.data.gov/ed/collegescorecard/v1/schools.json?school.name=" + name.replace(" ", "%20") + "&fields=id&api_key=qJUoSqiDjTmw1kiszqkRHKfDtePtrQgl7YPJ8XTh"

        # sending get request and saving the response as response object 
        r = requests.get(url = URL) 

        try:
            # extracting data in json format 
            data = r.json() 

            # extracting latitude, longitude and formatted address 
            # of the first matching location 
            id = data['results'][0]['id']

            id_dict[name] = str(id)
        except:
            id = "-"
            print("An Error happends!")
            id_dict[name] = str(id)
    print(id_dict)
    doc.set({'schoolNameToIdMap': id_dict}, merge=True)
    doc.update({'schoolIdList' : firestore.DELETE_FIELD})

def get_id_list(doc, doc_dict):
    print(doc_dict)
    try:
      nameToIdMap = doc_dict["schoolNameToIdMap"]
      id_list = list(nameToIdMap.values())
      doc.set({'schoolIdList': id_list}, merge=True)
    except:
      print("an error occurs!")



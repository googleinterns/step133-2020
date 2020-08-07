#!/usr/bin/env python3
#
# Copyright 2019 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License..
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


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



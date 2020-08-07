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

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from DoE import get_id_list
from DoE import get_id_map

#gets the database.
def get_cred():
    cred = credentials.ApplicationDefault()
    firebase_admin.initialize_app(cred, {
        'projectId': 'viewing-step-2020-v2',
    })

    db = firestore.client()
    return db

# Delete all documents in a firestore collection.
def delete_collection(coll_ref, batch_size):
    docs = coll_ref.limit(batch_size).stream()
    deleted = 0

    for doc in docs:
        print(f'Deleting doc {doc.id} => {doc.to_dict()}')
        doc.reference.delete()
        deleted = deleted + 1

    if deleted >= batch_size:
        return delete_collection(coll_ref, batch_size)

#Delete the entire scholarship collection
def delete_scholarship_collection():
    db = get_cred()
    doc_ref = db.collection('scholarships')

    delete_collection(doc_ref, 5)

def iterate_all_data_and_get_id_list():
    db = get_cred()
    docs = db.collection('scholarships').stream()

    for doc in docs:
        doc_dict = doc.to_dict()
        doc_ref = db.collection('scholarships').document(doc_dict['id'])
        get_id_list(doc_ref, doc_dict)

def iterate_all_data_and_get_id_map():
    db = get_cred()
    docs = db.collection('scholarships').stream()

    for doc in docs:
        doc_dict = doc.to_dict()
        name_list = doc_dict['schoolsList']
        doc_ref = db.collection('scholarships').document(doc_dict['id'])
        get_id_map(name_list, doc_ref)

        
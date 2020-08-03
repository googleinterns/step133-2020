import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from DoE import get_id_list

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

def update_array(collectionName, documentName, fieldName, value):
    city_ref = db.collection(collectionName).document(documentName)

    # Atomically add a new region to the 'regions' array field.
    city_ref.update({fieldName: firestore.ArrayUnion([value])})

def delete_scholarship_collection():
    cred = credentials.ApplicationDefault()
    firebase_admin.initialize_app(cred, {
        'projectId': 'viewing-step-2020-v2',
    })

    db = firestore.client()
    doc_ref = db.collection('scholarships')

    delete_collection(doc_ref, 5)

def iterate_all_data():
    cred = credentials.ApplicationDefault()
    firebase_admin.initialize_app(cred, {
        'projectId': 'viewing-step-2020-v2',
    })

    db = firestore.client()
    docs = db.collection('scholarships').stream()

    for doc in docs:
        doc_dict = doc.to_dict()
        name_list = doc_dict['schoolsList']
        doc_ref = db.collection('scholarships').document(doc_dict['id'])
        get_id_list(doc_ref, doc_dict)

iterate_all_data()
        
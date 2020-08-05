import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from excel import parseDataFrom

# Use the application default credentials
cred = credentials.ApplicationDefault()
firebase_admin.initialize_app(cred, {
  'projectId': 'viewing-step-2020-v2',
})


db = firestore.client()

scholarships = parseDataFrom(0, 63, 19)

for scholarship in scholarships:
    doc_ref = db.collection('scholarships').document(scholarship['id'])
    doc_ref.set(scholarship)


users_ref = db.collection('scholarships')
docs = users_ref.stream()

for doc in docs:
    print(f'{doc.id} => {doc.to_dict()}')
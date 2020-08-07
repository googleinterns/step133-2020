# Python Codes for Parsing Excel Data and Stroing Data to Firebase


## Getting Started

This program only runs on cloud shell.

To run python codes in this file, please install Python3. 
Install firebase_admin module with pip.

Add the excel file you want to parse to this folder, name it to 'Scholarshps.xlsx'. 
Please refer to the list of functions in excel.py for the requirements on formatting data in excel.

You may need to move this directory to root directory of your could shell.

### Step 1: parse excel and store data 

To parse data from your excel sheet and store them into database, 
first change the input of the call to parseDataForm(sheet, start, range) and then run 
```
python3 main.py
```

### Step 2: Iterate through all data and request College ID from DoE API

run iterate_all_data_and_get_id_map() in firebase.py, 
which creates a map from college name to id (used for rendering links from scholarship to college);

and then run iterate_all_data_and_get_id_list(),
which pulls id into a separate list (used for rendering links from college to scholarship)



## To delete all data in scholarship collection

run method delete_scholarship_collection() in firebase.py

## Authors

* **Lorraine Lyu** - *Initial work* 



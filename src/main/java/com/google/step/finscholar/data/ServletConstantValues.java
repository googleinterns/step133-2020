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

public class ServletConstantValues {	

    public static final String JSON_CONTENT_TYPE = "application/json";	
    public static final String INDEX_PATH = "/index.html";	
    // The default value for undefined fields.	
    public static final String DEFAULT_VALUE = "";

    // Constants for the different Firestore Collection names.
    public static final String SCHOLARSHIP_COLLECTION_NAME = "scholarships";
    public static final String COLLEGE_COLLECTION_NAME = "colleges";
    public static final String FORUM_POST_COLLECTION_NAME = "forumPosts";
    public static final String FORUM_COMMENT_COLLECTION_NAME = "forumCommments";

    // Constants for exceptions when retrieving data from the Firestore Database.
    public static final String UNABLE_TO_WRITE_TO_FIRESTORE = "Unable to write new object to collection with name: ";
    public static final String UNABLE_TO_READ_FROM_FIRESTORE = "Unable to retrieve data from Firestore.";
    public static final String UNABLE_TO_LOAD_FIREBASE = "Unable to load firebase: ";

    // Constants for FirebaseAppManager.
    public static final String SCOPE_CLOUD_URL = "https://www.googleapis.com/auth/cloud-platform";
    public static final String SCOPE_USER_INFO_URL = "https://www.googleapis.com/auth/userinfo.email";
    public static final String DATABASE_URL = "https://viewing-step-2020-v2.firebaseio.com";
    public static final String PROJECT_ID = "viewing-step-2020-v2";	

} 
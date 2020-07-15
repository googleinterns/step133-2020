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

/** This class is a holder class for constant values used by the servlets. */
public class ServletConstantValues {

    public static final String JSON_CONTENT_TYPE = "application/json;";
    public static final String INDEX_PATH = "/index.html";

    // The default value for undefined fields.
    public static final String DEFAULT_VALUE = "";

    // Constants for the different Firestore Collection names.
    public static final String SCHOLARSHIP_COLLECTION_NAME = "scholarships";
    public static final String COLLEGE_COLLECTION_NAME = "colleges";
    public static final String FORUM_POST_COLLECTION_NAME = "forumPosts";
    public static final String FORUM_COMMENT_COLLECTION_NAME = "forumCommments";

    // Constants for Object Types.
    public static final String SCHOLARSHIP_OBJECT_TYPE = "Scholarship";
    public static final String COLLEGE_OBJECT_TYPE = "College";
    public static final String FORUM_POST_OBJECT_TYPE = "ForumPost";
    public static final String FORUM_COMMENT_OBJECT_TYPE = "ForumComment";
}
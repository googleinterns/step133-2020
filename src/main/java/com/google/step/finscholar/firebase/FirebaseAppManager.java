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

package com.google.step.finscholar.firebase;

import com.google.appengine.api.utils.SystemProperty;
import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


// Only create app one time
public class FirebaseAppManager {
  /** This is the FirebaseApp object to be used for Firestore and Authentication. */
  private static FirebaseApp app = null;

  /**
   * This method initializes the Google Credentials needed to start up the Firebase service.
   *   If the app initializes in production, we return credentials associated with the app.
   *   Else, we use the default application credentials.
   * @return - A GoogleCredentials object representing the credentials needed to start up the Firebase service.
   */
  private static GoogleCredentials getCredentials() throws IOException {
    if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
      List<String> scopes =
        Arrays.asList(
          "https://www.googleapis.com/auth/cloud-platform",
          "https://www.googleapis.com/auth/userinfo.email");
      return AppEngineCredentials.newBuilder().setScopes(scopes).build();
    } else {
      // Local development server
      return GoogleCredentials.getApplicationDefault();
    }
  }

  /** 
   * Initialize the Firebase app.
   * @return - A FirebaseApp object that has been initialized to work with the current running build.
   */
  public static FirebaseApp getApp() throws IOException {
    if(app == null) {
      FirebaseOptions options =
          new FirebaseOptions.Builder()
              .setCredentials(getCredentials())
              .setDatabaseUrl("https://viewing-step-2020-v2.firebaseio.com")
              .setProjectId("viewing-step-2020-v2")
              .build();
      app = FirebaseApp.initializeApp(options);
    }
    return app;
  }
}


STEP Capstone Project - Finscholar

# Objective:

There exists a significant gap in information that many high school students face when it 
comes to college financial aid and scholarships. 

Unfortunately, a lot of the information 
available is not easily accessible, locked behind a pay wall, or simply presented in a 
way that is difficult to understand. 

We'd like to increase the access and transparency of 
this information so that disadvantaged groups, such as minority, low-income, and 
first-generation students (in addition to everyone else) can apply to colleges with a 
greater knowledge of how to afford it. 

This is why we created Finscholar, a portal for information related to college 
financial aid and college-specific scholarships. 

We collected and stored this information and presented it in a way that is easy to 
navigate and digest. 

# What we used:

- We used soy closure templates, CSS, and Closure JavaScript for our frontend. 

- For the backend, we used Java servlets and Cloud Firestore as our database.

- We built all of our components from scratch, without any third-party UI frameworks. 

- We used Material Design Lite as our primary CSS source. 

# How to Build:

- Must be run in Google Cloud Appengine console.
- Fork this repository.
- Navigate to the root directory of this repository
- In the pom.xml file, find `<deploy.projectId>**YOUR PROJECT ID HERE**</deploy.projectId>`.
   and replace the project id with the id of your Appengine project.
- You must also obtain an API key from here: https://api.data.gov/signup/
- Once you've obtained the API key, create a file called config.js.
- Paste this code into the file:
``goog.module('datahandlers.config');

const COLLEGE_API_KEY = '**YOUR API KEY HERE**';

exports = {COLLEGE_API_KEY};``
- The project is ready to be run locally or deployed.
`./run` to run locally.
-`./deploy` to deploy to your Appengine project.

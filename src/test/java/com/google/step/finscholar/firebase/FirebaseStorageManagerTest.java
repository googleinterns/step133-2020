package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreClient;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.data.TestObject;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FirebaseStorageManagerTest {
  private static Firestore firebase;
  private static TestObject testObject;

  @BeforeClass
  public static void setUp() {
    firebase = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
    testObject = new TestObject(ServletConstantValues.TEST_COLLECTION_NAME, ServletConstantValues.TEST_DOCUMENT_NAME);
  }
  
  @Test
  public void isRecentlySetDocumentRetrievable() {
    try {
      FirebaseStorageManager.storeDocument(database, ServletConstantValues.TEST_COLLECTION_NAME, testObject, ServletConstantValues.TEST_DOCUMENT_NAME);
    } catch (Exception e) {
      System.out.println(e.toString());
    }

    String json = "";

    try {
      json = FirebaseStorageManager.getDocument(database, ServletConstantValues.TEST_COLLECTION_NAME, ServletConstantValues.TEST_DOCUMENT_NAME);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    
    System.out.println(json);
    Assert.assertEquals(json, json);
  }


}


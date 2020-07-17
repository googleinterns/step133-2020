package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.data.TestObject;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FirebaseStorageManagerTest {
  private static Firestore firebase;
  private static TestObject testObject;
  private static TestObject testObjectTwo;
  private static List<TestObject> testObjectList;

  @Before
  public static void setUp() {
    try {
      firebase = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
    } catch (Exception e) {
      System.out.println(e);
    }
    testObject = new TestObject(ServletConstantValues.TEST_COLLECTION_NAME, ServletConstantValues.TEST_DOCUMENT_NAME);
    testObjectTwo = new TestObject(ServletConstantValues.TEST_DOCUMENT_NAME, ServletConstantValues.TEST_COLLECTION_NAME);
    testObjectList = new ArrayList<TestObject>();
    testObjectList.add(testObject);
    testObjectList.add(testObjectTwo);
  }

  @Test
  public void isRecentlySetDocumentRetrievable() {
    try {
      FirebaseStorageManager.storeDocument(firebase, ServletConstantValues.TEST_COLLECTION_NAME, testObject, ServletConstantValues.TEST_DOCUMENT_NAME);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    String json = "";
    try {
      json = FirebaseStorageManager.getDocument(firebase, ServletConstantValues.TEST_COLLECTION_NAME, ServletConstantValues.TEST_DOCUMENT_NAME);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    Assert.assertEquals(json, ServletConstantValues.EXPECTED_DOCUMENT_RETRIEVABLE);
  }


  @Test
  public void setMultipleDocumentsAndRetrieveCollection() {
    try {
      FirebaseStorageManager.storeMultipleDocuments(firebase, ServletConstantValues.TEST_COLLECTION_NAME, testObjectList);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}


package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.firebase.TestObject;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FirebaseStorageManagerTest {
  // Constants for FirebaseStorageManagerTest.
  public static final String TEST_COLLECTION_NAME = "testObjects";
  public static final String TEST_DOCUMENT_NAME = "testDocument";
  public static final String EXPECTED_DOCUMENT_RETRIEVABLE = "{\"one\":\"testObjects\",\"two\":\"testDocument\"}";
  private static Firestore firebase;
  private static TestObject testObject;
  private static TestObject testObjectTwo;
  private static List<TestObject> testObjectList;

  @BeforeClass
  public static void setUp() throws Exception{
    firebase = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
    testObject = new TestObject(TEST_COLLECTION_NAME, TEST_DOCUMENT_NAME);
    testObjectTwo = new TestObject(TEST_DOCUMENT_NAME, TEST_COLLECTION_NAME);
    testObjectList = new ArrayList<TestObject>();
    testObjectList.add(testObject);
    testObjectList.add(testObjectTwo);
  }

  @Test
  public void isRecentlySetDocumentRetrievable() throws Exception {
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, TEST_DOCUMENT_NAME);
    String json = FirebaseStorageManager.getDocument(firebase, TEST_COLLECTION_NAME, TEST_DOCUMENT_NAME);
    Assert.assertEquals(json, EXPECTED_DOCUMENT_RETRIEVABLE);
  }

  @Test
  public void setMultipleDocumentsAndRetrieveCollection() {
    try {
      FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    String json = ServletConstantValues.DEFAULT_VALUE;
    try {
      json = FirebaseStorageManager.getCollection(firebase, TEST_COLLECTION_NAME);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    Assert.assertNotEquals(ServletConstantValues.DEFAULT_VALUE, json);
  }
}

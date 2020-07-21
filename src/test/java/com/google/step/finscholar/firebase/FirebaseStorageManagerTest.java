package com.google.step.finscholar.firebase;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.data.TestObject;
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
  public static final int TEST_BATCH_SIZE_LIMIT = 10;
  private static Firestore firebase;
  private static TestObject testObject;
  private static TestObject testObjectTwo;
  private static List<TestObject> testObjectList;

  @BeforeClass
  public static void setUp() {
    try {
      firebase = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
    } catch (Exception e) {
      System.out.println(e);
    }
    testObject = new TestObject(TEST_COLLECTION_NAME, TEST_DOCUMENT_NAME);
    testObjectTwo = new TestObject(TEST_DOCUMENT_NAME, TEST_COLLECTION_NAME);
    testObjectList = new ArrayList<TestObject>();
    testObjectList.add(testObject);
    testObjectList.add(testObjectTwo);
  }

  @Test
  public void isRecentlySetDocumentRetrievable() {
    try {
      FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, TEST_DOCUMENT_NAME);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    String json = "";
    try {
      json = FirebaseStorageManager.getDocument(firebase, TEST_COLLECTION_NAME, TEST_DOCUMENT_NAME);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
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

  @Test
  public void retrieveCollectionBatchWithIDandWithoutID() {
    String jsonWithID = ServletConstantValues.DEFAULT_VALUE;
    try {
      jsonWithID = FirebaseStorageManager.getCollectionBatch(firebase, TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LIMIT, TEST_DOCUMENT_NAME, ServletConstantValues.DEFAULT_VALUE);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    System.out.println("Batch query results with ID: " + jsonWithID);
    Assert.assertNotEquals(ServletConstantValues.DEFAULT_VALUE, jsonWithID);

    String jsonWithoutID = ServletConstantValues.DEFAULT_VALUE;
    try {
      jsonWithoutID = FirebaseStorageManager.getCollectionBatch(firebase, TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LIMIT, ServletConstantValues.DEFAULT_VALUE, ServletConstantValues.DEFAULT_VALUE);
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    System.out.println("Batch query results without ID: " + jsonWithoutID);
    Assert.assertNotEquals(ServletConstantValues.DEFAULT_VALUE, jsonWithoutID);
  }
}

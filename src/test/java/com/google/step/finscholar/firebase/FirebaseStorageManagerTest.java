package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.step.finscholar.firebase.TestObject;

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

  @BeforeClass
  public static void setUp() throws Exception{
    firebase = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
    testObject = new TestObject(TEST_COLLECTION_NAME, TEST_DOCUMENT_NAME);
  }

  @Test
  public void isRecentlySetDocumentRetrievable() throws Exception {
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, TEST_DOCUMENT_NAME);
    String json = FirebaseStorageManager.getDocument(firebase, TEST_COLLECTION_NAME, TEST_DOCUMENT_NAME);
    Assert.assertEquals(json, EXPECTED_DOCUMENT_RETRIEVABLE);
  }
}

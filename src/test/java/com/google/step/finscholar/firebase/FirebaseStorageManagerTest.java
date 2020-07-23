package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.firebase.TestObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FirebaseStorageManagerTest {
  // Constants for FirebaseStorageManagerTest.
  /** Logger that sends logs to the Cloud Project console. */
  private static final Logger log = Logger.getLogger(FirebaseStorageManager.class.getName());
  public static final String TEST_COLLECTION_NAME = "testObjects";
  public static final String TEST_DOCUMENT_NAME = "testDocument";
  public static final String EXPECTED_DOCUMENT_RETRIEVABLE = "{\"one\":\"testObjects\",\"two\":\"testDocument\"}";
  public static final int TEST_BATCH_SIZE_LIMIT = 10;
  public static final String PARAM_TO_SORT_BY = "one";
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
  public void setMultipleDocumentsAndRetrieveCollection() throws Exception {
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String json = FirebaseStorageManager.getCollection(firebase, TEST_COLLECTION_NAME);
    Optional<String> jsonNullable = Optional.ofNullable(json);
    Assert.assertTrue(jsonNullable.isPresent());
  }

  @Test
  public void retrieveCollectionBatchWithIDandWithoutID() throws Exception {
    String jsonWithID = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LIMIT, 
        TEST_DOCUMENT_NAME, PARAM_TO_SORT_BY);
    String noIdMessage = String.format("Batch query results with ID: %s", jsonWithID);
    log.info(noIdMessage);
    Optional<String> jsonNullable = Optional.ofNullable(jsonWithID);
    Assert.assertTrue(jsonNullable.isPresent());

    String jsonWithoutID = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LIMIT, null, PARAM_TO_SORT_BY);
    String idMessage = String.format("Batch query results without ID: %s", jsonWithoutID);
    log.info(idMessage);
    Optional<String> jsonNoIdNullable = Optional.ofNullable(jsonWithoutID);
    Assert.assertTrue(jsonNoIdNullable.isPresent());
  }
}

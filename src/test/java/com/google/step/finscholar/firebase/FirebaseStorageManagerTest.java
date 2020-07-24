package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.errorprone.annotations.CompileTimeConstant;
import com.google.firebase.cloud.FirestoreClient;
import com.google.step.finscholar.data.ServletConstantValues;
import com.google.step.finscholar.firebase.TestObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
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
  public static final String TEST_DOCUMENT_TWO_NAME = "testDocument2";
  public static final String TEST_DOCUMENT_THREE_NAME = "testDocument3";
  public static final String TEST_DOCUMENT_FOUR_NAME = "testDocument4";
  public static final String TEST_DOCUMENT_FIVE_NAME = "testDocument5";
  public static final String TEST_DOCUMENT_SIX_NAME = "testDocument6";
  public static final String TEST_DOCUMENT_SEVEN_NAME = "testDocument7";
  public static final String TEST_DOCUMENT_EIGHT_NAME = "testDocument8";
  public static final String EXPECTED_DOCUMENT_RETRIEVABLE = "{\"one\":\"testObjects\",\"two\":\"testDocument\"}";
public static final String EXPECTED_COLLECTION_WITH_ID = "[{\"one\":\"testDocument\",\"two\":\"testObjects\"}]";
  public static final String EXPECTED_COLLECTION_NO_ID = "";
  public static final int TEST_BATCH_SIZE_LIMIT = 10;
  public static final int TEST_BATCH_SIZE_LOWER = 3;
  public static final String PARAM_TO_SORT_BY = "one";
  private static Firestore firebase;
  private static TestObject testObject;
  private static TestObject testObjectTwo;
  private static TestObject testObjectThree;
  private static TestObject testObjectFour;
  private static TestObject testObjectFive;
  private static TestObject testObjectSix;
  private static TestObject testObjectSeven;
  private static TestObject testObjectEight;
  private static List<TestObject> testObjectList;
  private static List<TestObject> testObjectListTwo;
  private static List<TestObject> testObjectListThree;

  @BeforeClass
  public static void setUp() throws Exception {
    firebase = FirestoreClient.getFirestore(FirebaseAppManager.getApp());
    testObject = new TestObject(TEST_DOCUMENT_NAME, TEST_COLLECTION_NAME);
    testObjectTwo = new TestObject(TEST_DOCUMENT_TWO_NAME, TEST_COLLECTION_NAME);
    testObjectThree = new TestObject(TEST_DOCUMENT_THREE_NAME, TEST_COLLECTION_NAME);
    testObjectFour = new TestObject(TEST_DOCUMENT_FOUR_NAME, TEST_COLLECTION_NAME);
    testObjectFive = new TestObject(TEST_DOCUMENT_FIVE_NAME, TEST_COLLECTION_NAME);
    testObjectSix = new TestObject(TEST_DOCUMENT_SIX_NAME, TEST_COLLECTION_NAME);
    testObjectSeven = new TestObject(TEST_DOCUMENT_SEVEN_NAME, TEST_COLLECTION_NAME);
    testObjectEight = new TestObject(TEST_DOCUMENT_EIGHT_NAME, TEST_COLLECTION_NAME);
    testObjectList = new ArrayList<TestObject>();
    testObjectListTwo = new ArrayList<TestObject>();
    testObjectListThree = new ArrayList<TestObject>();
    testObjectList.add(testObject);
    testObjectList.add(testObjectTwo);
    testObjectListTwo.add(testObjectFour);
    testObjectListTwo.add(testObjectFive);
    testObjectListThree.add(testObjectSeven);
    testObjectListThree.add(testObjectEight);
  }

  @After
  public void tearDown() throws Exception {
    FirebaseStorageManager.deleteCollection(firebase, TEST_COLLECTION_NAME);
  }

  @Test
  public void isRecentlySetDocumentRetrievable() throws Exception {
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, TEST_DOCUMENT_NAME);
    String json = FirebaseStorageManager.getDocument(firebase, TEST_COLLECTION_NAME, TEST_DOCUMENT_NAME);
    Assert.assertEquals(EXPECTED_DOCUMENT_RETRIEVABLE,json);
  }

  @Test
  public void setMultipleDocumentsAndRetrieveCollection() throws Exception {
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String json = FirebaseStorageManager.getCollection(firebase, TEST_COLLECTION_NAME);
    Optional<String> jsonNullable = Optional.ofNullable(json);
    Assert.assertTrue(jsonNullable.isPresent());
  }

  @Test
  public void retrieveCollectionBatchWithID() throws Exception {
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, TEST_DOCUMENT_NAME);
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String jsonWithID = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LIMIT, 
        TEST_DOCUMENT_NAME, PARAM_TO_SORT_BY);
    String noIdMessage = String.format("Batch query results with ID: %s", jsonWithID);
    log.info(noIdMessage);
    Optional<String> jsonNullable = Optional.ofNullable(jsonWithID);
    Assert.assertTrue(jsonNullable.isPresent());
  }

  @Test
  public void retrieveCollectionBatchWithoutID() throws Exception {
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String jsonWithoutID = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LIMIT, null, PARAM_TO_SORT_BY);
    String idMessage = String.format("Batch query results without ID: %s", jsonWithoutID);
    log.info(idMessage);
    Optional<String> jsonNoIdNullable = Optional.ofNullable(jsonWithoutID);
    Assert.assertTrue(jsonNoIdNullable.isPresent());
  }

  @Test
  public void retrieveCollectionBatchNoSort() throws Exception {
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, TEST_DOCUMENT_NAME);
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String jsonWithoutSort = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LIMIT, null, null);
    String idMessage = String.format("Batch query results with no sort parameter: %s", jsonWithoutSort);
    log.info(idMessage);
    Optional<String> jsonNoIdNullable = Optional.ofNullable(jsonWithoutSort);
    Assert.assertTrue(jsonNoIdNullable.isPresent());
  }
  
  @Test
  public void multipleBatchesSizeLowerThanCollectionSize() throws Exception {
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObjectThree, TEST_DOCUMENT_THREE_NAME);
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObjectSix, TEST_DOCUMENT_SIX_NAME);
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String jsonFirstBatch = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LOWER, null, PARAM_TO_SORT_BY);
    String idMessage = String.format("Batch query results for first batch: %s", jsonFirstBatch);
    log.info(idMessage);
    String jsonSecondBatch = FirebaseStorageManager.getCollectionBatch(firebase, 
    TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LOWER, TEST_DOCUMENT_THREE_NAME, PARAM_TO_SORT_BY);
    idMessage = String.format("Batch query results for second batch: %s", jsonSecondBatch);
    log.info(idMessage);
    String jsonLastBatch = FirebaseStorageManager.getCollectionBatch(firebase, 
    TEST_COLLECTION_NAME, TEST_BATCH_SIZE_LOWER, TEST_DOCUMENT_SIX_NAME, PARAM_TO_SORT_BY);
    idMessage = String.format("Batch query results for last batch: %s", jsonLastBatch);
    log.info(idMessage);
  }
}

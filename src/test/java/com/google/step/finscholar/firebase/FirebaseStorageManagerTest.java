package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
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
  public static final String EXPECTED_DOCUMENT_RETRIEVABLE = "{\"one\":\"testDocument\",\"two\":\"testObjects\"}";
  public static final String EXPECTED_COLLECTION_WITH_ID = "[{\"one\":\"testDocument2\",\"two\":\"testObjects\"}]";
  public static final String EXPECTED_COLLECTION_NO_ID = 
    "[{\"one\":\"testDocument\",\"two\":\"testObjects\"},{\"one\":\"testDocument2\",\"two\":\"testObjects\"}]";
  public static final String EXPECTED_COLLECTION_NO_SORT =
    "[{\"one\":\"testDocument2\",\"two\":\"testObjects\"},{\"one\":\"testDocument\",\"two\":\"testObjects\"}]";
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
  public static String EXPECTED_FIRST_BATCH;
  public static String EXPECTED_SECOND_BATCH;
  public static String EXPECTED_THIRD_BATCH;

  @BeforeClass
  public static void setUp() throws Exception {
    firebase = FirestoreClient.getFirestore(FirebaseAppManager.getApp().get());
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
    // Using string concat here since the expected output string is so large.
    String firstBatch = "[{\"one\":\"testDocument\",\"two\":\"testObjects\"}";
    firstBatch = firstBatch.concat(",{\"one\":\"testDocument2\",\"two\":\"testObjects\"},");
    firstBatch = firstBatch.concat("{\"one\":\"testDocument3\",\"two\":\"testObjects\"}]");
    EXPECTED_FIRST_BATCH = firstBatch;
    String secondBatch = "[{\"one\":\"testDocument4\",\"two\":\"testObjects\"}";
    secondBatch = secondBatch.concat(",{\"one\":\"testDocument5\",\"two\":\"testObjects\"},");
    secondBatch = secondBatch.concat("{\"one\":\"testDocument6\",\"two\":\"testObjects\"}]");
    EXPECTED_SECOND_BATCH = secondBatch;
    String thirdBatch = "[{\"one\":\"testDocument7\",\"two\":\"testObjects\"}";
    thirdBatch = thirdBatch.concat(",{\"one\":\"testDocument8\",\"two\":\"testObjects\"}]");
    EXPECTED_THIRD_BATCH = thirdBatch;
  }

  @After
  public void tearDown() throws Exception {
    // Reset the collection after each test to ensure consistency.
    FirebaseStorageManager.deleteCollection(firebase, TEST_COLLECTION_NAME);
  }

  @Test
  public void multipleBatchesSizeLowerThanCollectionSize() throws Exception {
    // The key of this test here is testing batching in succession, so the result of
    //   each batch request must be checked against the expected value of that batch.
    // Each batch is less than the collection size and the last batch is smaller 
    //   than the batch limit.

    // Store all the documents necessary.
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObjectThree, 
        Optional.ofNullable(TEST_DOCUMENT_THREE_NAME));
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectListTwo);
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObjectSix, 
        Optional.ofNullable(TEST_DOCUMENT_SIX_NAME));
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectListThree);

    // Test the first batch with no previous ID.
    String jsonFirstBatch = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, Optional.ofNullable(TEST_BATCH_SIZE_LOWER), Optional.empty(), 
        Optional.ofNullable(PARAM_TO_SORT_BY));
    String idMessage = String.format("Batch query results for first batch: %s", jsonFirstBatch);
    log.info(idMessage);
    Assert.assertEquals(EXPECTED_FIRST_BATCH, jsonFirstBatch);

    // Test second batch with last accessed ID and matching batch size limit.
    String jsonMiddleBatch = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, Optional.ofNullable(TEST_BATCH_SIZE_LOWER), 
        Optional.ofNullable(TEST_DOCUMENT_THREE_NAME), Optional.ofNullable(PARAM_TO_SORT_BY));
    idMessage = String.format("Batch query results for second batch: %s", jsonMiddleBatch);
    log.info(idMessage);
    Assert.assertEquals(EXPECTED_SECOND_BATCH, jsonMiddleBatch);

    // Test second batch with last accessed ID but with size < batch size limit.
    String jsonLastBatch = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, Optional.ofNullable(TEST_BATCH_SIZE_LOWER), 
        Optional.ofNullable(TEST_DOCUMENT_SIX_NAME), Optional.ofNullable(PARAM_TO_SORT_BY));
    idMessage = String.format("Batch query results for last batch: %s", jsonLastBatch);
    log.info(idMessage);
    Assert.assertEquals(EXPECTED_THIRD_BATCH, jsonLastBatch);
  }

  @Test
  public void isRecentlySetDocumentRetrievable() throws Exception {
    FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, 
        Optional.ofNullable(TEST_DOCUMENT_NAME));
    String json = FirebaseStorageManager.getDocument(firebase, TEST_COLLECTION_NAME, 
        Optional.ofNullable(TEST_DOCUMENT_NAME));
    Assert.assertEquals(EXPECTED_DOCUMENT_RETRIEVABLE, json);
  }

  @Test
  public void setMultipleDocumentsAndRetrieveCollection() throws Exception {
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String json = FirebaseStorageManager.getCollection(firebase, TEST_COLLECTION_NAME);
    Optional<String> jsonOptional = Optional.ofNullable(json);
    Assert.assertTrue(jsonOptional.isPresent());
  }

//   @Test
//   public void retrieveCollectionBatchWithID() throws Exception {
//     FirebaseStorageManager.storeDocument(firebase, TEST_COLLECTION_NAME, testObject, 
//         Optional.ofNullable(TEST_DOCUMENT_NAME));
//     FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
//     String jsonWithID = FirebaseStorageManager.getCollectionBatch(firebase, 
//         TEST_COLLECTION_NAME, Optional.ofNullable(TEST_BATCH_SIZE_LIMIT), 
//         Optional.ofNullable(TEST_DOCUMENT_NAME), Optional.ofNullable(PARAM_TO_SORT_BY));
//     String noIdMessage = String.format("Batch query results with ID: %s", jsonWithID);
//     log.info(noIdMessage);
//     Assert.assertEquals(EXPECTED_COLLECTION_WITH_ID, jsonWithID);
//   }

  @Test
  public void retrieveCollectionBatchWithoutID() throws Exception {
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String jsonWithoutID = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, Optional.ofNullable(TEST_BATCH_SIZE_LIMIT), 
        Optional.empty(), Optional.ofNullable(PARAM_TO_SORT_BY));
    String idMessage = String.format("Batch query results without ID: %s", jsonWithoutID);
    log.info(idMessage);
    Assert.assertEquals(EXPECTED_COLLECTION_NO_ID, jsonWithoutID);
  }

  @Test
  public void retrieveCollectionBatchNoSort() throws Exception {
    FirebaseStorageManager.storeMultipleDocuments(firebase, TEST_COLLECTION_NAME, testObjectList);
    String jsonWithoutSort = FirebaseStorageManager.getCollectionBatch(firebase, 
        TEST_COLLECTION_NAME, Optional.ofNullable(TEST_BATCH_SIZE_LIMIT), 
        Optional.empty(), Optional.empty());
    String idMessage = 
        String.format("Batch query results with no sort parameter: %s", jsonWithoutSort);
    log.info(idMessage);
    Optional<String> noSortOptional = Optional.ofNullable(jsonWithoutSort);
    Assert.assertTrue(noSortOptional.isPresent());
  }
}

package com.google.step.finscholar.firebase;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreClient;
import com.google.step.finscholar.data.ServletConstantValues;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FirebaseStorageManagerTest {
  private static Firestore firebase;

  @BeforeClass
  public static void setUp() {
    firebase = FiresstoreClient.getFirestorre(FirebaseAppManager.getApp());
    
  }
  
  @Test
  public void isRecentlySetDocumentRetrievable() {
    FirebaseStorageManager.storeDocument(database, ServletConstantValues.TEST_COLLECTION_NAME, object);

  }


}


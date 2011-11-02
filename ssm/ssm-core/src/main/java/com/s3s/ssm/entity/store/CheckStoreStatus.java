package com.s3s.ssm.entity.store;

/**
 * <li>OPEN: start to check store, first state of object.</li> <li>PROCESSING: adding some detail check stored.</li> <li>
 * VALIDATED: ensure that the check store is correct.</li> <li>CLOSED: the check store is closed and the amount of item
 * is updated to store.</li>
 * 
 * @author phamcongbang
 * 
 */
public enum CheckStoreStatus {
    OPEN, PROCESSING, VALIDATED, CLOSED
}

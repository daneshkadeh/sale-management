/*
 * CheckStoreStatus
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
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

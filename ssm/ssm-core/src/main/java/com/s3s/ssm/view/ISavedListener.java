/*
 * IEditSavedListener
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

package com.s3s.ssm.view;

import java.util.EventListener;

import com.s3s.ssm.entity.AbstractBaseIdObject;

/**
 * 
 * @author Phan Hong Phuc
 * @since Feb 22, 2012
 */
public interface ISavedListener<T extends AbstractBaseIdObject> extends EventListener {
    /**
     * Invoke when the edit view was saved.
     */
    public void doSaved(SavedEvent<T> e);
}

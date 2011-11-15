/*
 * PageChangeListener
 * 
 * Project: SSM
 * 
 * Copyright 2010 by S3SSoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of S3SSoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with S3SSoft.
 */

package com.s3s.ssm.view.component;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

/**
 * The listener interface for receiving the page number changed.
 * 
 * @see S3sPagingNavigator
 * @author Phan Hong Phuc
 * @since Nov 13, 2011
 * 
 */
public interface S3sPageChangeListener extends EventListener {
    /**
     * Invoke when the page number is changed.
     */
    public void doPageChanged(ChangeEvent e);
}

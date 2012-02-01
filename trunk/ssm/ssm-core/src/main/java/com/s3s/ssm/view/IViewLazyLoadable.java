/*
 * IViewLazyLoadable
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

/**
 * The proxy pattern, purpose to lazy load the view.
 * 
 * @author Phan Hong Phuc
 * @since Nov 20, 2011
 * 
 */
public interface IViewLazyLoadable {
    public void loadView();
}

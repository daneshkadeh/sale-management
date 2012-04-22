/*
 * ViewHelper
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view;

import com.s3s.ssm.util.CacheDataService;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.ServiceProvider;

/**
 * @author Le Thanh Hoang
 * 
 */
public abstract class ViewHelper {

    protected static ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();

    protected CacheDataService cacheDataService = ConfigProvider.getInstance().getCacheDataService();
}

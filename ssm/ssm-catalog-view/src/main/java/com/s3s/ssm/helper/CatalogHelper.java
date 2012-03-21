/*
 * CatalogHelper
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
package com.s3s.ssm.helper;

import java.util.ArrayList;
import java.util.List;

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.util.DaoHelper;

public class CatalogHelper {

    // TODO: This should be moved to properly helper or service!
    public static List<String> getCurrenciesCode(DaoHelper daoHelper) {
        List<SCurrency> currencies = daoHelper.getDao(SCurrency.class).findAll();
        List<String> result = new ArrayList<>();
        for (SCurrency currency : currencies) {
            result.add(currency.getCode());
        }
        return result;
    }
}

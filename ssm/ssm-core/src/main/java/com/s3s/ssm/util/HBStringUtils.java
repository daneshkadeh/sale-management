/*
 * HBStringUtils
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
package com.s3s.ssm.util;

import org.apache.commons.lang.StringUtils;

/**
 * Handle string
 * 
 * @author Thanh Hoang
 * 
 */
public class HBStringUtils extends StringUtils {
    public static Double safeParseDouble(String str) {
        Double result;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            result = (double) 0;
        }
        return result;
    }

    public static Integer safeParseInt(String str) {
        Integer result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = (int) 0;
        }
        return result;
    }
}

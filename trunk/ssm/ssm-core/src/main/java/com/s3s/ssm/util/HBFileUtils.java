/*
 * HBFileUtils
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

import java.io.IOException;

/**
 * Handle file
 * 
 * @author Thanh Hoang
 * 
 */
public class HBFileUtils {
    /**
     * Run file with suitable program
     * 
     * @param filePath
     *            the path of file
     */
    public static void openFile(String filePath) {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
 * ExporterNotFoundException
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

package com.s3s.ssm.export.exporter;

/**
 * @author Le Thanh Hoang
 *
 */
public class ExporterNotFoundException extends Exception {
    public ExporterNotFoundException(){
        super();
    }
    
    public ExporterNotFoundException(String message){
        super(message);
    }
    
    public ExporterNotFoundException(Throwable throwable){
        super(throwable);
    }
    
    public ExporterNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}

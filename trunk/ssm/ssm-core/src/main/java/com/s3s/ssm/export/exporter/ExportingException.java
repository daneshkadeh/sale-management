/*
 * ExportingException
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

package com.s3s.ssm.export.exporter;

/**
 * @author Le Thanh Hoang
 *
 */
public class ExportingException extends Exception {
    public ExportingException(){
        super();
    }
    
    public ExportingException(String message){
        super(message);
    }
    
    public ExportingException(Throwable throwable){
        super(throwable);
    }
    
    public ExportingException(String message, Throwable throwable){
        super(message, throwable);
    }
}

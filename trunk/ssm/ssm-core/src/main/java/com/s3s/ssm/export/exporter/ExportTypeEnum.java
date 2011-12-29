/*
 * ExportTypeEnum
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
public enum ExportTypeEnum {
    PDF(".pdf"), EXCEL2003(".xls"), EXCEL2007(".xlsx"), CSV(".csv");

    private String extension;;

    ExportTypeEnum(String extension) {
        this.extension = extension;
    }
}

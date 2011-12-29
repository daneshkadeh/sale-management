/*
 * ExcelBuilder
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

package com.s3s.ssm.export.builder;

import java.io.OutputStream;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.CellValue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Le Thanh Hoang
 * 
 */
public class ExcelBuilder {
    WritableWorkbook workbook;
    WritableSheet sheet;

    String format;
    Map formats;

    private static final Log logger = LogFactory.getLog(ExcelBuilder.class);

    /**
     * This method is invoked when you invoke a method without arguments on the builder e.g. builder.write().
     * 
     * @param name
     *            The name of the method which should be invoked e.g. write.
     * 
     */
    protected Object createNode(Object name) {
        logger.debug("createNode(Object name)");
        logger.debug("name: ${name}");

        if (name == "write") {
            this.write();
        }

        return null;
    }

    public void createWorkBook(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                logger.debug("Creating workbook");
                workbook = Workbook.createWorkbook(outputStream);
            } catch (Exception e) {
                logger.error("Error creating workbook", e);
            }
        }
    }

    public void createSheet(String name) {
        try {
            logger.debug("Creating sheet");
            sheet = workbook.createSheet(name, workbook.getNumberOfSheets());
        } catch (Exception e) {
            logger.error("Error creating sheet", e);
        }
    }

    // Cell, column header or row cells
    // TODO: set format ex: format = header
    public void createCell(int column, int row, Object cellValue) {
        try {
            CellValue value;

            if (cellValue instanceof java.lang.Number) {
                logger.debug("Creating number cell");
                value = new Number(column, row, (double) cellValue);
            } else {
                logger.debug("Creating label cell");
                value = new Label(column, row, cellValue.toString());
            }
            sheet.addCell(value);
        } catch (Exception e) {
            logger.error("Error adding cell with attributes: ${attributes}", e);
        }
    }

    /**
     * Finish writing the document.
     */
    public void write() {
        logger.debug("Writing document");

        try {
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            logger.error("Error writing document", e);
        }
    }
}

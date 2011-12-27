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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.CellValue;
import jxl.write.Number;
import jxl.write.Label;

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
     * This method is invoked when you invoke a method without arguments on the builder 
     * e.g. builder.write().
     * 
     * @param name The name of the method which should be invoked e.g. write.
     * 
     */
    protected Object createNode(Object name) {
        logger.debug("createNode(Object name)");
        logger.debug("name: ${name}");
        
        if(name == "write"){
            this.write();
        }
        
        return null;
    }
    
//    /**
//     * This method is invoked when you invoke a method with a map of attributes e.g. 
//     * cell(row: 0, column: 0, value: "Hello1"). It switches between different build 
//     * actions such as creating the workbook, sheet, cells etc.
//     * 
//     * @param name The name of the method which should be invoked e.g. cell
//     * @param attributes The map of attributes which have been supplied e.g. [row: 0, column: 0, value: "Hello1"]
//     * 
//     */
//    protected Object createNode(Object name, Map attributes) {
//        logger.debug("createNode(Object name, Map attributes)");
//        logger.debug("name: ${name} attributes: ${attributes}");
//        
//        switch(name){
//            // Workbook, the Excel document as such
//            case "workbook":
//                attributes.containsKey("outputStream")
//                if(attributes.outputStream){
//                    try {
//                        logger.debug("Creating workbook");
//                        workbook = Workbook.createWorkbook(attributes.outputStream);    
//                    }
//                    catch(Exception e){
//                        logger.error("Error creating workbook", e);
//                    }
//                }
//                break;
//            
//            // Sheet, an Excel file can contain multiple sheets which are typically shown as tabs
//            case "sheet":
//                try {
//                    logger.debug("Creating sheet");
//                    sheet = workbook.createSheet(attributes?.name, workbook.getNumberOfSheets()) ;   
//                    if (attributes?.widths && !attributes?.widths?.isEmpty()) {
//                        attributes.widths.eachWithIndex { width, i ->
//                            sheet.setColumnView(i, (width < 1.0 ? width * 100 : width) as int );
//                        }
//                    }
//                }
//                catch(Exception e){
//                    logger.error("Error creating sheet", e);
//                }
//                break;
//                
//            // Cell, column header or row cells
//            case "cell":
//                try {
//                    CellValue value;
//                    
//                    if(attributes.value instanceof java.lang.Number){
//                        log.debug("Creating number cell");
//                        value = new Number(attributes?.column, attributes?.row, attributes?.value);
//                    }
//                    else {
//                        log.debug("Creating label cell");
//                        value = new Label(attributes?.column, attributes?.row, attributes?.value?.toString());
//                    }
//
//                    if(attributes?.format && formats.containsKey(attributes?.format)){
//                        value.setCellFormat(formats[attributes.format]);
//                    }
//                    
//                    
//                    // Create hyperlinks for values beginning with http
//                    if (attributes?.value?.toString()?.toLowerCase()?.startsWith('http://') || attributes?.value?.toString()?.toLowerCase()?.startsWith('https://')) {
//                        log.debug("Changing cell to Hyperlink")
//                        def link = new WritableHyperlink(attributes?.column, attributes?.row, new URL(attributes?.value?.toString()))
//                        link.setDescription(attributes?.value?.toString() ?: 'no URL')
//                        sheet.addHyperlink(link);
//                    }
//                    else {
//                        sheet.addCell(value);
//                    }
//                }
//                catch(Exception e){
//                    log.error("Error adding cell with attributes: ${attributes}", e);
//                }
//                break;
//                
//            case "format":
//                if(attributes?.name){
//                    format = attributes?.name;
//                }
//                break;
//            
//            case "font":
//                try {
//                    log.debug("attributes: ${attributes}")
//                    
//                    attributes.name = attributes?.name ? attributes?.name : "arial"
//                    attributes.italic = attributes?.italic ? attributes?.italic : false
//                    attributes.bold = attributes?.bold ? attributes?.bold : "false"
//                    attributes["size"] = attributes["size"] ? attributes["size"] : WritableFont.DEFAULT_POINT_SIZE
//                    attributes.underline = attributes?.underline ? attributes?.underline : "none"
//                            
//                    Map bold = ["true": WritableFont.BOLD, "false": WritableFont.NO_BOLD]       
//                    if(bold.containsKey(attributes.bold.toString())){
//                        attributes.bold = bold[attributes?.bold.toString()] 
//                    }
//                            
//                    Map underline = ["none": UnderlineStyle.NO_UNDERLINE, "double accounting": UnderlineStyle.DOUBLE_ACCOUNTING,
//                                     "single": UnderlineStyle.SINGLE, "single accounting": UnderlineStyle.SINGLE_ACCOUNTING]
//                    if(underline.containsKey(attributes.underline)){
//                        attributes.underline = underline[attributes.underline]
//                    }
//                    
//                    Map fontname = ["arial":  WritableFont.ARIAL, "courier": WritableFont.COURIER, 
//                                "tahoma":  WritableFont.TAHOMA, "times":  WritableFont.TIMES]
//                    if(fontname.containsKey(attributes.name)){
//                        attributes.name = fontname[attributes.name]
//                    }
//                    
//                    logger.debug("attributes: ${attributes}");
//                            
//                    WritableFont font = new WritableFont(attributes.name, attributes["size"], attributes.bold, attributes.italic, attributes.underline) 
//                    WritableCellFormat cellFormat = new WritableCellFormat(font)
//                    formats.put(format, cellFormat) 
//                }
//                catch(Exception e){
//                    
//                }
//                break;
//        }
//        
//        return null;
//    }
    
    public void createWorkBook(OutputStream outputStream) {
        if(outputStream!=null){
            try {
                logger.debug("Creating workbook");
                workbook = Workbook.createWorkbook(outputStream);    
            }
            catch(Exception e){
                logger.error("Error creating workbook", e);
            }
        }
    }
    
    //TODO set default width for column of sheet
    public void createSheet(String name) {
        try {
            logger.debug("Creating sheet");
            sheet = workbook.createSheet(name, workbook.getNumberOfSheets()) ;   
//            if (widths && !attributes?.widths?.isEmpty()) {
//                attributes.widths.eachWithIndex { width, i ->
//                    sheet.setColumnView(i, (width < 1.0 ? width * 100 : width) as int );
//                }
//            }
        }
        catch(Exception e){
            logger.error("Error creating sheet", e);
        }
    }
    
    //Cell, column header or row cells
    //TODO: set format ex: format = header
    public void createCell(int column, int row,Object cellValue) {
        try {
            CellValue value;
            
            if(cellValue instanceof java.lang.Number){
                logger.debug("Creating number cell");
                value = new Number(column, row, (double) cellValue);
            }
            else {
                logger.debug("Creating label cell");
                value = new Label(column, row, cellValue.toString());
            }

//            if(attributes?.format && formats.containsKey(attributes?.format)){
//                value.setCellFormat(formats[attributes.format]);
//            }
            
            
//            // Create hyperlinks for values beginning with http
//            if (attributes?.value?.toString()?.toLowerCase()?.startsWith('http://') || attributes?.value?.toString()?.toLowerCase()?.startsWith('https://')) {
//                log.debug("Changing cell to Hyperlink")
//                def link = new WritableHyperlink(attributes?.column, attributes?.row, new URL(attributes?.value?.toString()))
//                link.setDescription(attributes?.value?.toString() ?: 'no URL')
//                sheet.addHyperlink(link);
//            }
//            else {
//                sheet.addCell(value);
//            }
            sheet.addCell(value);
        }catch(Exception e){
            logger.error("Error adding cell with attributes: ${attributes}", e);
        }
    }
    /**
     * Finish writing the document.
     */
    public void write(){
        logger.debug("Writing document");
        
        try {
            workbook.write();
            workbook.close(); 
        }
        catch(Exception e){
            logger.error("Error writing document", e);
        }
    }
}

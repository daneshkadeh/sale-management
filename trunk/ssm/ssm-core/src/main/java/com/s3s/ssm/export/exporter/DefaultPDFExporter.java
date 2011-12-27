/*
 * DefaultPDFExporter
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

import java.awt.Color;
import java.io.OutputStream;
import java.util.List;

import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.s3s.ssm.view.AbstractListView.AdvanceTableModel;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Chanhchua
 * 
 */
public class DefaultPDFExporter extends AbstractExporter {

    /**
     * Use this method for exporting an table on swing
     */
    @Override
    public void exportData(OutputStream outputStream, JXTable jxTable, AdvanceTableModel tableModel)
            throws ExportingException {
        List<TableColumn> tableColumns = jxTable.getColumns();
        int colNum = tableColumns.size();
        int colNumNotHide = getColNumNotHide(jxTable);
        try {
            Rectangle pageSize = PageSize.A4;
            Document document = new Document(pageSize, 36, 36, 36, 36);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            String encoding = BaseFont.CP1252;
         // Title font
            Font title = createFont("title", FontFactory.HELVETICA, encoding, 10, Font.BOLD);
            
            // Header font
            Font header = createFont("header", FontFactory.HELVETICA, encoding, 8, Font.BOLD);
            
            // Text font
            Font text = createFont("text", FontFactory.HELVETICA, encoding, 8, Font.NORMAL);
            //set footer
            Phrase footerPhrase = new Phrase("", text);
            HeaderFooter footer = new HeaderFooter(footerPhrase, true);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.disableBorderSide(Rectangle.TOP);
            footer.disableBorderSide(Rectangle.BOTTOM);
            document.setFooter(footer);
            
            //set title
            //TODO get the title from parameters
            Paragraph paragraph = new Paragraph("Title of Report", title);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);
            
            float minimumCellHeight = 17;
            //table
            PdfPTable table;
            table = new PdfPTable(colNumNotHide);
            table.setWidthPercentage(100);
            //TODO in case header is 2 rows, we must get the value from parameter
            table.setHeaderRows(1);
            //TODO the value shoud get from parameter
            Color borderColor = new Color(163, 163, 163);
            
            //Header columns
            //TODO should get from the value of abstract class
            int cellHeaderIndex = 0;
            String strHeader;
            for (int i = 0; i < colNum; i++) {
                int maxColSize = tableColumns.get(i).getMaxWidth();
                if (maxColSize > 0) {
                    strHeader = (String) tableColumns.get(i)
                            .getHeaderValue();
                    PdfPCell cell = new PdfPCell(new Paragraph(strHeader, header));
                    cell.setBorderColor(borderColor);
                    cell.setMinimumHeight(minimumCellHeight);
                    table.addCell(cell);
                    cellHeaderIndex++;
                }
            }
            //TODO must get from parameter
            Color separatorColor = new Color(238, 238, 238);
            //data
            int pageRows = tableModel.getRowCount();
            for (int i = 1; i <= pageRows; i++) {
                Object valueCell;
                int cellColIndex = 0;
                for (int j = 0; j < colNum; j++) {
                    int maxColSize = tableColumns.get(j).getMaxWidth();
                    if (maxColSize > 0) {
                        valueCell = tableModel.getValueAt(i - 1, j);
                        PdfPCell cell = new PdfPCell(new Paragraph(valueCell.toString(), text));
                        if(i % 2 == 0){
                            cell.setBackgroundColor(separatorColor);
                        }
                        cell.setBorderColor(borderColor);
                        cell.setMinimumHeight(minimumCellHeight);
                        table.addCell(cell);
                        cellColIndex++;
                    }
                }
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new ExportingException("Error during export", e);
        }
    }
    
 // Create font from parameters (it only checks the font is exit in parameters)
    private Font createFont(String type, String family, String encoding, int fontSize, int style){
        // Font size
//        if(getParameters().containsKey(type + ".font.size")){
//            try {
//                fontSize = new Integer(getParameters().get(type + ".font.size"))    
//            }
//            catch(Exception e){
//                println e
//            }
//        }
//        
//        // Global font family
//        if(getParameters().containsKey("font.family")){
//            family = getParameters().get("font.family")
//        }
//        
//        // Font family
//        if(getParameters().containsKey(type + ".font.family")){
//            family = getParameters().get(type + ".font.family")
//        }
//        
//        // Font style
//        if(getParameters().containsKey(type + ".font.style")){
//            try {
//                style = fontStyles[getParameters().get(type + ".font.style")]
//            }
//            catch(Exception e){
//                println e
//            }
//        }
//        
//        // Font encoding (to overwrite global encoding)
//        if(getParameters().containsKey(type + ".encoding")){
//            family = getParameters().get(type + ".encoding")
//        }
        
        return FontFactory.getFont(family, encoding, fontSize, style);
    }

    private int getColNumNotHide(JXTable jxTable) {
        int colNumNotHide = 0;
        List<TableColumn> tableColumns = jxTable.getColumns();
        for (TableColumn tableColumn : tableColumns) {
            int maxColSize = tableColumn.getMaxWidth();
            if (maxColSize > 0) {
                colNumNotHide++;
            }
        }
        return colNumNotHide;
        
    }
    
    
    

}

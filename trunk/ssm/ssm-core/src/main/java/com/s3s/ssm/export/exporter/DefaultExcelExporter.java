/*
 * DefaultExcelExporter
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

import java.io.OutputStream;
import java.util.List;

import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;

import com.lowagie.text.Cell;
import com.lowagie.text.Row;
import com.s3s.ssm.export.builder.ExcelBuilder;
import com.s3s.ssm.view.AbstractListView.AdvanceTableModel;

/**
 * @author Chanhchua
 *
 */
public class DefaultExcelExporter extends AbstractExporter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void exportData(OutputStream outputStream, JXTable tblListEntities, AdvanceTableModel tableModel) throws ExportingException {
        try {
            ExcelBuilder builder = new ExcelBuilder();
            builder.createWorkBook(outputStream);
            //TODO get name of Sheet
            builder.createSheet("Export");
            //create header
            List<TableColumn> tableColumns = tblListEntities.getColumns();
            int colNum = tableColumns.size();
            int cellHeaderIndex = 0;
            String strHeader;
            for (int i = 0; i < colNum; i++) {
                int maxColSize = tableColumns.get(i).getMaxWidth();
                if (maxColSize > 0) {
                    strHeader = (String) tableColumns.get(i)
                            .getHeaderValue();
                    builder.createCell(cellHeaderIndex, 0, strHeader);
                    cellHeaderIndex++;
                }
            }
            //create rows
         // generate data for each column
            int pageRows = tableModel.getRowCount();
            for (int i = 1; i <= pageRows; i++) {
                Object valueCell;
                int cellColIndex = 0;
                for (int j = 0; j < colNum; j++) {
                    int maxColSize = tableColumns.get(j).getMaxWidth();
                    if (maxColSize > 0) {
                        valueCell = tableModel.getValueAt(i - 1, j);
                        builder.createCell(cellColIndex, i, valueCell);
                        cellColIndex++;
                    }
                }
            }
            
            builder.write();
        }
        catch(Exception e){
            throw new ExportingException("Error during export", e);
        }

    }
}

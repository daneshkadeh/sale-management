/*
 * DefaultExcelExporter
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

import java.io.OutputStream;
import java.util.List;

import javax.swing.table.TableColumn;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.ObjectUtils;
import org.jdesktop.swingx.JXTable;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.export.builder.ExcelBuilder;
import com.s3s.ssm.view.list.AbstractListView.AdvanceTableModel;

/**
 * @author Le Thanh Hoang
 * 
 */
public class DefaultExcelExporter extends AbstractExporter {
    protected BeanWrapper beanWrapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void exportData(OutputStream outputStream, JXTable tblListEntities, AdvanceTableModel tableModel)
            throws ExportingException {
        try {
            ExcelBuilder builder = new ExcelBuilder();
            builder.createWorkBook(outputStream);
            // TODO get name of Sheet
            builder.createSheet("Sheet");
            // create header
            List<TableColumn> tableColumns = tblListEntities.getColumns();
            int colNum = tableColumns.size();
            int cellHeaderIndex = 0;
            String strHeader;
            for (int i = 0; i < colNum; i++) {
                int maxColSize = tableColumns.get(i).getMaxWidth();
                if (maxColSize > 0) {
                    strHeader = (String) tableColumns.get(i).getHeaderValue();
                    builder.createCell(cellHeaderIndex, 0, strHeader);
                    cellHeaderIndex++;
                }
            }
            // create rows
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
        } catch (Exception e) {
            throw new ExportingException("Error during export", e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void exportData(OutputStream outputStream, List data) throws ExportingException {
        try {
            int colNum = exportFields.size();
            ExcelBuilder builder = new ExcelBuilder();
            builder.createWorkBook(outputStream);
            // TODO get name of Sheet
            builder.createSheet("Sheet");
            // create header
            String strHeader;

            for (int i = 0; i < colNum; i++) {
                strHeader = labels.get(exportFields.get(i));
                builder.createCell(i, 0, strHeader);
            }
            // create rows
            // generate data for each column
            int pageRows = data.size();
            for (int i = 0; i < pageRows; i++) {
                beanWrapper = new BeanWrapperImpl(data.get(i));
                for (int j = 0; j < colNum; j++) {
                    Object value = beanWrapper.getPropertyValue(exportFields.get(j));
                    Class<?> propertyReturnType = beanWrapper.getPropertyType(exportFields.get(j));
                    if (ClassUtils.isAssignable(propertyReturnType, Number.class)) {
                        builder.createCell(j, i + 1, (Number) value);
                    } else {
                        builder.createCell(j, i + 1, ObjectUtils.toString(value));
                    }

                }
            }
            builder.write();
        } catch (Exception e) {
            throw new ExportingException("Error during export", e);
        }

    }
}

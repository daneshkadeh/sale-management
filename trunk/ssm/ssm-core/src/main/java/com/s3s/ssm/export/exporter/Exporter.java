/*
 * Exporter
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
import java.util.Map;

import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;

/**
 * @author Le Thanh Hoang
 * 
 */
public interface Exporter {

    void setExportFields(List<String> fields);

    List<String> getExportFields();

    void setLabels(Map<String, String> labels);

    Map<String, String> getLabels();

    void setFormatters(Map<String, String> formatters);

    Map<String, String> getFormatters();

    void setParameters(Map<String, String> parameters);

    Map<String, String> getParameters();

    void export(OutputStream outputStream, List data) throws ExportingException;

    void export(OutputStream outputStream, JXTable jxTable, TableModel tableModel) throws ExportingException;
    // void export(OutputStream outputStream, JXTable jxTable, AdvanceTableModel tableModel) throws ExportingException;
}

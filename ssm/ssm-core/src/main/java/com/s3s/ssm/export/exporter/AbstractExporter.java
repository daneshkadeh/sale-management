/*
 * AbstractExporter
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdesktop.swingx.JXTable;

import com.s3s.ssm.view.AbstractListView.AdvanceTableModel;

/**
 * @author Le Thanh Hoang
 * 
 */
public abstract class AbstractExporter implements Exporter {
    protected List<String> exportFields = new ArrayList<String>();
    protected Map<String, String> labels = new HashMap<String, String>();
    protected Map<String, String> formatters = new HashMap<String, String>();
    protected Map<String, String> parameters = new HashMap<String, String>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExportFields(List<String> fields) {
        exportFields = fields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getExportFields() {
        return exportFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getLabels() {
        return labels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFormatters(Map<String, String> formatters) {
        this.formatters = formatters;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getFormatters() {
        return formatters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getParameters() {
        // TODO Auto-generated method stub
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void export(OutputStream outputStream, JXTable jxTable, AdvanceTableModel tableModel)
            throws ExportingException {
        exportData(outputStream, jxTable, tableModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void export(OutputStream outputStream, List data) throws ExportingException {
        exportData(outputStream, data);
    }

    protected abstract void exportData(OutputStream outputStream, JXTable jxTable, AdvanceTableModel tableModel)
            throws ExportingException;

    protected abstract void exportData(OutputStream outputStream, List data) throws ExportingException;
}

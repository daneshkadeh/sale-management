/*
 * AbstractExporter
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
    List exportFields = new ArrayList<>();
    Map labels = new HashMap<>();
    Map formatters = new HashMap<>();
    Map parameters = new HashMap<>();
    /**
     * {@inheritDoc}
     */
    @Override
    public void setExportFields(List fields) {
        exportFields = fields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List getExportFields() {
        // TODO Auto-generated method stub
        return exportFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabels(Map labels) {
        this.labels = labels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map getLabels() {
        return labels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFormatters(Map formatters) {
        this.formatters = formatters;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map getFormatters() {
        return formatters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameters(Map parameters) {
        this.parameters = parameters;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map getParameters() {
        // TODO Auto-generated method stub
        return parameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void export(OutputStream outputStream, List data) throws ExportingException {
        if(exportFields.size() > 0){
//            exportData(outputStream, data, exportFields);
        }
        else {
            //TODO implement class ExporterUtil
            //exportData(outputStream, data, ExporterUtil.getFields(data.get(0)));
        }

    }
//    protected abstract void exportData(OutputStream outputStream,List data,List exportFields);
    protected abstract void exportData(OutputStream outputStream, JXTable jxTable, AdvanceTableModel tableModel) throws ExportingException;
}

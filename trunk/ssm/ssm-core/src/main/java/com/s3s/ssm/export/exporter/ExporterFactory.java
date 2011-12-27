/*
 * ExporterFactory
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
import java.util.Map;

import org.jdesktop.swingx.JXTable;

import com.s3s.ssm.view.AbstractListView.AdvanceTableModel;


/**
 * @author Le Thanh Hoang
 *
 */
public interface ExporterFactory {
    Exporter createExporter(ExportTypeEnum type) throws ExporterNotFoundException;
    Exporter createExporter(ExportTypeEnum type, List fields, Map labels, Map formatters, Map parameters) throws ExporterNotFoundException;
    Exporter createExporter(ExportTypeEnum type, Object domain) throws ExporterNotFoundException;
    Exporter createExporter(ExportTypeEnum type, Object domain, List fields, Map labels, Map formatters, Map parameters) throws ExporterNotFoundException;
}

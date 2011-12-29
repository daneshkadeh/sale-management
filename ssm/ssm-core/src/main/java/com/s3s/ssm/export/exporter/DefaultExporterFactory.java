/*
 * DefaultExporterFactory
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;

/**
 * @author Le Thanh Hoang
 * 
 */
public class DefaultExporterFactory implements ExporterFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Exporter createExporter(ExportTypeEnum type) throws ExporterNotFoundException {
        return createExporter(type, null, null, null, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exporter createExporter(ExportTypeEnum type, List fields, Map labels, Map formatters, Map parameters)
            throws ExporterNotFoundException {
        return createExporter(type, null, fields, labels, formatters, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exporter createExporter(ExportTypeEnum type, Object domain) throws ExporterNotFoundException {
        return createExporter(type, domain, null, null, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Exporter createExporter(ExportTypeEnum type, Object domain, List fields, Map labels,
            Map formatters, Map parameters) throws ExporterNotFoundException {
        try {
            Exporter exporter = null;
            switch (type) {
            case CSV:
                // TODO write a exporter for Excel
                // exporter = new DefaultExcelExporter();
                break;
            case EXCEL2003:
                exporter = new DefaultExcelExporter();
                break;
            case EXCEL2007:
                exporter = new DefaultExcelExporter();
                break;
            case PDF:
                exporter = new DefaultPDFExporter();
                break;
            default:
                break;
            }
            if (fields != null) {
                exporter.setExportFields(fields);
            }

            if (labels != null) {
                exporter.setLabels(labels);
            }

            if (formatters != null) {
                exporter.setFormatters(formatters);
            }

            if (parameters != null) {
                exporter.setParameters(parameters);
            } else {
                parameters = getDefPDFParameter();
                exporter.setParameters(parameters);
            }

            return exporter;
        } catch (Exception e) {
            throw new ExporterNotFoundException("No exporter found for type: ${type}", e);
        }

    }

    private Map<String, String> getDefPDFParameter() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("pdf.orientation", "portrait");
        // Possible values Cp1250, Cp1252, Cp1257, Identity-H, Identity-V, MacRoman
        paramMap.put("pdf.encoding", BaseFont.CP1252);
        paramMap.put("title", "DefaultTitle");
        // row number om header
        paramMap.put("header.rows", "1");
        // set font
        paramMap.put("font.family", FontFactory.HELVETICA); // Global font family

        paramMap.put("title.font.size", "10");
        paramMap.put("title.font.family", FontFactory.HELVETICA);
        paramMap.put("title.font.style", "normal");
        paramMap.put("title.encoding", BaseFont.CP1252); // this encoding will override pdf.encoding

        paramMap.put("header.font.size", "10");
        paramMap.put("header.font.family", FontFactory.HELVETICA);
        paramMap.put("header.font.style", "normal");
        paramMap.put("header.encoding", BaseFont.CP1252); // this encoding will override pdf.encoding

        paramMap.put("text.font.size", "10");
        paramMap.put("text.font.family", FontFactory.HELVETICA);
        paramMap.put("text.font.style", "normal");
        paramMap.put("text.encoding", BaseFont.CP1252); // this encoding will override pdf.encoding

        return paramMap;
    }

    private Map<String, String> getDefExcelParameter() {
        Map<String, String> paramMap = new HashMap<String, String>();
        return paramMap;
    }
}

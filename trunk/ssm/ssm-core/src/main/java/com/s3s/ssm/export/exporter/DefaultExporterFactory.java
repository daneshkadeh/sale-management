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

import java.util.List;
import java.util.Map;

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
    public synchronized Exporter createExporter(ExportTypeEnum type, Object domain, List fields, Map labels, Map formatters, Map parameters)
            throws ExporterNotFoundException {
        try {
            Exporter exporter = null;
            switch (type) {
            case CSV:
                //TODO write a exporter for Excel
                //exporter = new DefaultExcelExporter();
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
            if(fields!=null){
                exporter.setExportFields(fields);
            }
            
            if(labels!=null){
                exporter.setLabels(labels);
            }
            
            if(formatters!=null){
                exporter.setFormatters(formatters);
            }
            
            if(parameters!=null){
                exporter.setParameters(parameters);
            }
            
            return exporter;
        }
        catch(Exception e){
            throw new ExporterNotFoundException("No exporter found for type: ${type}", e);
        }
        
    }

}

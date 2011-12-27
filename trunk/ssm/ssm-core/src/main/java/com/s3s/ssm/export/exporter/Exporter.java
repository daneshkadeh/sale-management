/*
 * Exporter
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

/**
 * @author Le Thanh Hoang
 *
 */
public interface Exporter {
    
    void setExportFields(List fields);
    List getExportFields();
    void setLabels(Map labels);
    Map getLabels();
    void setFormatters(Map formatters);
    Map getFormatters();
    void setParameters(Map parameters);
    Map getParameters();
    
    void export(OutputStream outputStream, List data) throws ExportingException;
}

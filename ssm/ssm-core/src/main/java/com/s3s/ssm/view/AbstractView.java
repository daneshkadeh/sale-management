/*
 * AbstractView
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
package com.s3s.ssm.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.DaoHelper;

public abstract class AbstractView extends JPanel {
    private static final long serialVersionUID = 1L;
    protected Map<String, Object> params = new HashMap<String, Object>();

    private DaoHelper daoHelper = ConfigProvider.getInstance().getDaoHelper();

    protected DaoHelper getDaoHelper() {
        return daoHelper;
    }

    public AbstractView(Map<String, Object> params) {
        this.params = params;
    }
}

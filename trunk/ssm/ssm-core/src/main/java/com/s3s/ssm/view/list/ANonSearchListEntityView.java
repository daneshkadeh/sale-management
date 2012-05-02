/*
 * AbstractListView
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
package com.s3s.ssm.view.list;

import java.util.Map;

import javax.swing.Icon;
import javax.swing.JPanel;

import com.s3s.ssm.entity.AbstractBaseIdObject;

/**
 * TODO Phuc: follow to remove the classes extends or using this class.
 * 
 * @deprecated using {@link AListEntityView} instead, this class will be remove later to ensure every list searchable.
 */
public abstract class ANonSearchListEntityView<T extends AbstractBaseIdObject> extends AListEntityView<T> {
    private static final long serialVersionUID = -7276967240967148821L;

    public ANonSearchListEntityView() {
        super();
    }

    public ANonSearchListEntityView(Icon icon, String label, String tooltip) {
        super(icon, label, tooltip);
    }

    public ANonSearchListEntityView(Map<String, Object> request) {
        super(request);
    }

    public ANonSearchListEntityView(Map<String, Object> request, Icon icon, String label, String tooltip) {
        super(request, icon, label, tooltip);
    }

    @Override
    protected JPanel createSearchPanel() {
        return null;
    }

    @Override
    protected void clearCriteria() {
    }

}

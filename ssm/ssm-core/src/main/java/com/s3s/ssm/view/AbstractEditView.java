/*
 * AbstractEditView
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

import com.s3s.ssm.entity.AbstractBaseIdObject;

/**
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractEditView<T extends AbstractBaseIdObject> extends AbstractView {
    private static final long serialVersionUID = 5467303241585854634L;
    protected AbstractListView<T> listView;

    public AbstractEditView(T entity) {
        super();
    }

    public void setListView(AbstractListView<T> listView) {
        this.listView = listView;
    }
}

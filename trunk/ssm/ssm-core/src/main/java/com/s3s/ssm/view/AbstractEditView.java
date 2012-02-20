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
import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractEditView<T extends AbstractIdOLObject> extends AbstractView {
    private static final long serialVersionUID = 5467303241585854634L;
    protected AbstractListView<T> listView;
    protected Class<? extends AbstractBaseIdObject> parentClass;
    protected Long parentId;

    public AbstractEditView(T entity) {
        this(entity, null, null);
    }

    public AbstractEditView(T entity, Long parentId, Class<? extends AbstractBaseIdObject> parentClass) {
        super();
        this.parentId = parentId;
        this.parentClass = parentClass;
    }

    public void setParent(Long parentId, Class<? extends AbstractBaseIdObject> parentClass) {
        this.parentId = parentId;
        this.parentClass = parentClass;
    }

    public Long getParentId() {
        return parentId;
    }

    public Class<? extends AbstractBaseIdObject> getParentClass() {
        return parentClass;
    }

    public void setListView(AbstractListView<T> listView) {
        this.listView = listView;
    }
}

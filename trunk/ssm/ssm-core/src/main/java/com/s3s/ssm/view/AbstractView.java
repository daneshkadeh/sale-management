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

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.DaoHelper;
import com.s3s.ssm.util.ServiceProvider;

public abstract class AbstractView extends JPanel {
    private static final long serialVersionUID = 1L;
    protected Map<String, Object> request = new HashMap<String, Object>();
    protected Class<? extends AbstractBaseIdObject> parentClass;
    protected Long parentId;

    public enum EditActionEnum {
        EDIT, NEW
    }

    protected static final String PARAM_PARENT_CLASS = "parentClass";
    protected static final String PARAM_PARENT_ID = "parentId";
    protected static final String PARAM_ACTION = "action";
    protected static final String PARAM_LIST_VIEW = "listView";
    protected static final String PARAM_ENTITY_ID = "entityId";

    protected DaoHelper daoHelper = ConfigProvider.getInstance().getDaoHelper();

    protected ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();

    protected DaoHelper getDaoHelper() {
        return daoHelper;
    }

    public AbstractView(Map<String, Object> params) {
        this.request = params;
        parentId = (Long) request.get(PARAM_PARENT_ID);
        parentClass = (Class) request.get(PARAM_PARENT_CLASS);
    }
    
    public void setParent(Long parentId, Class<? extends AbstractIdOLObject> parentClass) {
        this.parentId = parentId;
        this.parentClass = parentClass;
    }

    public Object getParentObject() {
        return getDaoHelper().getDao(getParentClass()).findById(getParentId());
    }

    public Long getParentId() {
        return parentId;
    }

    public Class<? extends AbstractBaseIdObject> getParentClass() {
        return parentClass;
    }

}

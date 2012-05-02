/*
 * ListComponentInfo
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

package com.s3s.ssm.view.edit;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.view.list.AListComponent;

/**
 * @author Phan Hong Phuc
 * @since Apr 22, 2012
 */
public class ListComponentInfo implements IComponentInfo {
    private AListComponent<? extends AbstractBaseIdObject> listComponent;
    private String parentFieldName;

    public ListComponentInfo(AListComponent<? extends AbstractBaseIdObject> listComponent, String parentFieldName) {
        this.listComponent = listComponent;
        this.setParentFieldName(parentFieldName);
    }

    public AListComponent<? extends AbstractBaseIdObject> getListComponent() {
        return listComponent;
    }

    public void setListComponent(AListComponent<? extends AbstractBaseIdObject> listComponent) {
        this.listComponent = listComponent;
    }

    public String getParentFieldName() {
        return parentFieldName;
    }

    public void setParentFieldName(String parentFieldName) {
        this.parentFieldName = parentFieldName;
    }
}

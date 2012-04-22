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

import com.s3s.ssm.view.list.AListComponent;

/**
 * @author Phan Hong Phuc
 * @since Apr 22, 2012
 */
@SuppressWarnings("rawtypes")
public class ListComponentInfo implements IComponentInfo {
    private AListComponent listComponent;

    public ListComponentInfo(AListComponent listComponent) {
        this.listComponent = listComponent;
    }

    public AListComponent getListComponent() {
        return listComponent;
    }

    public void setListComponent(AListComponent listComponent) {
        this.listComponent = listComponent;
    }
}

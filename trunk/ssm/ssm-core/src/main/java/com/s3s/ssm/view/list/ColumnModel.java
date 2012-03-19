/*
 * Column
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

import com.s3s.ssm.view.list.ListDataModel.ListColumType;

/**
 * @author Phan Hong Phuc
 * @since Mar 19, 2012
 */
public class ColumnModel {
    private String name;
    private boolean isRaw;
    private ListColumType type;

    public ColumnModel(String name, ListColumType type) {
        super();
        this.setName(name);
        this.setType(type);
    }

    public boolean isRaw() {
        return isRaw;
    }

    public void setRaw(boolean isRaw) {
        this.isRaw = isRaw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListColumType getType() {
        return type;
    }

    public void setType(ListColumType type) {
        this.type = type;
    }

}

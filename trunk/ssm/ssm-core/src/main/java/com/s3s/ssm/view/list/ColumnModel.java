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

import javax.swing.SortOrder;

import com.s3s.ssm.view.list.ListDataModel.ListColumnType;

/**
 * @author Phan Hong Phuc
 * @since Mar 19, 2012
 */
public class ColumnModel {
    private String name;
    private boolean isRaw;
    private ListColumnType type;
    private boolean isSummarized; // show sum values in footer or not. It must be Number type.

    private boolean isSorted;
    private SortOrder sortOrder;

    public ColumnModel(String name, ListColumnType type) {
        super();
        this.name = name;
        this.type = type;
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

    public ListColumnType getType() {
        return type;
    }

    public boolean isSummarized() {
        return isSummarized;
    }

    public ColumnModel summarized() {
        this.isSummarized = true;
        return this;
    }

    public ColumnModel sort(SortOrder order) {
        this.isSorted = true;
        this.sortOrder = order;
        return this;
    }

}

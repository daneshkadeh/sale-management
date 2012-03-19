/*
 * ListDataModel
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phan Hong Phuc
 * @since Mar 19, 2012
 */
public class ListDataModel {
    public enum ListColumType {
        TEXT
    }

    private List<ColumnModel> columns = new ArrayList<>();

    public ListDataModel addColumn(String name, ListColumType type) {
        columns.add(new ColumnModel(name, type));
        return this;
    }

}

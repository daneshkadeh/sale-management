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
    public enum ListRendererType {
        TEXT, BOOLEAN, NUMBER, DATE, LINK, SEARCH_COMPONENT;
    }

    public enum ListEditorType {
        TEXTFIELD, CHECKBOX, COMBOBOX, DATE_PICKER, MONEY, SEARCH_COMPONENT;
    }

    private List<ColumnModel> columns = new ArrayList<>();

    public ColumnModel addColumn(String name, ListRendererType rendererType) {
        ColumnModel columnModel = new ColumnModel(name, rendererType);
        columns.add(columnModel);
        return columnModel;
    }

    public ColumnModel addColumn(String name, ListRendererType rendererType, ListEditorType editorType) {
        ColumnModel columnModel = new ColumnModel(name, rendererType, editorType);
        columns.add(columnModel);
        return columnModel;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public ColumnModel getColumn(int index) {
        return columns.get(index);
    }

    /**
     * Get the searched columns
     * 
     * @return
     */
    public List<ColumnModel> getSearchedColumns() {
        List<ColumnModel> searchedColumns = new ArrayList<>();
        for (ColumnModel columnModel : getColumns()) {
            if (columnModel.isSearched()) {
                searchedColumns.add(columnModel);
            }
        }
        return searchedColumns;
    }
}

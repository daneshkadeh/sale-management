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
        TEXT, BOOLEAN, NUMBER, DATE, LINK
    }

    public enum ListEditorType {
        TEXTFIELD, CHECKBOX, COMBOBOX, DATE_PICKER
    }

    private List<ColumnModel> columns = new ArrayList<>();
    private boolean isEditable = false;

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

    public boolean isEditable() {
        return isEditable;
    }

    /**
     * Set the entire table is editable or not.
     * 
     * @param isEditable
     */
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
}

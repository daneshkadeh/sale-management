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

import org.springframework.util.Assert;

import com.s3s.ssm.view.list.ListDataModel.ListEditorType;
import com.s3s.ssm.view.list.ListDataModel.ListRendererType;

/**
 * @author Phan Hong Phuc
 * @since Mar 19, 2012
 */
public class ColumnModel {
    private String name;
    private boolean isRaw;
    private ListRendererType rendererType;
    private ListEditorType editorType;
    private boolean isSummarized; // show sum values in footer or not. It must be Number type.
    private Object value;
    private boolean isEditable = true;

    private String referenceDataId;

    // For sorting.
    private boolean isSorted;
    private SortOrder sortOrder;
    private int precedence;

    /**
     * Init the column model with name, rendererType, and default value for editorType is
     * {@link ListEditorType#TEXTFIELD}.
     * 
     * @param name
     * @param rendererType
     */
    public ColumnModel(String name, ListRendererType rendererType) {
        this(name, rendererType, ListEditorType.TEXTFIELD);
    }

    public ColumnModel(String name, ListRendererType rendererType, ListEditorType editorType) {
        super();
        this.name = name;
        this.rendererType = rendererType;
        this.editorType = editorType;
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

    public ListRendererType getRendererType() {
        return rendererType;
    }

    public ListEditorType getEditorType() {
        return editorType;
    }

    public boolean isSummarized() {
        return isSummarized;
    }

    public ColumnModel summarized() {
        this.isSummarized = true;
        return this;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public int getPrecedence() {
        return precedence;
    }

    public ColumnModel sort(SortOrder order, int precedence) {
        this.isSorted = true;
        this.sortOrder = order;
        this.precedence = precedence;
        return this;
    }

    public Object getValue() {
        return value;
    }

    /**
     * Set the initial value for the raw attribute.
     * 
     * @param value
     *            the initial value for the attribute.
     * @return
     */
    public ColumnModel value(Object value) {
        Assert.isTrue(isRaw, "Not allow to set value for not raw attribute.");
        this.value = value;
        return this;
    }

    public String getReferenceDataId() {
        return referenceDataId;
    }

    public ColumnModel referenceDataId(String referenceDataId) {
        Assert.isTrue(editorType == ListEditorType.COMBOBOX, "Reference data is now just supported for "
                + ListEditorType.COMBOBOX.toString() + " type");
        this.referenceDataId = referenceDataId;
        return this;
    }

    public boolean isEditable() {
        return isEditable;
    }

    /**
     * Set this column can not be editable <br/>
     * Pay attention: this method is just meaningful when the {@link ListDataModel#isEditable()} is true.
     * 
     * @param isEditable
     * @return
     */
    public ColumnModel notEditable() {
        this.isEditable = false;
        return this;
    }

}

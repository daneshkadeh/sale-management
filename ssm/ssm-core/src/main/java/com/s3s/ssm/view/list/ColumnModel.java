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

import com.s3s.ssm.view.edit.IComponentInfo;
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
    private String cacheDataId;

    private IComponentInfo componentInfo;

    // For sorting.
    private boolean isSorted;
    private SortOrder sortOrder;
    private int precedence;

    // For searching
    private boolean isSearched = false;

    // null value stand for default width
    private Integer width;
    private Integer maxWidth;
    private Integer minWidth;

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
        Assert.isTrue(editorType == ListEditorType.COMBOBOX || editorType == ListEditorType.MONEY,
                "Reference data is now just supported for " + ListEditorType.COMBOBOX.toString() + " and "
                        + ListEditorType.MONEY + " type");
        this.referenceDataId = referenceDataId;
        return this;
    }

    public String getCacheDataId() {
        return cacheDataId;
    }

    public ColumnModel cacheDataId(String cacheDataId) {
        this.cacheDataId = cacheDataId;
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

    public Integer getWidth() {
        return width;
    }

    /**
     * Set prefer width, min width, max width. </br> By default, the table already automatically calculate the width of
     * column base on it contents.
     * 
     * @param width
     *            the prefer width, null if default.
     * @param minWidth
     *            the min width, null if default.
     * @param maxWidth
     *            the max width, null if default.
     * @return
     */
    public ColumnModel width(Integer width, Integer minWidth, Integer maxWidth) {
        this.width = width;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * Set prefer width, min and max width is not cared in this case.
     * 
     * @param width
     *            the prefer width, null if default.
     * @return
     */
    public ColumnModel width(Integer width) {
        return this.width(width, null, null);
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public Integer getMinWidth() {
        return minWidth;
    }

    public boolean isSearched() {
        return isSearched;
    }

    public ColumnModel searched() {
        this.isSearched = true;
        return this;
    }

    public IComponentInfo getComponentInfo() {
        return componentInfo;
    }

    public ColumnModel componentInfo(IComponentInfo componentInfo) {
        this.componentInfo = componentInfo;
        return this;
    }
}

/*
 * DetailAttribute
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

import org.springframework.util.Assert;

import com.s3s.ssm.util.view.UIConstants;
import com.s3s.ssm.view.edit.DetailDataModel.DetailFieldType;

public class DetailAttribute {
    private String name;
    private DetailFieldType type;
    private boolean isEditable = true;
    private boolean isEnable = true;
    private boolean isMandatory = false;
    private Object value; // The initial value for the raw attribute.
    private boolean isRaw = false;
    private boolean isAutoComplete = false;
    // private List<ParentNodeInfo> parentNodePath = new ArrayList<>();

    /** The property for layout the attribute. The attribute is rendered in new line or not. */
    private boolean newColumn = false;
    private int width;

    private String referenceDataId;

    private String cacheDataId;
    private String label;

    public DetailAttribute(String name, DetailFieldType type) {
        this(name, type, false);
    }

    public DetailAttribute(String name, DetailFieldType type, boolean raw) {
        this.name = name;
        this.type = type;
        this.isRaw = raw;
    }

    public String getName() {
        return name;
    }

    public DetailFieldType getType() {
        return type;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public DetailAttribute editable(boolean isEditable) {
        this.isEditable = isEditable;
        return this;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public DetailAttribute enable(boolean isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    /**
     * Id to identify the referenceData of property in ReferenceDataModel
     * 
     * @param referenceDataId
     */
    public DetailAttribute referenceDataId(String referenceDataId) {
        this.referenceDataId = referenceDataId;
        return this;
    }

    public String getReferenceDataId() {
        return referenceDataId;
    }

    public String getCacheDataId() {
        return cacheDataId;
    }

    public DetailAttribute cacheDataId(String cacheDataId) {
        this.cacheDataId = cacheDataId;
        return this;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public DetailAttribute mandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
        return this;
    }

    /** Add new column. (The attribute will be rendered in new column). */
    public DetailAttribute newColumn() {
        this.newColumn = true;
        return this;
    }

    public boolean isNewColumn() {
        return newColumn;
    }

    /**
     * The width of field. The default value is {@link UIConstants#DEFAULT_WIDTH}.
     * 
     * @param width
     * @return
     */
    public DetailAttribute width(int width) {
        this.width = width;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public boolean isRaw() {
        return isRaw;
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
    public DetailAttribute value(Object value) {
        Assert.isTrue(isRaw, "Not allow to set value for not raw attribute.");
        this.value = value;
        return this;
    }

    public boolean isAutoComplete() {
        return isAutoComplete;
    }

    public DetailAttribute autoComplete() {
        Assert.isTrue(type == DetailFieldType.DROPDOWN, "Auto complete is just used for DROPDOWN type");
        this.isAutoComplete = true;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public DetailAttribute label(String label) {
        this.label = label;
        return this;
    }
}

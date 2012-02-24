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
package com.s3s.ssm.model;

import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;
import com.s3s.ssm.util.view.UIConstants;

public class DetailAttribute {
    private String name;
    private FieldTypeEnum type;
    private boolean isEditable = true;
    private boolean isEnable = true;
    private boolean isMandatory = false;

    /** The property for layout the attribute. The attribute after this attribute is rendered in new line or not. */
    private boolean newColumn = false;
    private int width;

    private String referenceDataId;

    public DetailAttribute(String name, FieldTypeEnum type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public FieldTypeEnum getType() {
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

    public boolean isMandatory() {
        return isMandatory;
    }

    public DetailAttribute mandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
        return this;
    }

    /** Add new column. (The next attribute will be rendered in new column). */
    public DetailAttribute newColumn() {
        this.newColumn = true;
        return this;
    }

    public boolean isNewColumn() {
        return newColumn;
    }

    /**
     * The width of field. The default value for {@link FieldTypeEnum#TEXTAREA}, {@link FieldTypeEnum#TEXTBOX} is
     * {@link UIConstants#DEFAULT_WIDTH}.
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
}

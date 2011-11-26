package com.s3s.ssm.model;

import com.s3s.ssm.model.DetailDataModel.FieldTypeEnum;

public class DetailAttribute {
    private String name;
    private FieldTypeEnum type;
    private boolean isEditable = true;
    private boolean isEnable = true;
    private boolean isMandatory = false;
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

    public DetailAttribute setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
        return this;
    }

}

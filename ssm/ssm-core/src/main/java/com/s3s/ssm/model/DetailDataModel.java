package com.s3s.ssm.model;

public class DetailDataModel {
    private String fieldName;
    private FieldTypeEnum fieldType;
    private boolean isEditable = true;
    private boolean isEnable = true;
    private String referenceDataId;

    public DetailDataModel(String fieldName, FieldTypeEnum fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldTypeEnum getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldTypeEnum fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public DetailDataModel setEditable(boolean isEditable) {
        this.isEditable = isEditable;
        return this;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public DetailDataModel setEnable(boolean isEnable) {
        this.isEnable = isEnable;
        return this;
    }

    /**
     * Id to identify the referenceData of property in ReferenceDataModel
     * 
     * @param referenceDataId
     */
    public void setReferenceDataId(String referenceDataId) {
        this.referenceDataId = referenceDataId;
    }

    public String getReferenceDataId() {
        return referenceDataId;
    }

}

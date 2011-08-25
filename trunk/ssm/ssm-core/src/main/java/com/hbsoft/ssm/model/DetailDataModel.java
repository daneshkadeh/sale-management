package com.hbsoft.ssm.model;

public class DetailDataModel {
    private String fieldName;
    private FieldTypeEnum fieldType;
    private boolean isEditable;
    private boolean isEnable;

    public DetailDataModel(String fieldName, FieldTypeEnum fieldType, boolean isEditable, boolean isEnable) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.isEditable = isEditable;
        this.isEnable = isEnable;
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

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

}

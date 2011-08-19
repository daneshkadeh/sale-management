package com.hbsoft.ssm.view.object;

public class DetailDataModel {
	private String label;
	private String fieldName;
	private FieldTypeEnum fieldType;
	private Class<?> clazz;
	private boolean isEditable;
	private boolean isEnable;

	public DetailDataModel(String label, String fieldName, FieldTypeEnum fieldType, boolean editable, boolean enable) {
		this.label = label;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		isEditable = editable;
		isEnable = enable;
		// this.setClazz(clazz);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
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

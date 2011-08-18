package com.hbsoft.ssm.view.object;

public class DetailDataModel {
	private String label;
	private String fieldName;
	private FieldType fieldType;
	private Class<?> clazz;
	private boolean editable;
	private boolean enable;

	public DetailDataModel(String label, String fieldName, FieldType fieldType, boolean editable, boolean enable) {
		this.label = label;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.editable = editable;
		this.enable = enable;
		// this.setClazz(clazz);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}

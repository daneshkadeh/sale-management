package com.s3s.ssm.model;

import java.util.ArrayList;
import java.util.List;

public class DetailDataModel {
    public enum FieldTypeEnum {
        TEXTBOX, DROPDOWN, MULTI_SELECT_BOX, PASSWORD, CHECKBOX, DATE, RADIO_BUTTON, IMAGE;
    }

    private List<DetailAttribute> detailAttributes = new ArrayList<>();

    public DetailAttribute addAttribute(String name, FieldTypeEnum fieldType) {
        DetailAttribute attribute = new DetailAttribute(name, fieldType);
        detailAttributes.add(attribute);
        return attribute;
    }

    public List<DetailAttribute> getDetailAttributes() {
        return detailAttributes;
    }
}

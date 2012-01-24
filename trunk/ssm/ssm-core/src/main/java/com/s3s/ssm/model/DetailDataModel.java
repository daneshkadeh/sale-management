package com.s3s.ssm.model;

import java.util.ArrayList;
import java.util.List;

public class DetailDataModel {
    public enum FieldTypeEnum {
        TEXTBOX, RICH_TEXTBOX, DROPDOWN, MULTI_SELECT_BOX, PASSWORD, CHECKBOX, DATE, RADIO_BUTTON, IMAGE, FILE_CHOOSER, ENTITY_CHOOSER, SALE_TARGET, SEX_RADIO;
    }

    private List<DetailAttribute> detailAttributes = new ArrayList<>();

    private List<TabInfoData> tabList = new ArrayList<>();

    /**
     * Tab data structure store info of the tabs. Including the index of <code>detailAttributes</code> to start tab and
     * corresponding name of tab.
     */
    public class TabInfoData {
        private int index;
        private String name;

        public TabInfoData(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public DetailAttribute addAttribute(String name, FieldTypeEnum fieldType) {
        DetailAttribute attribute = new DetailAttribute(name, fieldType);
        detailAttributes.add(attribute);
        return attribute;
    }

    public List<DetailAttribute> getDetailAttributes() {
        return detailAttributes;
    }

    /**
     * Layout the tab.
     * 
     * @param name
     *            the title of the tab.
     * @return
     */
    public DetailDataModel tab(String name) {
        tabList.add(new TabInfoData(detailAttributes.size(), name));
        return this;
    }

}

/*
 * DetailDataModel
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

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

public class DetailDataModel {
    public enum FieldTypeEnum {
        TEXTBOX, TEXTAREA, DROPDOWN, MULTI_SELECT_BOX, PASSWORD, CHECKBOX, DATE, RADIO_BUTTON_GROUP, IMAGE, FILE_CHOOSER, ENTITY_CHOOSER, SALE_TARGET, SEX_RADIO;
    }

    private List<DetailAttribute> detailAttributes = new ArrayList<>();
    private int numColumnDefault = 2;
    private List<TabInfoData> tabList = new ArrayList<>();

    /**
     * Tab data structure store info of the tabs. Including the index of <code>detailAttributes</code> to start tab and
     * corresponding name of tab.
     */
    public class TabInfoData {
        private int index;
        private String name;
        private String tooltip;
        private Icon icon;

        public TabInfoData(int index, String name, String tooltip, Icon icon) {
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

        public String getTooltip() {
            return tooltip;
        }

        public void setTooltip(String tooltip) {
            this.tooltip = tooltip;
        }

        public Icon getIcon() {
            return icon;
        }

        public void setIcon(Icon icon) {
            this.icon = icon;
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

    public List<TabInfoData> getTabList() {
        return tabList;
    }

    /**
     * Layout the tab.
     * 
     * @param name
     *            the title of the tab.
     * @return
     */
    public DetailDataModel tab(String name, String tooltip, Icon icon) {
        tabList.add(new TabInfoData(detailAttributes.size(), name, tooltip, icon));
        return this;
    }

    public int getNumColumnDefault() {
        return numColumnDefault;
    }

    public void setNumColumnDefault(int numColumnDefault) {
        if(numColumnDefault <= 0){
            throw new RuntimeException("Num of column must greater than 0");
        }
        this.numColumnDefault = numColumnDefault;
    }

}

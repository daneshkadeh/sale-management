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

import org.apache.commons.collections.CollectionUtils;

public class DetailDataModel {
    public enum FieldTypeEnum {
        TEXTBOX, TEXTAREA, DROPDOWN, MULTI_SELECT_BOX, PASSWORD, CHECKBOX, DATE, RADIO_BUTTON_GROUP, IMAGE, FILE_CHOOSER, ENTITY_CHOOSER, SALE_TARGET, SEX_RADIO;
    }

    private List<DetailAttribute> detailAttributes = new ArrayList<>();
    private int maxColumn = 2;

    // ///// Manage tabs /////////
    private int currentTabIndex = -1;
    private List<TabInfoData> tabList = new ArrayList<>();

    /**
     * Tab data structure store info of the tabs. Including the index of <code>detailAttributes</code> to start tab and
     * corresponding name of tab.
     */
    public class TabInfoData {
        /** The inclusive index of element in detailAttributes to start tab. */
        private int startIndex;
        
        /** The exclusive index of detailAttributes to end tab. */
        private int endIndex;
        private String name;
        private String tooltip;
        private Icon icon;

        public TabInfoData(int startIndex, String name, String tooltip, Icon icon) {
            this.startIndex = startIndex;
            this.name = name;
            this.tooltip = tooltip;
            this.icon = icon;
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

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
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
    public void startTab(String name, String tooltip, Icon icon) {
        tabList.add(new TabInfoData(detailAttributes.size() - 1, name, tooltip, icon));
        currentTabIndex++;
    }

    public void endTab() {
        if(currentTabIndex >= 0){
            tabList.get(currentTabIndex).setEndIndex(detailAttributes.size());
            currentTabIndex--;
        } else {
            throw new RuntimeException("Forget to startTab");
        }
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public void setMaxColumn(int maxColumn) {
        if (maxColumn <= 0) {
            throw new RuntimeException("Num of column must greater than 0");
        }
        this.maxColumn = maxColumn;
    }

}

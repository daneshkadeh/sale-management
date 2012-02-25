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
        TEXTBOX, TEXTAREA, DROPDOWN, MULTI_SELECT_BOX, PASSWORD, CHECKBOX, DATE, RADIO_BUTTON_GROUP, IMAGE, FILE_CHOOSER, ENTITY_CHOOSER, SALE_TARGET;
    }

    private List<DetailAttribute> detailAttributes = new ArrayList<>();

    // ///// Manage tabs /////////
    private List<TabInfoData> tabList = new ArrayList<>();

    // //// Manage groups ////////
    private List<GroupInfoData> groupList = new ArrayList<>();

    /**
     * Tab data structure store info of the tabs. Including the index of <code>detailAttributes</code> to start tab and
     * corresponding name of tab.
     */
    public class TabInfoData {
        /** The inclusive index of element in detailAttributes to start tab. */
        private int startIndex;

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

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

    }

    public class GroupInfoData {
        /** The inclusive index of element in detailAttributes to start group. */
        private int startGroupIndex;
        /** The exclusive index of element in detailAttributes to end group. */
        private int endGroupIndex;
        private String name;

        public GroupInfoData(int startGroupIndex, String name) {
            this.startGroupIndex = startGroupIndex;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStartGroupIndex() {
            return startGroupIndex;
        }

        public void setStartGroupIndex(int startGroupIndex) {
            this.startGroupIndex = startGroupIndex;
        }

        public int getEndGroupIndex() {
            return endGroupIndex;
        }

        public void setEndGroupIndex(int endGroupIndex) {
            this.endGroupIndex = endGroupIndex;
        }
    }

    public DetailAttribute addAttribute(String name, FieldTypeEnum fieldType) {
        return addAttribute(name, fieldType, false);
    }
    
    public DetailAttribute addRawAttribute(String name, FieldTypeEnum fieldType) {
        return addAttribute(name, fieldType, true);
    }
    
    private DetailAttribute addAttribute(String name, FieldTypeEnum fieldType, boolean isRaw){
        DetailAttribute attribute = new DetailAttribute(name, fieldType, isRaw);
        detailAttributes.add(attribute);
        return attribute;
    }

    public List<DetailAttribute> getDetailAttributes() {
        return detailAttributes;
    }

    public List<TabInfoData> getTabList() {
        return tabList;
    }

    public List<GroupInfoData> getGroupList() {
        return groupList;
    }

    /**
     * Layout the tab.
     * 
     * @param name
     *            the title of the tab.
     * @return
     */
    public void tab(String name, String tooltip, Icon icon) {
        tabList.add(new TabInfoData(detailAttributes.size(), name, tooltip, icon));
    }

    public void startGroup(String name) {
        groupList.add(new GroupInfoData(detailAttributes.size(), name));
    }

    public void endGroup() {
        groupList.get(groupList.size() - 1).setEndGroupIndex(detailAttributes.size());
    }
}

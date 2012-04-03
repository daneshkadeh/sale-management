/*
 * AdvanceTableModel
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

package com.s3s.ssm.view.list;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.util.Solution3sClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Phan Hong Phuc
 * @since Apr 3, 2012
 */
public class AdvanceTableModel<T extends AbstractBaseIdObject> extends AbstractTableModel {
    private static final long serialVersionUID = -4720974982417224609L;

    private ListDataModel listDataModel;
    private List<T> entities;
    private Class<T> clazz;
    protected BeanWrapper beanWrapper;

    public AdvanceTableModel(ListDataModel listDataModel, List<T> entities, Class<T> clazz) {
        this.listDataModel = listDataModel;
        this.entities = entities;
        this.clazz = clazz;
    }

    @Override
    public int getRowCount() {
        return entities.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T entity = entities.get(rowIndex);
        beanWrapper = new BeanWrapperImpl(entity);
        // The hide extra column contain the entity ID
        if (columnIndex == 0) {
            return entity.getId();
        }

        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex - 1);
        return beanWrapper.getPropertyValue(dataModel.getName());
    }

    @Override
    public int getColumnCount() {
        return listDataModel.getColumns().size() + 1; // Add a more column contain entity value.
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "";
        }
        return ControlConfigUtils.getString("label." + clazz.getSimpleName() + "."
                + listDataModel.getColumns().get(column - 1).getName());
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        T entity = entities.get(rowIndex);
        beanWrapper = new BeanWrapperImpl(entity);
        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);
        beanWrapper.setPropertyValue(dataModel.getName(), aValue);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Long.class; // Type of entityId is Long.
        }
        return getClassOfField(listDataModel.getColumns().get(columnIndex - 1).getName());
    }

    private Class<?> getClassOfField(String fieldName) {
        String[] paths = StringUtils.split(fieldName, '.');
        Class<?> c = clazz; // original class is class of current entity.
        for (String path : paths) {
            c = Solution3sClassUtils.getGetterMethod(c, path).getReturnType();
        }
        return c;
    }
}
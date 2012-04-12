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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log logger = LogFactory.getLog(AdvanceTableModel.class);
    private ListDataModel listDataModel;
    private List<T> entities;
    private Class<T> clazz;
    protected BeanWrapper beanWrapper;
    protected AbstractListView<T> listView;

    public AdvanceTableModel(ListDataModel listDataModel, List<T> entities, Class<T> clazz, AbstractListView<T> listView) {
        this.listDataModel = listDataModel;
        this.entities = entities;
        this.clazz = clazz;
        this.listView = listView;
    }

    @Override
    public int getRowCount() {
        return listDataModel.isEditable() ? entities.size() + 1 : entities.size(); // An empty row to add a new entity.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == entities.size()) {
            return null;
        }
        T entity = entities.get(rowIndex);
        beanWrapper = new BeanWrapperImpl(entity);
        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);
        return beanWrapper.getPropertyValue(dataModel.getName());
    }

    @Override
    public int getColumnCount() {
        return listDataModel.getColumns().size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (listDataModel.isEditable() && rowIndex != entities.size()) {
            return listDataModel.getColumns().get(columnIndex).isEditable();
        }
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return ControlConfigUtils.getString("label." + clazz.getSimpleName() + "."
                + listDataModel.getColumns().get(column).getName());
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex == entities.size()) {
            return;
        }
        T entity = entities.get(rowIndex);
        beanWrapper = new BeanWrapperImpl(entity);
        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);
        Object value = beanWrapper.getPropertyValue(dataModel.getName());
        if (!ObjectUtils.equals(value, aValue)) {
            beanWrapper.setPropertyValue(dataModel.getName(), aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        // //////////////////////////////////////////////
        // TODO Phuc: Reminding should fire on the cellChanged instead of rowChanged? In that case, the ColumnModel need
        // listen() function. And the code will be like this:
        // fireTableCellUpdated(rowIndex, columnIndex);
        // for (int i = 0; i < listDataModel.getColumns().size(); i++) {
        // if (listDataModel.getColumn(i).isListener()) {
        // fireTableCellUpdated(rowIndex, i);
        // }
        // }
        // ////////////////////////////////////////////////
    }

    public void addNewRowAt(int index) {
        try {
            T entity = listView.initEntity(clazz.newInstance());
            entities.add(index, entity);
            fireTableRowsInserted(entities.size() - 1, entities.size() - 1);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error when create an empty entity for the new row");
            e.printStackTrace();
        }
    }

    /**
     * Delete a range of rows.
     * 
     * @param firstRow
     *            first index of row, inclusive
     * @param lastRow
     *            last index of row, inclusive
     */
    public void deleteRows(int firstRow, int lastRow) {
        for (int i = firstRow; i <= lastRow; i++) {
            entities.remove(firstRow);
        }
        fireTableRowsDeleted(firstRow, lastRow);
    }

    public T getEntity(int index) {
        return entities.get(index);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Solution3sClassUtils.getClassOfField(listDataModel.getColumns().get(columnIndex).getName(), clazz);
    }

}

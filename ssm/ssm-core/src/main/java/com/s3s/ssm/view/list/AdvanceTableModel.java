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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;

import com.s3s.ssm.util.IziClassUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Phan Hong Phuc
 * @since Apr 3, 2012
 */
public class AdvanceTableModel<T> extends AbstractTableModel {
    private static final long serialVersionUID = -4720974982417224609L;

    private static final Log logger = LogFactory.getLog(AdvanceTableModel.class);
    private ListDataModel listDataModel;
    private List<T> entities;
    private boolean isEditable;
    private Class<T> clazz;
    private int visibleRowCount;
    protected BeanWrapper beanWrapper;
    protected ICallbackAdvanceTableModel<T> iCallback;

    public AdvanceTableModel(ListDataModel listDataModel, List<T> entities, Class<T> clazz, boolean isEditable,
            int visibleRowCount, ICallbackAdvanceTableModel<T> iCallbackAdvanceTableModel) {
        this.listDataModel = listDataModel;
        this.entities = entities;
        this.isEditable = isEditable;
        this.clazz = clazz;
        this.visibleRowCount = visibleRowCount;
        iCallback = iCallbackAdvanceTableModel;
    }

    @Override
    public int getRowCount() {
        if (isEditable) {
            return entities.size() < visibleRowCount ? visibleRowCount : entities.size();
        }
        return entities.size();

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= entities.size()) {
            return null;
        }
        T entity = entities.get(rowIndex);

        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);
        return iCallback.getAttributeValue(entity, dataModel);
    }

    @Override
    public int getColumnCount() {
        return listDataModel.getColumns().size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return isEditable && listDataModel.getColumns().get(columnIndex).isEditable() && rowIndex < entities.size();
    }

    @Override
    public String getColumnName(int column) {
        return ControlConfigUtils.getString("label." + clazz.getSimpleName() + "."
                + listDataModel.getColumns().get(column).getName());
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex >= entities.size()) {
            return;
        }

        T entity = entities.get(rowIndex);

        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);

        Object value = iCallback.getAttributeValue(entity, dataModel);
        if (!ObjectUtils.equals(value, aValue)) {
            iCallback.setAttributeValue(entity, dataModel, aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }

    }

    public void addRowAt(int index, T entity) {
        entities.add(index, entity);
        fireTableRowsInserted(index, index);
    }

    public void setData(Collection<T> entities) {
        this.entities.removeAll(this.entities);
        this.entities.addAll(entities);
        fireTableDataChanged();
    }

    public List<T> getData() {
        return entities;
    }

    /**
     * Delete a range of rows.
     * 
     * @param firstRow
     *            first index of row, inclusive
     * @param lastRow
     *            last index of row, inclusive
     */
    public void deleteRows(int[] deletedIndex) {
        List<T> deletedEntities = new ArrayList<>(deletedIndex.length);
        for (int i : deletedIndex) {
            deletedEntities.add(entities.get(i));
        }
        entities.removeAll(deletedEntities);

        // TODO Phuc
        fireTableRowsDeleted(deletedIndex[0], deletedIndex[deletedIndex.length - 1]); // Just notify some rows was
                                                                                      // deleted.
    }

    public T getEntity(int index) {
        return entities.get(index);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return IziClassUtils.getClassOfField(listDataModel.getColumns().get(columnIndex).getName(), clazz);
    }

    public boolean isEditable() {
        return isEditable;
    }

}

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
        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);
        return beanWrapper.getPropertyValue(dataModel.getName());
    }

    @Override
    public int getColumnCount() {
        return listDataModel.getColumns().size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (listDataModel.isEditable()) {
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
        T entity = entities.get(rowIndex);
        beanWrapper = new BeanWrapperImpl(entity);
        ColumnModel dataModel = listDataModel.getColumns().get(columnIndex);
        beanWrapper.setPropertyValue(dataModel.getName(), aValue);

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
        fireTableRowsUpdated(rowIndex, rowIndex);

    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Solution3sClassUtils.getClassOfField(listDataModel.getColumns().get(columnIndex).getName(), clazz);
    }

}

/*
 * AdvanceCellEditor
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

import java.awt.Component;
import java.util.Arrays;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.s3s.ssm.model.Money;
import com.s3s.ssm.view.component.MoneyComponent;

/**
 * @author Phan Hong Phuc
 * @since Apr 5, 2012
 */
public class MoneyCellEditor extends AbstractCellEditor implements TableCellEditor, CellEditor {
    private static final long serialVersionUID = -8608804755026438690L;
    private MoneyComponent moneyComponent;

    public MoneyCellEditor(MoneyComponent moneyComponent) {
        this.moneyComponent = (moneyComponent == null) ? new MoneyComponent(Money.zero("VND"), Arrays.asList("VND",
                "USD")) : moneyComponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getCellEditorValue() {
        return moneyComponent.getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        moneyComponent.setMoney((Money) value);
        return moneyComponent;
    }

}

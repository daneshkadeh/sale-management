/*
 * BooleanRenderer
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

package com.s3s.ssm.view.list.editor;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang.BooleanUtils;

import com.s3s.ssm.util.IziImageConstants;
import com.s3s.ssm.util.IziImageUtils;

/**
 * @author Phan Hong Phuc
 * @since May 2, 2012
 */
public class SBooleanCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
    private static final long serialVersionUID = -574910107761337674L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Icon icon = BooleanUtils.isTrue((Boolean) value) ? IziImageUtils.getSmallIcon(IziImageConstants.ACTIVATE_ICON)
                : IziImageUtils.getSmallIcon(IziImageConstants.INACTIVATE_ICON);
        setIcon(icon);
        setText(null);
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
}

/*
 * FooterRenderer
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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * @author Phan Hong Phuc
 * @since Apr 26, 2012
 */
public class FooterRenderer extends JLabel implements TableCellRenderer {
    private static final long serialVersionUID = -6965018746139464733L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        // setBackground(Color.LIGHT_GRAY);
        if (value != null) {
            setText(value.toString());
            setHorizontalAlignment(SwingConstants.RIGHT);
            setBorder(BorderFactory.createRaisedBevelBorder());
        } else {
            setText(null);
        }
        setOpaque(true);
        return this;
    }

}

/*
 * AbstractStatisticListView
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

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * The list view containing buttons of the statistic list screen:
 * <ul>
 * <li>Print</li>
 * <li>Open Excel</li>
 * <li>Cancel</li>
 * </ul>
 * 
 * @see AbstractCommonListView
 * @see AbstractListView
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractStatisticListView<T extends AbstractIdOLObject> extends AbstractListView<T> {
    private static final long serialVersionUID = -1081095962690319904L;

    @Override
    protected JToolBar createButtonToolBar() {
        JToolBar pnlButton = new JToolBar();
        JButton btnPrint = new JButton(ControlConfigUtils.getString("ListView.Statistic.Button.Print"));
        JButton btnOpenExcel = new JButton(ControlConfigUtils.getString("ListView.Statistic.Button.OpenExcel"));
        JButton btnCancel = new JButton(ControlConfigUtils.getString("ListView.Statistic.Button.Cancel"));

        pnlButton.add(btnPrint);
        pnlButton.add(btnOpenExcel);
        pnlButton.add(btnCancel);

        return pnlButton;
    }

}

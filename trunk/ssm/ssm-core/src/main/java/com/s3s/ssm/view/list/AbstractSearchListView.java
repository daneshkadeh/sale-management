/*
 * AbstractSearchListView
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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTaskPane;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * The list view with the search panel.
 * 
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractSearchListView<T extends AbstractIdOLObject> extends AbstractListView<T> {
    private static final long serialVersionUID = -2256837250215615557L;

    protected abstract JPanel createSearchPanel();

    /**
     * Clear the criteria in search panel.
     */
    protected abstract void clearCriteria();

    protected JPanel createSearchButtonsPanel() {
        JButton btnSearch = new JButton(ImageUtils.getMediumIcon(ImageConstants.SEARCH_ICON));
        btnSearch.setToolTipText(ControlConfigUtils.getString("tooltip.search"));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        JButton btnClear = new JButton(ImageUtils.getMediumIcon(ImageConstants.CLEAR_ICON));
        btnClear.setToolTipText(ControlConfigUtils.getString("tooltip.clearCriteria"));
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clearCriteria();
            }
        });

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(btnSearch);
        panel.add(btnClear);
        return panel;
    }

    @Override
    protected void addComponents() {
        JXTaskPane pane = new JXTaskPane();
        pane.setTitle(ControlConfigUtils.getString("label.search.searchTitle"));
        pane.setIcon(ImageUtils.getSmallIcon(ImageConstants.SEARCH_ICON));
        pane.setCollapsed(true);
        pane.add(createSearchPanel());
        pane.add(createSearchButtonsPanel());
        contentPane.add(pane);
        super.addComponents();
    }

}

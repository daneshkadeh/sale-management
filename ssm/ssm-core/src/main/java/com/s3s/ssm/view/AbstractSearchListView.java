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
package com.s3s.ssm.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.s3s.ssm.entity.AbstractIdOLObject;

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
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        JButton btnClear = new JButton("Clear");
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
        add(createSearchPanel());
        add(createSearchButtonsPanel());
        super.addComponents();
    }

}

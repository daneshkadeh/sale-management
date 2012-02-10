/*
 * AbstractDomain
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

package com.s3s.ssm.view.component;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import com.s3s.ssm.view.TreeView;

/**
 * The domain button with the particular tree which displayed on the left of main screen.
 * 
 * @author Phan Hong Phuc
 * @since Nov 19, 2011
 * 
 */
public abstract class AbstractDomain extends JToggleButton implements ItemListener {
    private static final long serialVersionUID = -3288047545897833014L;

    private JScrollPane treeScrollPane;
    private JScrollPane contentScrollPane;
    private TreeView treeView;

    public AbstractDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super();
        this.treeScrollPane = treeScrollPane;
        this.contentScrollPane = contentScrollPane;
        treeView = getTreeView();
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setHorizontalAlignment(LEFT);
        addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
            treeScrollPane.setViewportView(treeView);
            contentScrollPane.setViewportView(treeView.getCurrentView());
        }
    }

    private TreeView getTreeView() {
        TreeView treeView = new TreeView(contentScrollPane);
        constructTreeView(treeView);
        return treeView;
    }

    protected abstract void constructTreeView(TreeView treeView);
}

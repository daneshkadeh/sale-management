/*
 * S3sTreeView
 * 
 * Project: SSM
 * 
 * Copyright 2010 by S3SSoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of S3SSoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with S3SSoft.
 */

package com.s3s.ssm.view.component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.s3s.ssm.view.IViewLazyLoadable;

/**
 * The tree view.
 * 
 * @author Phan Hong Phuc
 * @since Nov 19, 2011
 * 
 */
public class TreeView extends JTree implements TreeSelectionListener {
    private static final long serialVersionUID = -3487864445665189571L;
    private JScrollPane contentScrollPane;
    private JPanel currentView;

    public TreeView(JScrollPane contentScrollPane) {
        super();
        this.contentScrollPane = contentScrollPane;
        addTreeSelectionListener(this);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreeNodeWithView node = (TreeNodeWithView) getLastSelectedPathComponent();
        if (node == null) {
            return;
        }

        JPanel viewOfNode = node.getView();
        if (viewOfNode == null) {
            return;
        }
        if (viewOfNode instanceof IViewLazyLoadable) {
            ((IViewLazyLoadable) viewOfNode).loadView();
        }
        contentScrollPane.setViewportView(viewOfNode);
        currentView = viewOfNode;
    }

    /**
     * Get the current view which is the view assigned with the focusing node of the tree.
     * 
     * @return the current view
     */
    public JPanel getCurrentView() {
        return currentView;
    }
}

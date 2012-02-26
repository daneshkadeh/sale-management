/*
 * TreeView
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

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;

import com.s3s.ssm.view.TreeNodeWithView.NodeValue;

/**
 * The tree view with the node is {@link TreeNodeWithView}.
 * 
 * @author Phan Hong Phuc
 * @since Nov 19, 2011
 * 
 */
public class TreeView extends JTree implements TreeSelectionListener {
    private static final long serialVersionUID = -3487864445665189571L;
    private JScrollPane contentScrollPane;
    private JPanel currentView;

    /**
     * Init the treeView with the contentScrollPane.
     * 
     * @param contentScrollPane
     *            the scrollPane contains the view of {@link TreeNodeWithView}.
     */
    public TreeView(TreeModel treeModel, JScrollPane contentScrollPane) {
        super(treeModel);
        this.contentScrollPane = contentScrollPane;
        addTreeSelectionListener(this);
        setCellRenderer(new TreeViewRenderer());
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

    private class TreeViewRenderer extends DefaultTreeCellRenderer {
        private static final long serialVersionUID = -3765500551785294524L;

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            NodeValue nodeValue = (NodeValue) node.getUserObject();
            if (nodeValue.getIcon() != null) {
                setIcon(nodeValue.getIcon());
            }
            return this;
        }
    }
}

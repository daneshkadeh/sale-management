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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.view.UIConstants;
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
    private TreeCellRenderer defaultRenderer;

    /**
     * Init the treeView with the contentScrollPane, by default the tree expands all node.
     * 
     * @param contentScrollPane
     *            the scrollPane contains the view of {@link TreeNodeWithView}.
     */
    public TreeView(TreeModel treeModel, JScrollPane contentScrollPane) {
        super(treeModel);
        this.contentScrollPane = contentScrollPane;
        addTreeSelectionListener(this);
        defaultRenderer = getCellRenderer();
        setCellRenderer(new TreeViewRenderer());
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        expandAll();
    }

    /**
     * Expand all node.
     */
    public void expandAll() {
        for (int i = 0; i < getRowCount(); i++) {
            expandRow(i);
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreeNodeWithView node = (TreeNodeWithView) getLastSelectedPathComponent();
        if (node == null) {
            return;
        }

        JPanel viewOfNode = node.getView();
        if (node.isLeaf() && viewOfNode == null) {
            viewOfNode = new JPanel();
            viewOfNode.setBackground(Color.WHITE);
            viewOfNode.add(new JLabel(ImageUtils.getIcon("/icons/Under-Construction.jpg")));
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

    private class TreeViewRenderer implements TreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                boolean leaf, int row, boolean hasFocus) {
            JLabel c = (JLabel) defaultRenderer.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
                    hasFocus);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            NodeValue nodeValue = (NodeValue) node.getUserObject();
            if (nodeValue.getIcon() != null) {
                c.setIcon(nodeValue.getIcon());
            }
            if (!leaf) {
                c.setFont(UIConstants.DEFAULT_BOLD_FONT);
                c.setForeground(Color.BLUE);
            } else {
                c.setFont(UIConstants.DEFAULT_FONT);
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    }
}

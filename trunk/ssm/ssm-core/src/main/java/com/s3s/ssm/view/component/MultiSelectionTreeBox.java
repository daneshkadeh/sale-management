/*
 * MultiSelectionTreeBox
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

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.StringUtils;

/**
 * @author Phan Hong Phuc
 * @since Feb 26, 2012
 */
public class MultiSelectionTreeBox extends AbstractMultiSelectionBox {
    private static final long serialVersionUID = 3125395234804094978L;
    private JTree tree;
    private JList<List<MultiSelectableTreeNode>> list;
    private MultiSelectableTreeNode rootNode;

    public MultiSelectionTreeBox(MultiSelectableTreeNode rootNode) {
        super();
        this.rootNode = rootNode;
        initComponents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JList<List<MultiSelectableTreeNode>> createDestinationView() {
        DefaultListModel<List<MultiSelectableTreeNode>> model = new DefaultListModel<>();
        list = new JList<>(model);
        refreshDataForList();
        list.setSelectionModel(new DefaultListSelectionModel());
        list.setCellRenderer(new ListCellRenderer<List<MultiSelectableTreeNode>>() {

            @Override
            public Component getListCellRendererComponent(JList<? extends List<MultiSelectableTreeNode>> list,
                    List<MultiSelectableTreeNode> value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = new JLabel();
                label.setText(StringUtils.join(value, '>'));
                return label;
            }

        });
        return list;
    }

    private void refreshDataForList() {
        DefaultListModel<List<MultiSelectableTreeNode>> model = (DefaultListModel<List<MultiSelectableTreeNode>>) list
                .getModel();
        model.removeAllElements();
        List<MultiSelectableTreeNode> leafNodes = rootNode.getAllSelectedLeafNodes(rootNode);
        for (MultiSelectableTreeNode node : leafNodes) {
            model.addElement(node.getPathToBeforeRoot());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JTree createSourceView() {
        tree = new JTree(new DefaultTreeModel(rootNode));
        tree.setRootVisible(false);
        return tree;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSelectAll() {
        rootNode.setState(true);
        refreshDataForList();
        ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(rootNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSelectSingle() {
        for (TreePath treePath : tree.getSelectionPaths()) {
            ((MultiSelectableTreeNode) treePath.getLastPathComponent()).setState(true);
        }
        refreshDataForList();
        ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(rootNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDeselectAll() {
        List<MultiSelectableTreeNode> selectedLeafNodes = rootNode.getAllSelectedLeafNodes(rootNode);
        for (MultiSelectableTreeNode node : selectedLeafNodes) {
            node.setState(false);
        }
        refreshDataForList();
        ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(rootNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDeselectSingle() {
        // TODO Auto-generated method stub

    }

}

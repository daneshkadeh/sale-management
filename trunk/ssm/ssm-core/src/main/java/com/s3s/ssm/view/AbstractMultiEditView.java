/*
 * EditView
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

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;

import org.springframework.util.Assert;

import com.s3s.ssm.entity.AbstractIdOLObject;

/**
 * The edit view with multi-view on the tree.
 * 
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractMultiEditView<T extends AbstractIdOLObject> extends AbstractEditView<T> {
    private static final long serialVersionUID = 5168377500300996678L;

    public AbstractMultiEditView(T entity) {
        this(entity, null, null);
    }

    public AbstractMultiEditView(T entity, Long parentId, Class<? extends AbstractIdOLObject> parentClass) {
        super(entity, parentId, parentClass);
        setLayout(new MigLayout("fill"));
        JScrollPane contentScrollPane = new JScrollPane();
        TreeView treeView = initTreeView(entity, contentScrollPane);
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JScrollPane(treeView));
        splitPane.setRightComponent(contentScrollPane);
        add(splitPane);
    }

    private TreeView initTreeView(T entity, JScrollPane contentScrollPane) {
        TreeView treeView = new TreeView(contentScrollPane);
        TreeNodeWithView root = new TreeNodeWithView("");
        treeView.setModel(new DefaultTreeModel(root));
        treeView.setRootVisible(false);
        constructTreeView(root, entity);
        Assert.isTrue(root.getChildAt(0) != null, "There is no node in the tree");
        // Set selection on the first node
        treeView.setSelectionPath(new TreePath(((TreeNodeWithView) root.getChildAt(0)).getPath()));
        return treeView;
    }

    /**
     * @param root
     * @return
     */
    protected abstract void constructTreeView(TreeNodeWithView root, T entity);
}

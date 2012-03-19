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

package com.s3s.ssm.view.edit;

import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;

import org.springframework.util.Assert;

import com.s3s.ssm.entity.AbstractIdOLObject;
import com.s3s.ssm.view.ISavedListener;
import com.s3s.ssm.view.SavedEvent;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.TreeView;

/**
 * The edit view with multi-view on the tree.
 * 
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractMultiEditView<T extends AbstractIdOLObject> extends AbstractEditView<T> {
    private static final long serialVersionUID = 5168377500300996678L;
    private TreeView treeView;
    private boolean isCreatedSubView = false;

    public AbstractMultiEditView() {
        this(null);
    }

    public AbstractMultiEditView(Map<String, Object> request) {
        super(request);
        setLayout(new MigLayout("hidemode 2, fill, ins 0", "", "grow"));
        JScrollPane contentScrollPane = new JScrollPane();
        TreeView treeView = initTreeView(entity, contentScrollPane, request);
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JScrollPane(treeView));
        splitPane.setRightComponent(contentScrollPane);
        add(splitPane, "grow, top");
    }

    private TreeView initTreeView(T entity, JScrollPane contentScrollPane, Map<String, Object> request) {
        TreeNodeWithView root = new TreeNodeWithView("");
        constructTreeView(root, entity, request);
        treeView = new TreeView(new DefaultTreeModel(root), contentScrollPane);
        treeView.setRootVisible(false);
        Assert.isTrue(root.getChildAt(0) != null, "There is no node in the tree");
        // Set selection on the first node
        treeView.setSelectionPath(new TreePath(((TreeNodeWithView) root.getChildAt(0)).getPath()));
        return treeView;
    }

    protected TreeView getTreeView() {
        return treeView;
    }

    /**
     * @param root
     * @return
     */
    protected void constructTreeView(final TreeNodeWithView root, T entity, final Map<String, Object> request) {
        // TODO Phuc: should we init the main view in the this class?
        AbstractSingleEditView<T> detailView = constructMainView(root, entity, request);
        if (entity.isPersisted()) {
            constructSubViews(root, entity, request);
            isCreatedSubView = true;
        }

        detailView.addSavedListener(new ISavedListener<T>() {
            @Override
            public void doSaved(SavedEvent<T> e) {
                if (!isCreatedSubView) {
                    constructSubViews(root, e.getEntity(), request);
                    ((DefaultTreeModel) getTreeView().getModel()).nodeStructureChanged(root);
                    isCreatedSubView = true;
                }
            }
        });
    }

    protected abstract AbstractSingleEditView<T> constructMainView(TreeNodeWithView root, T entity,
            Map<String, Object> request);

    protected abstract void constructSubViews(TreeNodeWithView root, T entity, Map<String, Object> request);
}

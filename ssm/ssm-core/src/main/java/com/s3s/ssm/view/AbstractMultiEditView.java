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

import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
public abstract class AbstractMultiEditView<T extends AbstractIdOLObject> extends AbstractEditView<T> implements
        TreeSelectionListener {
    private static final long serialVersionUID = 5168377500300996678L;
    private TreeView treeView;
    private boolean isCreatedSubView = false;

    public AbstractMultiEditView() {
        this(null);
    }

    public AbstractMultiEditView(Map<String, Object> request) {
        super(request);
        setLayout(new MigLayout("hidemode 2, fillx, ins 0", "", "[]0[grow]"));
        add(toolbar, "growx, wrap, top");
        JScrollPane contentScrollPane = new JScrollPane();
        TreeView treeView = initTreeView(entity, contentScrollPane, request);
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JScrollPane(treeView));
        splitPane.setRightComponent(contentScrollPane);
        add(splitPane, "grow, top");
    }

    private TreeView initTreeView(T entity, JScrollPane contentScrollPane, Map<String, Object> request) {
        treeView = new TreeView(contentScrollPane);
        TreeNodeWithView root = new TreeNodeWithView("");
        treeView.setModel(new DefaultTreeModel(root));
        treeView.setRootVisible(false);
        constructTreeView(root, entity, request);
        Assert.isTrue(root.getChildAt(0) != null, "There is no node in the tree");
        // Set selection on the first node
        treeView.setSelectionPath(new TreePath(((TreeNodeWithView) root.getChildAt(0)).getPath()));
        treeView.addTreeSelectionListener(this);
        return treeView;
    }

    @Override
    protected void doClose() {
        ((AbstractEditView) treeView.getCurrentView()).doClose();
    }

    @Override
    protected void doNew() {
        ((AbstractEditView) treeView.getCurrentView()).doNew();
    }

    @Override
    protected boolean doSave() {
        return ((AbstractEditView) treeView.getCurrentView()).doSave();
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreeNodeWithView node = (TreeNodeWithView) treeView.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }

        JPanel viewOfNode = node.getView();
        // setVisibleToolbar(viewOfNode instanceof AbstractEditView);
    }

    protected TreeView getTreeView() {
        return treeView;
    }

    /**
     * @param root
     * @return
     */
    protected void constructTreeView(final TreeNodeWithView root, final T entity, final Map<String, Object> request) {
        AbstractSingleEditView<T> detailView = constructMainView(root, entity, request);
        if (entity.isPersisted()) {
            constructSubViews(root, entity, request);
        }

        detailView.addSavedListener(new ISavedListener<AbstractIdOLObject>() {
            @Override
            public void doSaved(SavedEvent<AbstractIdOLObject> e) {
                if (!isCreatedSubView) {
                    constructSubViews(root, entity, request);
                    ((DefaultTreeModel) getTreeView().getModel()).nodeStructureChanged(root);
                }
            }
        });
    }

    protected abstract AbstractSingleEditView<T> constructMainView(TreeNodeWithView root, T entity,
            Map<String, Object> request);

    protected void constructSubViews(TreeNodeWithView root, T entity, Map<String, Object> request) {
        isCreatedSubView = true;
    }
}

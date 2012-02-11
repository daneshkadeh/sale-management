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

import com.s3s.ssm.entity.AbstractBaseIdObject;

/**
 * @author Phan Hong Phuc
 * 
 */
public abstract class AbstractMultiEditView<T extends AbstractBaseIdObject> extends AbstractEditView<T> {
    private static final long serialVersionUID = 5168377500300996678L;

    public AbstractMultiEditView(T entity) {
        super(entity);
        JScrollPane contentScrollPane = new JScrollPane();
        TreeView treeView = new TreeView(contentScrollPane);
        constructTreeView(treeView, entity);
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(new JScrollPane(treeView));
        splitPane.setRightComponent(contentScrollPane);
        add(splitPane);
    }

    /**
     * @param treeView
     * @return
     */
    protected abstract TreeView constructTreeView(TreeView treeView, T entity);
}

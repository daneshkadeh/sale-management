/*
 * ManufacturerDomain
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

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.view.list.param.ListManufacturerView;

/**
 * @author Phan Hong Phuc
 * @since Nov 20, 2011
 * 
 */
public class ManufacturerDomain extends AbstractDomain {
    private static final long serialVersionUID = 2688633156370098361L;

    public ManufacturerDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText("Manufacturer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void constructTreeView(TreeView treeView) {
        TreeNodeWithView root = new TreeNodeWithView("Manufacturers", new ListManufacturerView());
        treeView.setModel(new DefaultTreeModel(root));
    }

}

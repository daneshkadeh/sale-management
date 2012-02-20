/*
 * EditMultiSupplierViewTest
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

package com.s3s.ssm.view.detail.contact;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.view.AbstractMultiEditView;
import com.s3s.ssm.view.TreeNodeWithView;

/**
 * @author Phan Hong Phuc
 * 
 */
public class EditMultiSupplierViewTest extends AbstractMultiEditView<Supplier> {

    private static final long serialVersionUID = -427718510574119477L;

    public EditMultiSupplierViewTest(Supplier entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void constructTreeView(TreeNodeWithView root, Supplier entity) {
        EditSupplierView detailView = new EditSupplierView(entity);
        detailView.setListView(listView);
        TreeNodeWithView node = new TreeNodeWithView("Supplier", detailView);
        root.add(node);
    }

}

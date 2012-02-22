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

import java.util.Map;

import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.view.AbstractMultiEditView;
import com.s3s.ssm.view.AbstractSingleEditView;
import com.s3s.ssm.view.TreeNodeWithView;

/**
 * @author Phan Hong Phuc
 * 
 */
public class EditMultiSupplierViewTest extends AbstractMultiEditView<Supplier> {

    private static final long serialVersionUID = -427718510574119477L;

    public EditMultiSupplierViewTest(Map<String, Object> entity) {
        super(entity);
    }

    @Override
    protected AbstractSingleEditView<Supplier> constructMainView(TreeNodeWithView root, Supplier entity,
            Map<String, Object> request) {
        EditSupplierView detailView = new EditSupplierView(request);
        detailView.setVisibleToolbar(false);
        TreeNodeWithView node = new TreeNodeWithView("Supplier", detailView);
        root.add(node);
        return detailView;
    }

    @Override
    protected void constructSubViews(TreeNodeWithView root, Supplier entity, Map<String, Object> request) {
        super.constructSubViews(root, entity, request);
        // TODO: add subviews later
    }

}

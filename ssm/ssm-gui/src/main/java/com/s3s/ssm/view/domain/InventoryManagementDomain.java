/*
 * InventoryManagementDomain
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
package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.list.param.ListStoreView;
import com.s3s.ssm.view.list.store.ListCheckStoreView;
import com.s3s.ssm.view.list.store.ListExchangeStoreFormView;
import com.s3s.ssm.view.list.store.ListExportStoreFormView;
import com.s3s.ssm.view.list.store.ListImportProductFormView;

/**
 * All view relates to store, inventory, import/export
 * 
 * @author phamcongbang
 * 
 */
public class InventoryManagementDomain extends AbstractDomain {

    public InventoryManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Store.InventoryManagement"));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView tonKhoNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.Inventory"),
                new ListStoreView()); // "Ton kho"

        TreeNodeWithView chuyenKhoNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.ExchangeStore"), new ListExchangeStoreFormView()); // "chuyen kho"

        TreeNodeWithView phieuXuatKhoNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.ExportStoreForm"), new ListExportStoreFormView()); // "phieu xuat kho"

        TreeNodeWithView phieuNhapKhoNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.ImportStoreForm"), new ListImportProductFormView()); // "Phieu nhap kho"

        TreeNodeWithView phieuKiemKhoNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.CheckStoreForm"), new ListCheckStoreView()); // "Phieu nhap kho"

        rootNode.add(tonKhoNode);
        rootNode.add(chuyenKhoNode);
        rootNode.add(phieuXuatKhoNode);
        rootNode.add(phieuNhapKhoNode);
        rootNode.add(phieuKiemKhoNode);
    }

}

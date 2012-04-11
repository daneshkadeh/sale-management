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
import com.s3s.ssm.view.list.shipment.ListTransportationTypeView;
import com.s3s.ssm.view.list.store.ListExportStoreFormView;
import com.s3s.ssm.view.list.store.ListImportStoreFormView;
import com.s3s.ssm.view.list.store.ListShipPriceTypeView;
import com.s3s.ssm.view.list.store.ListShipPriceView;
import com.s3s.ssm.view.list.store.ListStoreView;

/**
 * All view relates to store, inventory, import/export
 * 
 * @author phamcongbang
 * 
 */
public class StoreManagementDomain extends AbstractDomain {

    public StoreManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Store.InventoryManagement"));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView storeNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.Store"),
                new ListStoreView());
        TreeNodeWithView shipPriceTypeNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.ShipPriceType"), new ListShipPriceTypeView());
        TreeNodeWithView shipPriceNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.ShipPrice"),
                new ListShipPriceView());
        TreeNodeWithView transportNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.TransportType"), new ListTransportationTypeView());
        TreeNodeWithView importNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.ImportStore"),
                new ListImportStoreFormView());

        // TreeNodeWithView chuyenKhoNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Store.ExchangeStore"), new ListExchangeStoreFormView()); // "chuyen kho"
        //
        TreeNodeWithView exportNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.ExportStore"),
                new ListExportStoreFormView(null, ControlConfigUtils.getString("label.ExportStoreForm.list.title"),
                        null)); //
        // "phieu xuat kho"
        //
        // TreeNodeWithView phieuNhapKhoNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Store.ImportStoreForm"), new ListImportStoreView()); // "Phieu nhap kho"
        //
        // TreeNodeWithView phieuKiemKhoNode = new TreeNodeWithView(
        // ControlConfigUtils.getString("JTree.Store.CheckStoreForm"), new ListCheckStoreView()); // "Phieu nhap kho"

        rootNode.add(storeNode);
        rootNode.add(shipPriceTypeNode);
        rootNode.add(shipPriceNode);
        rootNode.add(transportNode);
        rootNode.add(importNode);
        rootNode.add(exportNode);
        // rootNode.add(chuyenKhoNode);
        // rootNode.add(phieuXuatKhoNode);
        // rootNode.add(phieuNhapKhoNode);
        // rootNode.add(phieuKiemKhoNode);
    }

}

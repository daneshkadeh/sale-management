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

import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;
import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.TreeNodeWithView;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.list.shipment.ListTransportationTypeView;
import com.s3s.ssm.view.list.store.ListExportStoreFormView;
import com.s3s.ssm.view.list.store.ListImportStoreFormView;
import com.s3s.ssm.view.list.store.ListMoveStoreFormView;
import com.s3s.ssm.view.list.store.ListMoveStoreOrderView;
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
        setIcon(ImageUtils.getMediumIcon(ImageConstants.WAREHOUSE_ICON));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        TreeNodeWithView storeNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.Store"),
                new ListStoreView(null, ControlConfigUtils.getString("label.Store.list.title"), null));
        TreeNodeWithView shipPriceTypeNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.ShipPriceType"), new ListShipPriceTypeView(null,
                        ControlConfigUtils.getString("label.ShipPriceType.list.title"), null));
        TreeNodeWithView shipPriceNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.ShipPrice"),
                new ListShipPriceView(null, ControlConfigUtils.getString("label.ImportStoreForm.list.title"), null));
        TreeNodeWithView transportNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.TransportType"), new ListTransportationTypeView(null,
                        ControlConfigUtils.getString("label.TransportationType.list.title"), null));
        TreeNodeWithView importNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.ImportStore"),
                new ListImportStoreFormView(null, ControlConfigUtils.getString("label.ImportStoreForm.list.title"),
                        null));
        TreeNodeWithView exportNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.ExportStore"),
                new ListExportStoreFormView(null, ControlConfigUtils.getString("label.ExportStoreForm.list.title"),
                        null));
        TreeNodeWithView moveNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Store.MoveStore"),
                new ListMoveStoreFormView(null, ControlConfigUtils.getString("label.MoveStoreForm.list.title"), null));
        TreeNodeWithView moveOrderNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.MoveStoreOrder"), new ListMoveStoreOrderView(null,
                        ControlConfigUtils.getString("label.MoveStoreOrder.list.title"), null));
        rootNode.add(storeNode);
        rootNode.add(shipPriceTypeNode);
        rootNode.add(shipPriceNode);
        rootNode.add(transportNode);
        rootNode.add(importNode);
        rootNode.add(exportNode);
        rootNode.add(moveNode);
        rootNode.add(moveOrderNode);
    }
}

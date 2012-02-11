/*
 * SupplyChainDomain
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

/**
 * Supply chain domain transfer material to products.
 * 
 * @author phamcongbang
 * 
 */
public class SupplyChainDomain extends AbstractDomain {

    public SupplyChainDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.SupplyChain.Management"));
    }

    @Override
    protected void constructTreeView(TreeNodeWithView rootNode) {
        // TODO: Supply chain management
        TreeNodeWithView materialNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.SupplyChain.Material")); // "Material"
        TreeNodeWithView materialPriceNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.MaterialPrice")); // "Material price"
        TreeNodeWithView materialEndProductExchangeNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.MaterialProductExchange")); // "Material product exchange"
        TreeNodeWithView endProductNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.EndProduct")); // "End-product"
        TreeNodeWithView endProductPriceNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.EndProductPrice")); // "End-product price"

        rootNode.add(materialNode);
        rootNode.add(materialPriceNode);
        rootNode.add(materialEndProductExchangeNode);
        rootNode.add(endProductNode);
        rootNode.add(endProductPriceNode);
    }

}

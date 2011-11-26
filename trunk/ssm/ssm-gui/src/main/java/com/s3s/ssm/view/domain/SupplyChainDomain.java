package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;

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
    protected void constructTreeView(TreeView treeView) {
        // TODO: Supply chain management
        TreeNodeWithView supplyChainMangamentEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.Management")); // "Supply chain management"
        TreeNodeWithView materialNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.SupplyChain.Material")); // "Material"
        TreeNodeWithView materialPriceNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.MaterialPrice")); // "Material price"
        TreeNodeWithView materialEndProductExchangeNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.MaterialProductExchange")); // "Material product exchange"
        TreeNodeWithView endProductNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.EndProduct")); // "End-product"
        TreeNodeWithView endProductPriceNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SupplyChain.EndProductPrice")); // "End-product price"

        supplyChainMangamentEntry.add(materialNode);
        supplyChainMangamentEntry.add(materialPriceNode);
        supplyChainMangamentEntry.add(materialEndProductExchangeNode);
        supplyChainMangamentEntry.add(endProductNode);
        supplyChainMangamentEntry.add(endProductPriceNode);

        treeView.setModel(new DefaultTreeModel(supplyChainMangamentEntry));
    }

}

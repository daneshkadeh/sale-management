package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;
import com.s3s.ssm.view.list.sales.ListSalesContractView;

/**
 * Buy products activity of the institution
 * 
 * @author phamcongbang
 * 
 */
public class BuyManagementDomain extends AbstractDomain {

    public BuyManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.SalesContract"));
    }

    @Override
    protected void constructTreeView(TreeView treeView) {
        // Sales contract
        TreeNodeWithView buyContractManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract"));

        TreeNodeWithView buyContractNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.buyContract"), new ListSalesContractView()); // "Hop dong mua hang"

        // TODO:
        TreeNodeWithView ttManageNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.TTmanagement"));
        TreeNodeWithView lcManageNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.LCmanagement"));
        TreeNodeWithView declareContractNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.SalesContract.importNote")); // "Quan ly to khai"
        buyContractManagementEntry.add(buyContractNode);
        buyContractManagementEntry.add(ttManageNode);
        buyContractManagementEntry.add(lcManageNode);
        buyContractManagementEntry.add(declareContractNode);

        treeView.setModel(new DefaultTreeModel(buyContractManagementEntry));

    }

}

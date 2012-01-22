package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;
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
    protected void constructTreeView(TreeView treeView) {
        TreeNodeWithView inventoryManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Store.InventoryManagement")); // "Quan ly kho"
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

        inventoryManagementEntry.add(tonKhoNode);
        inventoryManagementEntry.add(chuyenKhoNode);
        inventoryManagementEntry.add(phieuXuatKhoNode);
        inventoryManagementEntry.add(phieuNhapKhoNode);
        inventoryManagementEntry.add(phieuKiemKhoNode);

        treeView.setModel(new DefaultTreeModel(inventoryManagementEntry));

    }

}

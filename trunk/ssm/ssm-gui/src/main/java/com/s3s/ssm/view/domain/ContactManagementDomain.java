package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;
import com.s3s.ssm.view.list.contact.ListContactTypeView;
import com.s3s.ssm.view.list.contact.ListContactView;

/**
 * All views of contact.
 * 
 * @author phamcongbang
 * 
 */
public class ContactManagementDomain extends AbstractDomain {

    public ContactManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Contact.Management"));
    }

    @Override
    protected void constructTreeView(TreeView treeView) {
        // CRM Contact management
        TreeNodeWithView contactMagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.Management")); // "CRM contact management"
        TreeNodeWithView customerGroupNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.ContactGroup"), new ListContactTypeView()); // "Customer group"
        TreeNodeWithView customerNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Contact.Customer"),
                new ListContactView()); // "Customer"

        TreeNodeWithView supplierNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.SupplierContact")); // "Supplier"
        TreeNodeWithView nguoiDuocTaiTroNode = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Contact.SupportedContact")); // "Nguoi duoc tai tro"

        contactMagementEntry.add(customerGroupNode);
        contactMagementEntry.add(customerNode);
        contactMagementEntry.add(supplierNode);
        contactMagementEntry.add(nguoiDuocTaiTroNode);
        treeView.setModel(new DefaultTreeModel(contactMagementEntry));

    }

}

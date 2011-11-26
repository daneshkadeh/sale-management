package com.s3s.ssm.view.domain;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.util.i18n.ControlConfigUtils;
import com.s3s.ssm.view.component.AbstractDomain;
import com.s3s.ssm.view.component.TreeNodeWithView;
import com.s3s.ssm.view.component.TreeView;

/**
 * Management of human resource, devices.
 * 
 * @author phamcongbang
 * 
 */
public class ResourceManagementDomain extends AbstractDomain {

    public ResourceManagementDomain(JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(treeScrollPane, contentScrollPane);
        setText(ControlConfigUtils.getString("JTree.Resource.Management"));
    }

    @Override
    protected void constructTreeView(TreeView treeView) {
        // HRM Resource management
        TreeNodeWithView resourceManagementEntry = new TreeNodeWithView(
                ControlConfigUtils.getString("JTree.Resource.Management")); // "HRM Resource management"
        TreeNodeWithView employeeNode = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Resource.Employee")); // "Employee"
        TreeNodeWithView devices = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Resource.Device")); // "Device"
        TreeNodeWithView material = new TreeNodeWithView(ControlConfigUtils.getString("JTree.Resource.Material")); // "Material"
        resourceManagementEntry.add(employeeNode);
        resourceManagementEntry.add(devices);
        resourceManagementEntry.add(material);

        treeView.setModel(new DefaultTreeModel(resourceManagementEntry));

    }

}

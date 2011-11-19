/*
 * UserDomainToggleButton
 * 
 * Project: SSM
 * 
 * Copyright 2010 by S3SSoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of S3SSoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with S3SSoft.
 */

package com.s3s.ssm.view.component;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;

import com.s3s.ssm.view.list.contact.ListContactView;

/**
 * @author Phan Hong Phuc
 * @since Nov 20, 2011
 * 
 */
public class UserDomainToggleButton extends DomainToggleButton {

    /**
     * @param label
     * @param icon
     * @param treeScrollPane
     * @param viewScrollPane
     */
    public UserDomainToggleButton(String label, Icon icon, JScrollPane treeScrollPane, JScrollPane viewScrollPane) {
        super(label, icon, treeScrollPane, viewScrollPane);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void constructTreeView(TreeView treeView) {
        TreeNodeWithView root = new TreeNodeWithView("User Management");
        TreeNodeWithView user = new TreeNodeWithView("Contact", new ListContactView());
        root.add(user);
        treeView.setModel(new DefaultTreeModel(root));
    }
}

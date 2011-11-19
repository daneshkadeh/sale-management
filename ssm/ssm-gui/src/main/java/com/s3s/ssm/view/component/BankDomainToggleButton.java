/*
 * BankDomainToggleButton
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

import com.s3s.ssm.view.list.contact.ListBankView;

/**
 * @author Phan Hong Phuc
 * @since Nov 20, 2011
 * 
 */
public class BankDomainToggleButton extends DomainToggleButton {

    public BankDomainToggleButton(String label, Icon icon, JScrollPane treeScrollPane, JScrollPane contentScrollPane) {
        super(label, icon, treeScrollPane, contentScrollPane);
        // TODO Auto-generated constructor stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void constructTreeView(TreeView treeView) {
        TreeNodeWithView root = new TreeNodeWithView("Bank", new ListBankView());
        treeView.setModel(new DefaultTreeModel(root));

    }

}

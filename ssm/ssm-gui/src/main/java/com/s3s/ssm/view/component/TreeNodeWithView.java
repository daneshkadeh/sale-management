/*
 * S3sMultableTreeNodeWithView
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

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * The node of {@link JTree} component with the view which displayed on the right of the main sreen.
 * 
 * @author Phan Hong Phuc
 * @since Nov 19, 2011
 * 
 */
public class TreeNodeWithView extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 5556403806362671908L;
    private JPanel view;

    /**
     * Init the node with a label and a view. </br> <b>Note:</b> the view needs to implement {@link IViewLazyLoadable}
     * if it is quite large to load.
     * 
     * @param label
     *            label of the node.
     * @param view
     *            the view of the node.
     */
    public TreeNodeWithView(String label, JPanel view) {
        super(label);
        this.setView(view);
    }

    /**
     * Init the node with the label and no view.
     * 
     * @param label
     */
    public TreeNodeWithView(String label) {
        this(label, null);
    }

    public JPanel getView() {
        return view;
    }

    public void setView(JPanel view) {
        this.view = view;
    }
}

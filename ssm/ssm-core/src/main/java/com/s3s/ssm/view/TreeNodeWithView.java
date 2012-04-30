/*
 * TreeNodeWithView
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

package com.s3s.ssm.view;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.apache.commons.lang.StringUtils;

/**
 * The node of {@link JTree} component with the view which displayed on the right of the main screen.
 * 
 * @author Phan Hong Phuc
 * @since Nov 19, 2011
 * 
 */
public class TreeNodeWithView extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 5556403806362671908L;
    private NodeValue nodeValue;

    /**
     * Init the node with a label and a view. </br> <b>Note:</b> the view needs to implement {@link IViewLazyLoadable}
     * if it is quite large to load. </br> By default, the icon is still not set for the node. Need to write the
     * renderer for the tree to display this icon. See {@link DefaultTreeCellRenderer}.
     * 
     * @param label
     *            label of the node.
     * @param view
     *            the view of the node.
     * @param icon
     *            the icon of the node.
     */
    public TreeNodeWithView(String label, JPanel view, Icon icon) {
        super();
        nodeValue = new NodeValue(label, icon, view);
        setUserObject(nodeValue);
    }

    public TreeNodeWithView(String label, JPanel view) {
        this(label, view, null);
    }

    public TreeNodeWithView(String label, Icon icon) {
        this(label, null, icon);
    }

    /**
     * Init the node with the label and no view.
     * 
     * @param label
     */
    public TreeNodeWithView(String label) {
        this(label, null, null);
    }

    public JPanel getView() {
        return nodeValue.getView();
    }

    public void setView(JPanel view) {
        nodeValue.setView(view);
    }

    protected Icon getIcon() {
        return nodeValue.getIcon();
    }

    protected void setIcon(Icon icon) {
        nodeValue.setIcon(icon);
    }

    /**
     * The user object for tree node.
     * 
     * @author Phan Hong Phuc
     * @since Feb 27, 2012
     */
    public class NodeValue {
        private String label;
        private Icon icon;
        private JPanel view;

        public NodeValue(String label, Icon icon, JPanel view) {
            super();
            this.label = label;
            this.icon = icon;
            this.view = view;
        }

        protected String getLabel() {
            return label;
        }

        protected void setLabel(String label) {
            this.label = label;
        }

        protected Icon getIcon() {
            return icon;
        }

        protected void setIcon(Icon icon) {
            this.icon = icon;
        }

        protected JPanel getView() {
            return view;
        }

        protected void setView(JPanel view) {
            this.view = view;
        }

        /**
         * Display the label on the node of the tree.
         */
        @Override
        public String toString() {
            return StringUtils.trimToEmpty(label);
        }
    }
}

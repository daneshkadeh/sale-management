/*
 * SearchView
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

import javax.swing.JPanel;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;

/**
 * The panel contains the view of search function.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class SearchView extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable table;

    public SearchView(JTable table) {
        super();
        this.table = table;
        initComponent();
    }

    private void initComponent() {
        setLayout(new MigLayout("wrap 3"));

    }

}

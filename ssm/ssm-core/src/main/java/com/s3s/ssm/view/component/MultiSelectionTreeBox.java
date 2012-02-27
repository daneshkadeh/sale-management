/*
 * MultiSelectionTreeBox
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

package com.s3s.ssm.view.component;

import javax.swing.JList;
import javax.swing.JTree;

/**
 * @author Phan Hong Phuc
 * @since Feb 26, 2012
 */
public class MultiSelectionTreeBox extends AbstractMultiSelectionBox {
    private static final long serialVersionUID = 3125395234804094978L;

    public MultiSelectionTreeBox() {
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected JList<?> createDestinationView() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected JTree createSourceView() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSelectAll() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSelectSingle() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDeselectAll() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doDeselectSingle() {
        // TODO Auto-generated method stub

    }

}

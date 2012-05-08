/*
 * MarkEntireTextOnFocusListener
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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 * @author Phan Hong Phuc
 * @since May 8, 2012
 */
public class MarkEntireTextOnFocusListener implements FocusListener {

    private JTextComponent textComponent;

    public MarkEntireTextOnFocusListener(JTextComponent textComponent) {
        super();
        this.textComponent = textComponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void focusGained(FocusEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                markEntireText(textComponent);
            }
        });

    }

    public void markEntireText(JTextComponent textComponent) {
        textComponent.setCaretPosition(textComponent.getText().length());
        textComponent.moveCaretPosition(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void focusLost(FocusEvent e) {
    }

}

/*
 * SSplashScreen
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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.util.ImageUtils;

/**
 * @author Phan Hong Phuc
 * @since May 4, 2012
 */
public class SSplashScreen extends JWindow {
    private static final long serialVersionUID = 9219296900804342289L;
    private JProgressBar progressBar;

    public SSplashScreen() {
        super();
        JPanel panel = new JPanel(new MigLayout("ins 0, fill", "fill, grow"));
        JLabel versionLabel = new JLabel("Version 1.3.4.0 - Realease Date: 05/05/2012");
        JLabel image = new JLabel(ImageUtils.getIcon("/images/RetailBusinessActive.jpg"));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        panel.add(image, "grow, wrap");
        panel.add(progressBar, "grow, wrap");
        panel.add(versionLabel);
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        add(panel);
        pack();
    }

    /**
     * Set progress value
     */
    public void setValue(int value, String string) {
        progressBar.setValue(value);
        progressBar.setString(string);
    }

}

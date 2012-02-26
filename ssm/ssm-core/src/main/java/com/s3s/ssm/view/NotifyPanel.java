/*
 * NotifyPanel
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

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.commons.lang.StringUtils;

import com.s3s.ssm.util.ImageConstants;
import com.s3s.ssm.util.ImageUtils;

/**
 * The panel to show the notify message.
 * 
 * @author Phan Hong Phuc
 * 
 */
public class NotifyPanel extends JPanel {
    private static final long serialVersionUID = -7566700380195941863L;

    public enum NotifyKind {
        INFORMATION, WARNING, ERROR
    }

    private String message = StringUtils.EMPTY;
    private JLabel label;

    /**
     * Init the notify panel with the information type by default.
     */
    public NotifyPanel() {
        FlowLayout flowLayout = (FlowLayout) getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        label = new JLabel();
        label.setIcon(ImageUtils.getIcon(ImageConstants.INFO_ICON));
        label.setForeground(Color.GREEN);
        setBorder(new LineBorder(Color.GREEN, 1, true));
        add(label);
    }

    /**
     * Added the message in new line.
     * 
     * @param message
     */
    public void addMessage(String message) {
        this.message += "</br>" + message + "</br>";
        label.setText("<html>" + this.message + "</html>");
    }

    public void setMessage(String message) {
        this.message = message;
        label.setText(message);
    }

    public void clearMessage() {
        message = StringUtils.EMPTY;
        label.setText(message);
    }

    public String getMessage() {
        return message;
    }

    public void setNotifyKind(NotifyKind kind) {
        if (kind == NotifyKind.INFORMATION) {
            label.setIcon(ImageUtils.getIcon(ImageConstants.INFO_ICON));
            label.setForeground(Color.GREEN);
            setBorder(new LineBorder(Color.GREEN, 1, true));
        } else if (kind == NotifyKind.WARNING) {
            // TODO Phuc: Change info icon to warning icon
            label.setIcon(ImageUtils.getIcon(ImageConstants.INFO_ICON));
            label.setForeground(Color.YELLOW);
            setBorder(new LineBorder(Color.YELLOW, 1, true));
        } else if (kind == NotifyKind.ERROR) {
            label.setIcon(ImageUtils.getIcon(ImageConstants.ERROR_ICON));
            label.setForeground(Color.RED);
            setBorder(new LineBorder(Color.RED, 1, true));
        }
    }
}

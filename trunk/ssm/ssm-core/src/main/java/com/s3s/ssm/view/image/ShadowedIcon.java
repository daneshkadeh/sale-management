/*
 * ShadowedIcon
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.image;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Code taken from http://www.jroller.com/santhosh/entry/beautify_swing_applications_toolbar_with
 * 
 * @author Santhosh Kumar
 */
public class ShadowedIcon {
    private int shadowWidth = 2;

    private int shadowHeight = 2;

    private Icon icon, shadow;

    public ShadowedIcon(Icon icon) {
        this.icon = icon;
        shadow = new ImageIcon(GrayFilter.createDisabledImage(((ImageIcon) icon).getImage()));
    }

    public ShadowedIcon(Icon icon, int shadowWidth, int shadowHeight) {
        this(icon);
        this.shadowWidth = shadowWidth;
        this.shadowHeight = shadowHeight;
    }

    public int getIconHeight() {
        return icon.getIconWidth() + shadowWidth;
    }

    public int getIconWidth() {
        return icon.getIconHeight() + shadowHeight;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        shadow.paintIcon(c, g, x + shadowWidth, y + shadowHeight);
        icon.paintIcon(c, g, x, y);
    }
}

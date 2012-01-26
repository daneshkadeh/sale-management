/*
 * InsetsIcon
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
import java.awt.Insets;

import javax.swing.Icon;

/**
 * Code taken from http://www.jroller.com/santhosh/entry/beautify_swing_applications_toolbar_with
 * 
 * @author Santhosh Kumar
 */
public class InsetsIcon {
    private static final Insets DEFAULT_INSETS = new Insets(2, 2, 0, 0);

    private Icon icon;

    private Insets insets;

    public InsetsIcon(Icon icon) {
        this(icon, null);
    }

    public InsetsIcon(Icon icon, Insets insets) {
        this.icon = icon;
        this.insets = insets == null ? DEFAULT_INSETS : insets;
    }

    public int getIconHeight() {
        return icon.getIconHeight() + insets.top + insets.bottom;
    }

    public int getIconWidth() {
        return icon.getIconWidth() + insets.left + insets.right;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        icon.paintIcon(c, g, x + insets.left, y + insets.top);
    }
}

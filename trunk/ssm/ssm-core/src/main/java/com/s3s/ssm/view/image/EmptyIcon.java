/*
 * EmptyIcon
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

import javax.swing.Icon;

/**
 * A empty icon is a blank icon useful for ensuring alignment in menus between menuitems that have and do not have
 * icons.
 * 
 * @author Le Thanh Hoang
 * 
 */
public class EmptyIcon implements Icon {
    /**
     * Convenience object for small icons, whose size matches the size of small icons in Sun's graphics repository.
     */
    public static final EmptyIcon SMALL = new EmptyIcon(IconSize.SMALL);

    /**
     * Convenience object for large icons, whose size matches the size of large icons in Sun's graphics repository.
     */
    public static final EmptyIcon LARGE = new EmptyIcon(IconSize.LARGE);

    private IconSize size;

    /**
     * EmptyIcon objects are always square, having identical height and width.
     * 
     * @param size
     *            The size of the empty icon. Icons are always equal on all sides.
     */
    public EmptyIcon(IconSize size) {
        this.size = size;
    }

    public int getIconWidth() {
        return this.size.getValue();
    }

    public int getIconHeight() {
        return this.size.getValue();
    }

    /**
     * This implementation is empty, and paints nothing.
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // empty
    }
}

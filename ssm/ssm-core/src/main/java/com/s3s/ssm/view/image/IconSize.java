/*
 * IconSize
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

/**
 * Enum for various supported icon sizes.
 * 
 * @author Keith Donald
 */
public class IconSize {
    private String name;

    private int pixels;

    /**
     * The standard 16 pixel "small" icon.
     */
    public static final IconSize SMALL = new IconSize("small", 16);

    /**
     * The standard 24 pixel "large" icon.
     */
    public static final IconSize LARGE = new IconSize("large", 24);

    private IconSize(String name, int value) {
        this.name = name;
        this.pixels = value;
    }

    /**
     * Returns the icon size name.
     * 
     * @return The logical name of the icon size.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the size value in pixels.
     * 
     * @return The value in pixels.
     */
    public int getValue() {
        return pixels;
    }

    public String toString() {
        return "[IconSize name = '" + getName() + "', value = " + getValue() + "]";
    }
}

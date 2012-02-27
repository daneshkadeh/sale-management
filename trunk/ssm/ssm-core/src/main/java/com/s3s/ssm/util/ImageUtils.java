/*
 * ImageUtils
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

package com.s3s.ssm.util;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author Phan Hong Phuc
 * 
 */
public class ImageUtils {
    private static final int SMALL_SIZE = 16;

    private ImageUtils() {
    }

    /**
     * Get icon with original size.
     * 
     * @param imagePath
     * @return
     */
    public static ImageIcon getIcon(String imagePath) {
        return new ImageIcon(ImageUtils.class.getResource(imagePath));
    }

    /**
     * Get icon scaled to ({@link #SMALL_SIZE} x {@link #SMALL_SIZE}).
     * 
     * @param imagePath
     * @return
     */
    public static ImageIcon getSmallIcon(String imagePath) {
        Image image = new ImageIcon(ImageUtils.class.getResource(imagePath)).getImage();
        return new ImageIcon(image.getScaledInstance(SMALL_SIZE, SMALL_SIZE, Image.SCALE_SMOOTH));
    }

}

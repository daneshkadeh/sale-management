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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

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

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * 
     * @param srcImg
     *            - source image to scale
     * @param w
     *            - desired width
     * @param h
     *            - desired height
     * @return - the new resized image
     */
    public static final Image getScaledImage(Image srcImg, int w, int h) {
        // TODO rewrite the code and move it to ImageUtils
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}

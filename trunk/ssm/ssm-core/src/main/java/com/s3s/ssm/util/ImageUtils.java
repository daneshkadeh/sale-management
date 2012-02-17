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

import javax.swing.ImageIcon;

/**
 * @author Phan Hong Phuc
 * 
 */
public class ImageUtils {
    private ImageUtils() {
    }

    public static ImageIcon getImageIcon(String imagePath) {
        return new ImageIcon(ImageUtils.class.getResource(imagePath));
    }
    
}

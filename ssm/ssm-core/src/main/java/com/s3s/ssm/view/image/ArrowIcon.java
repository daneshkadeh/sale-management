/*
 * ArrowIcon
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

package com.s3s.ssm.view.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.SystemColor;

import javax.swing.Icon;

/**
 * A directional arrow icon; the direction can either be UP or DOWN.
 * 
 * @author Le Thanh Hoang
 * 
 */
public class ArrowIcon implements Icon {
    /**
     * Type-safe enum for the ArrowIcon's directional capability.
     * <p>
     * Currently only up and down are supported since this is primarily designed to be a table column sort indicator.
     * 
     */
    public static class Direction {
        public static final Direction DOWN = new Direction(0);

        public static final Direction UP = new Direction(1);

        private int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            return String.valueOf(value);
        }
    }

    private Direction direction;

    private int size;

    private Color color;

    /**
     * Creates a ArrowIcon in the specified direction with the default size and color.
     * 
     * @param direction
     *            The icon direction.
     */
    public ArrowIcon(Direction direction) {
        setDirection(direction);
        setSize(4);
        setColor(SystemColor.controlDkShadow);
    }

    /**
     * Creates a ArrowIcon pointing in the specified direction with the specified size and color.
     * 
     * @param direction
     *            the direction the arrow should point.
     * @param size
     *            the size of the arrow in pixels (4 is a good one)
     * @param color
     *            the color of the arrow (consider using UIDefaults for current L&F)
     */
    public ArrowIcon(Direction direction, int size, Color color) {
        setDirection(direction);
        setSize(size);
        setColor(color);
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        for (int i = 0; i < size; i++) {
            if (direction == Direction.UP) {
                g.drawLine(x + (size - (i + 1)), y + i, x + (size + i), y + i);
            } else {
                g.drawLine(x + i, y + i, x + (size * 2 - (i + 1)), y + i);
            }
        }
    }

    public int getIconWidth() {
        return size * 2;
    }

    public int getIconHeight() {
        return size;
    }

    /**
     * @param size
     */
    private void setSize(int size) {
        this.size = size;
    }

    /**
     * @param color
     */
    private void setColor(Color color) {

        this.color = color;
    }

    /**
     * @param direction
     */
    private void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("[ArrowIcon").append(" direction=").append(direction).append(" size=")
                .append(size).append(" color=").append(color);
        return buf.toString();
    }
}

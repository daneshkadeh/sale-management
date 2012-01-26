/*
 * UIConstants
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

package com.s3s.ssm.util.view;

/**
 * Constants for swing user interface classes.
 * 
 * @author Le Thanh Hoang
 * 
 */
public class UIConstants {
    private UIConstants() {
    }

    public static final String ELLIPSIS = "...";

    /**
     * The Java look and Feel standard for one screen space between GUI Components.
     */
    public static final int ONE_SPACE = 5;

    /**
     * The Java look and Feel standard for two screen spaces between GUI Components.
     */
    public static final int TWO_SPACES = 11;

    /**
     * The Java look and Feel standard for three screen spaces between GUI Components.
     */
    public static final int THREE_SPACES = 17;

    /**
     * The Java look and Feel standard for border spacing.
     */
    public static final int STANDARD_BORDER = TWO_SPACES;

    /**
     * Symbolic name for absence of keystroke mask.
     */
    public static final int NO_KEYSTROKE_MASK = 0;

    /**
     * Suggested width for a <code>JTextField</code>
     */
    public static final int SIMPLE_FIELD_WIDTH = 20;

    /**
     * Suggested width for a <code>JTextField</code> storing a file path.
     */
    public static final int FILE_PATH_FIELD_WIDTH = 30;

    /**
     * Maximum length for some <code>JLabel</code>s, beyond which the text will be truncated.
     */
    public static final int MAX_LABEL_LENGTH = 35;
}

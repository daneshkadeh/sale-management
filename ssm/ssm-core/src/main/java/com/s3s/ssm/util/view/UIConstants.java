/*
 * UIConstants
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

package com.s3s.ssm.util.view;

import java.awt.Color;
import java.awt.Font;

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

    public static final String BLANK = " ";

    public static final String HYPHEN = "-";

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
     * Suggested width for a component in edit view.
     */
    public final static int DEFAUL_TEXTAREA_ROWS = 4;

    /**
     * Suggested width for a fixed cell of the table
     */
    public final static int DEFAULT_ROW_HEADER_WIDTH = 20;

    /**
     * Suggested width for a <code>JTextField</code> storing a file path.
     */
    public static final int FILE_PATH_FIELD_WIDTH = 30;

    /**
     * Maximum length for some <code>JLabel</code>s, beyond which the text will be truncated.
     */
    public static final int MAX_LABEL_WIDTH = 150;

    /**
     * Color of row when mouse over on a row.
     */
    public static final Color HIGHLIGHT_ROW_COLOR = new Color(133, 205, 255);

    public static final int PRODUCT_CODE_COLUMN_WIDTH = 180;

    public static final int PRODUCT_NAME_COLUMN_WIDTH = 250;

    public static final int UOM_COLUMN_WIDTH = 60;

    public static final int QTY_COLUMN_WIDTH = 93;

    public static final int AMT_COLUMN_WIDTH = 110;

    public static final int NUMBER_FIELD_WIDTH = 125;

    public static final Font DEFAULT_BOLD_FONT = new Font("Arial", Font.BOLD, 12);
    public static final Font DEFAULT_ITALIC_FONT = new Font("Arial", Font.ITALIC, 12);

    public static final Color INFO_COLOR = new Color(252, 252, 209);
}

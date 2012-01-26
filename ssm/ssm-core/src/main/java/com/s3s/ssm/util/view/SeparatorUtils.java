/*
 * SeparatorUtils
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

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

/**
 * Utils class for consolidating separators on toolbars, popupmenus and menus.
 * 
 * @author Le Thanh Hoang
 * 
 */
public class SeparatorUtils {
    private SeparatorUtils() {
        // static utils only
    }

    /**
     * Consolidates separators in a toolbar:
     * <ul>
     * <li>subsequent separators will be collapsed to one separator</li>
     * <li>if the first visible item of a menu is a separator, it will be made invisible</li>
     * <li>if the last visible item of a menu is a separator, it will be made invisible</li>
     * </ul>
     * 
     * @param menu
     *            the menu (cannot be null)
     */
    public static void consolidateSeparators(JToolBar toolBar) {
        consolidateSeparators(toolBar.getComponents());
    }

    /**
     * Consolidates separators in a popupmenu:
     * <ul>
     * <li>subsequent separators will be collapsed to one separator</li>
     * <li>if the first visible item of a menu is a separator, it will be made invisible</li>
     * <li>if the last visible item of a menu is a separator, it will be made invisible</li>
     * </ul>
     * 
     * @param menu
     *            the menu (cannot be null)
     */
    public static void consolidateSeparators(JPopupMenu popupMenu) {

        consolidateSeparators(popupMenu.getComponents());
    }

    private static void consolidateSeparators(Component[] menuComponents) {
        Component previousVisibleComponent = null;
        boolean everythingInvisibleSoFar = true;

        for (int i = 0; i < menuComponents.length; i++) {
            Component menuComponent = menuComponents[i];

            // reset all separators
            if (menuComponent instanceof JSeparator) {
                menuComponent.setVisible(true);
            }

            // Separator should be invisible if
            // - previous visible item one is a separator
            // - it is the first one visible item (ie everything invisible
            // before)
            if (menuComponent instanceof JSeparator && everythingInvisibleSoFar) {
                menuComponent.setVisible(false);
            } else if (menuComponent instanceof JSeparator && previousVisibleComponent instanceof JSeparator) {
                previousVisibleComponent.setVisible(false);
            }

            if (menuComponent instanceof JSeparator) {
                previousVisibleComponent = menuComponent;
            } else if (menuComponent.isVisible()) {
                everythingInvisibleSoFar = false;
                previousVisibleComponent = menuComponent;
            }

            if (menuComponent instanceof JMenu) {
                consolidateSeparators((JMenu) menuComponent);
            }
        }

        // and if the last item on the menu is a separator -> make it invisible.
        if (previousVisibleComponent instanceof JSeparator) {
            previousVisibleComponent.setVisible(false);
        }
    }

    /**
     * Consolidates separators in a menu:
     * <ul>
     * <li>subsequent separators will be collapsed to one separator</li>
     * <li>if the first visible item of a menu is a separator, it will be made invisible</li>
     * <li>if the last visible item of a menu is a separator, it will be made invisible</li>
     * </ul>
     * 
     * @param menu
     *            the menu (cannot be null)
     */
    public static void consolidateSeparators(JMenu menu) {
        Component previousVisibleComponent = null;
        boolean everythingInvisibleSoFar = true;

        for (int j = 0; j < menu.getMenuComponentCount(); j++) {
            Component menuComponent = menu.getMenuComponent(j);

            // reset all separators
            if (menuComponent instanceof JSeparator) {
                menuComponent.setVisible(true);
            }

            // Separator should be invisible if
            // - previous visible item one is a separator
            // - it is the first one visible item (ie everything invisible
            // before)
            if (menuComponent instanceof JSeparator && everythingInvisibleSoFar) {
                menuComponent.setVisible(false);
            } else if (menuComponent instanceof JSeparator && previousVisibleComponent instanceof JSeparator) {
                previousVisibleComponent.setVisible(false);
            }

            if (menuComponent instanceof JSeparator) {
                previousVisibleComponent = menuComponent;
            } else if (menuComponent.isVisible()) {
                everythingInvisibleSoFar = false;
                previousVisibleComponent = menuComponent;
            }

            if (menuComponent instanceof JMenu) {
                consolidateSeparators((JMenu) menuComponent);
            }
        }

        // and if the last item on the menu is a separator -> make it invisible.
        if (previousVisibleComponent instanceof JSeparator) {
            previousVisibleComponent.setVisible(false);
        }
    }

    /**
     * Consolidates separators in a menubar. This essentialy calls {@link #consolidateSeparators(JMenu)} for each menu
     * in the menubar.
     * 
     * @param menuBar
     *            the menu bar (cannot be null)
     * @see #consolidateSeparators(JMenu)
     */
    public static void consolidateSeparators(JMenuBar menuBar) {
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            consolidateSeparators(menuBar.getMenu(i));
        }
    }
}

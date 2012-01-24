/*
 * SexRadio
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

package com.s3s.ssm.view.component;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;

import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * @author Le Thanh Hoang
 * 
 */
public class SexRadio extends JPanel {
    private static final long serialVersionUID = -5009249551651719126L;
    JRadioButton rbMale;
    JRadioButton rbFemale;
    int value;

    public SexRadio(int value) {
        this.value = value;
        initComponent();
    }

    private void initComponent() {
        setLayout(new MigLayout());
        ButtonGroup btnGroup = new ButtonGroup();
        rbMale = new JRadioButton();
        rbMale.setText(ControlConfigUtils.getString("SexRadio.label.male"));
        rbFemale = new JRadioButton();
        rbFemale.setText(ControlConfigUtils.getString("SexRadio.label.female"));
        if (value == 1) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }
        btnGroup.add(rbMale);
        btnGroup.add(rbFemale);
        add(rbMale);
        add(rbFemale);
    }

    /**
     * Get the value selected.
     * 
     * @return the value is selected. 1 is male - 0 is female
     */
    public int getSelectedValue() {
        if (rbMale.isSelected()) {
            return 1;
        }
        return 0;
    }
}

/*
 * NotEmptyValidator
 * 
 * Project: SSM
 * 
 * Copyright 2010 by S3SSoft
 * Rue de la Bergère 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of S3SSoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with S3SSoft.
 */

package com.s3s.ssm.view.component.validation;

import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

/**
 * Used for JTextField component.
 * 
 * @author Phan Hong Phuc
 * @since Nov 11, 2011
 * 
 */
public class NotEmptyValidator extends AbstractValidator {

    /**
     * Default constructor.
     * 
     * @param parent
     * @param c
     * @param message
     */
    public NotEmptyValidator(Window parent, JComponent c, String message) {
        super(parent, c, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean validationCriteria(JComponent c) {
        return StringUtils.isNotBlank(((JTextField) c).getText());
    }

}

/*
 * ValidationCapable
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

package com.s3s.ssm.view.component.validation;

/**
 * @author Phan Hong Phuc
 * @since Nov 11, 2011
 * 
 */
public interface ValidationCapable {
    void validateFailed(); // Called when a component has failed validation.

    void validatePassed(); // Called when a component has passed validation.
}

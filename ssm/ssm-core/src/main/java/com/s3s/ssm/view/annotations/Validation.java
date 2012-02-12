/*
 * Validator
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

package com.s3s.ssm.view.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.s3s.ssm.view.AbstractEditView;

/**
 * The annotation to mark on the method which perform validation. The method must be insided {@link AbstractEditView}
 * and return <code>true</code> of validation success or <code>false</code> if validation fail.
 * 
 * @author Phan Hong Phuc
 * 
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface Validation {

}

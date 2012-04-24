/*
 * OperatorService
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

package com.s3s.ssm.interfaces.operator;

import java.util.List;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.operator.Stall;

/**
 * @author Le Thanh Hoang
 * 
 */
public interface IOperatorService {
    public List<Operator> getOperators();

    public List<Stall> getStalls();
}

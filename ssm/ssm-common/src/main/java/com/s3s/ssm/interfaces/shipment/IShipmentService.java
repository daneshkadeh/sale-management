/*
 * IShipmentService
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

package com.s3s.ssm.interfaces.shipment;

import java.util.List;

import com.s3s.ssm.entity.shipment.TransportationType;

/**
 * @author Le Thanh Hoang
 * 
 */
public interface IShipmentService {
    List<TransportationType> getAllTransType();
}

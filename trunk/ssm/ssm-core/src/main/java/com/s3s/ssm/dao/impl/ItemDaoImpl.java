/*
 * ItemDaoImpl
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

package com.s3s.ssm.dao.impl;

import org.springframework.stereotype.Repository;

import com.s3s.ssm.dao.ItemDao;
import com.s3s.ssm.entity.param.Item;

/**
 * @author Phan Hong Phuc
 * @since Nov 8, 2011
 * 
 */
@Repository("itemDao")
public class ItemDaoImpl extends BaseDaoImpl<Item> implements ItemDao {
}

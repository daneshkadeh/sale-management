/*
 * ItemServiceImpl
 * 
 * Project: SSM
 * 
 * Copyright 2010 by S3SSoft
 * Rue de la Bergre 7, 1217 Meyrin
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of S3SSoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with S3SSoft.
 */

package com.s3s.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dao.ItemDao;
import com.s3s.ssm.entity.param.Item;
import com.s3s.ssm.entity.param.Product;
import com.s3s.ssm.service.ItemService;

/**
 * @author Phan Hong Phuc
 * @since Nov 8, 2011
 * 
 */
@Service("itemService")
@Transactional
public class ItemServiceImpl implements ItemService {

    // TODO should inject the daoHelper into the parent
    @Autowired
    ItemDao itemDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProductCodeOfItem() {
        Item item = itemDao.findAll().get(0);

        Product product = item.getProduct();
        return product.getCode();
    }

}

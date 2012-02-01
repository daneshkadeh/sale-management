/*
 * DefaultBaseDaoImpl
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
package com.s3s.ssm.dao.impl;

import org.springframework.stereotype.Repository;

import com.s3s.ssm.entity.AbstractBaseIdObject;

/**
 * This bean is not singleton. It will help to generate default DAO in DaoHelperImpl.
 * 
 * @author phamcongbang
 * 
 */
@Repository("defaultBaseDao")
public class DefaultBaseDaoImpl extends BaseDaoImpl<AbstractBaseIdObject> {

}

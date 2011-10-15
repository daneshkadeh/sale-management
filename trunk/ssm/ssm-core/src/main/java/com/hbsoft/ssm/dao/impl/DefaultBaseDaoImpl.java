package com.hbsoft.ssm.dao.impl;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.entity.AbstractBaseIdObject;

/**
 * This bean is not singleton. It will help to generate default DAO in DaoHelperImpl.
 * 
 * @author phamcongbang
 * 
 */
@Repository("defaultBaseDao")
public class DefaultBaseDaoImpl extends HibernateBaseDaoImpl<AbstractBaseIdObject> {

}

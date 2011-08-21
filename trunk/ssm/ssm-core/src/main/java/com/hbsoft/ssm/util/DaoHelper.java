package com.hbsoft.ssm.util;

import com.hbsoft.ssm.dao.impl.HibernateBaseDaoImpl;

/**
 * 
 * @author phamcongbang
 * 
 */
public interface DaoHelper {
    HibernateBaseDaoImpl getDao(Class clazz);
}

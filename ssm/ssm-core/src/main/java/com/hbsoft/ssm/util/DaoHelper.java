package com.hbsoft.ssm.util;

import com.hbsoft.ssm.dao.HibernateBaseDao;

/**
 * 
 * @author phamcongbang
 * 
 */
public interface DaoHelper {
    public <T> HibernateBaseDao<T> getDao(Class<T> clazz);
}

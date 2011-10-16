package com.s3s.ssm.util;

import com.s3s.ssm.dao.HibernateBaseDao;

/**
 * 
 * @author phamcongbang
 * 
 */
public interface DaoHelper {
    public <T> HibernateBaseDao<T> getDao(Class<T> clazz);
}

package com.s3s.ssm.util;

import com.s3s.ssm.dao.IBaseDao;

/**
 * 
 * @author phamcongbang
 * 
 */
public interface DaoHelper {
    public <T> IBaseDao<T> getDao(Class<T> clazz);
}

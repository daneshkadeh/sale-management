package com.hbsoft.ssm.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.dao.HibernateBaseDao;

/**
 * This class help to get DAO from an entity class. Support working with entity.
 * 
 * @author phamcongbang
 * 
 */
@Repository("daoHelper")
public class DaoHelperImpl implements DaoHelper {
    private Map<Class<?>, HibernateBaseDao<?>> mapDAOs = new HashMap<Class<?>, HibernateBaseDao<?>>();

    public DaoHelperImpl() {
        initDAOs();
    }

    /**
     * Loop all entity package com.hbsoft.ssm.entity to get classes. Get DAOs from spring bean or create DAO
     * HibernateBaseDaoImpl Then put into mapDAOs. TODO: This is not working.
     */
    private void initDAOs() {
        // Todo: loop all entity package com.hbsoft.ssm.entity to get classes
        // get DAOs from spring bean or create DAO HibernateBaseDaoImpl
        // Maybe we have to set up DAO context in this method.
    }

    @SuppressWarnings("unchecked")
    public <T> HibernateBaseDao<T> getDao(Class<T> clazz) {
        HibernateBaseDao<T> dao = (HibernateBaseDao<T>) mapDAOs.get(clazz);
        if (dao == null) {
            try {
                dao = (HibernateBaseDao<T>) ConfigProvider.getInstance().getApplicationContext()
                        .getBean(clazz.getSimpleName() + "DaoImpl");
            } catch (BeansException e) {
                dao = (HibernateBaseDao<T>) ConfigProvider.getInstance().getApplicationContext()
                        .getBean("defaultBaseDao");
            }
            mapDAOs.put(clazz, dao);
            dao.setEntityClass(clazz);
        }
        return dao;
    }
}

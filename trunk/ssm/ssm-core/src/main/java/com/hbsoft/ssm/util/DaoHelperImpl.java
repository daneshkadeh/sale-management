package com.hbsoft.ssm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;

import com.hbsoft.ssm.dao.impl.HibernateBaseDaoImpl;

/**
 * This class does not work now. </br> This class help to get DAO from an entity class. Support working with entity.
 * 
 * @author phamcongbang
 * 
 */
public class DaoHelperImpl {
    private Map<Class, HibernateBaseDaoImpl> mapDAOs = new HashMap<Class, HibernateBaseDaoImpl>();

    public DaoHelperImpl() {
        initDAOs();
    }

    /**
     * Loop all entity package com.hbsoft.ssm.entity to get classes. Get DAOs from spring bean or create DAO
     * HibernateBaseDaoImpl Then put into mapDAOs
     */
    private void initDAOs() {
        // Todo: loop all entity package com.hbsoft.ssm.entity to get classes
        // List<Class> listClass = getClass().getD
        List<Class> listClass = new ArrayList<Class>(); // TODO: this not work

        for (Class clazz : listClass) {
            HibernateBaseDaoImpl dao = null;
            try {
                dao = (HibernateBaseDaoImpl) ConfigProvider.getInstance().getApplicationContext()
                        .getBean(clazz.getSimpleName() + "DaoImpl");
            } catch (BeansException e) {
                // not specify bean for Dao, create default Dao.
                dao = new HibernateBaseDaoImpl(clazz);
            }
            mapDAOs.put(clazz, dao);
        }
        // get DAOs from spring bean or create DAO HibernateBaseDaoImpl

    }

    public HibernateBaseDaoImpl getDao(Class clazz) {
        return mapDAOs.get(clazz);
    }
}

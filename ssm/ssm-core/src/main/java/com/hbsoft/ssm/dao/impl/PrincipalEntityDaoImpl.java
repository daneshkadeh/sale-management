package com.hbsoft.ssm.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.entity.PrincipalEntity;

@Repository("principalEntityDao")
public class PrincipalEntityDaoImpl extends HibernateBaseDaoImpl<PrincipalEntity> {
    public PrincipalEntity findByName(String name) {
        List<PrincipalEntity> list = getHibernateTemplate().find("from PermissionEntity where name = '%" + name + "%'");
        if (CollectionUtils.isNotEmpty(list)) {
            return (PrincipalEntity) list.get(0);
        }
        return null;
    }
}

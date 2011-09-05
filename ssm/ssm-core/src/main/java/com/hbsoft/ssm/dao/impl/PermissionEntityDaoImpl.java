package com.hbsoft.ssm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.entity.PermissionEntity;

@Repository("permissionEntityDao")
public class PermissionEntityDaoImpl extends HibernateBaseDaoImpl<PermissionEntity> {
    public List<PermissionEntity> findLikeName(String name) {
        List<PermissionEntity> list = getHibernateTemplate().find(
                "from PermissionEntity where name like '%" + name + "%'");
        return list;
    }
}

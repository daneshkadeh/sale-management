package com.s3s.ssm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.s3s.ssm.entity.PermissionEntity;

@Repository("permissionEntityDao")
public class PermissionEntityDaoImpl extends HibernateBaseDaoImpl<PermissionEntity> {
    public List<PermissionEntity> findLikeName(String name) {
        List<PermissionEntity> list = getHibernateTemplate().find(
                "from PermissionEntity where name like '%" + name + "%'");
        return list;
    }
}

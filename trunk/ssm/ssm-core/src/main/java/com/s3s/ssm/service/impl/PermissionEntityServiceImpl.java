package com.s3s.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3s.ssm.dao.impl.PermissionEntityDaoImpl;
import com.s3s.ssm.entity.PermissionEntity;
import com.s3s.ssm.service.PermissionEntityService;

@Service("permissionEntityService")
public class PermissionEntityServiceImpl implements PermissionEntityService {
    @Autowired
    PermissionEntityDaoImpl permissionDao;

    public void save(PermissionEntity permission) {
        permissionDao.save(permission);
    }

    public void update(PermissionEntity permission) {
        permissionDao.update(permission);
    }

    public void delete(PermissionEntity permission) {
        permissionDao.delete(permission);
    }

}

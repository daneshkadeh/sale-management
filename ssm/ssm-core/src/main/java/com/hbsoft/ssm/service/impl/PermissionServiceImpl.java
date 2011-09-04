package com.hbsoft.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hbsoft.ssm.dao.impl.PermissionDaoImpl;
import com.hbsoft.ssm.entity.Permission;
import com.hbsoft.ssm.service.PermissionService;

public class PermissionServiceImpl implements PermissionService{
	@Autowired
	PermissionDaoImpl permissionDao;
	
	public void save(Permission permission) {
		permissionDao.save(permission);
	}

	public void update(Permission permission) {
		permissionDao.update(permission);
	}

	public void delete(Permission permission) {
		permissionDao.delete(permission);
	}

}

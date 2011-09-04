package com.hbsoft.ssm.service;

import com.hbsoft.ssm.entity.Permission;

public interface PermissionService {
	void save(Permission permission);
	
	void update(Permission permission);
	
	void delete(Permission permission);
}

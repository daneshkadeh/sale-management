package com.hbsoft.ssm.service;

import com.hbsoft.ssm.entity.PermissionEntity;

public interface PermissionEntityService {
    void save(PermissionEntity permission);

    void update(PermissionEntity permission);

    void delete(PermissionEntity permission);
}

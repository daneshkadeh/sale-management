package com.s3s.ssm.service;

import com.s3s.ssm.entity.PermissionEntity;

public interface PermissionEntityService {
    void save(PermissionEntity permission);

    void update(PermissionEntity permission);

    void delete(PermissionEntity permission);
}

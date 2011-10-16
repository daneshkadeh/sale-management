package com.s3s.ssm.service;

import com.s3s.ssm.entity.PrincipalEntity;

public interface PrincipalEntityService {
    void save(PrincipalEntity principal);

    void update(PrincipalEntity principal);

    void delete(PrincipalEntity principal);

    PrincipalEntity findByName(String name);
}

package com.hbsoft.ssm.service;

import com.hbsoft.ssm.entity.PrincipalEntity;

public interface PrincipalEntityService {
    void save(PrincipalEntity principal);

    void update(PrincipalEntity principal);

    void delete(PrincipalEntity principal);

    PrincipalEntity findByName(String name);
}

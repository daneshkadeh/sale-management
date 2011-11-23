package com.s3s.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3s.ssm.dao.impl.PrincipalEntityDaoImpl;
import com.s3s.ssm.entity.PrincipalEntity;
import com.s3s.ssm.service.PrincipalEntityService;

@Service("principalEntityService")
public class PrincipalEntityServiceImpl implements PrincipalEntityService {
    @Autowired
    PrincipalEntityDaoImpl principalEntityDao;

    public void save(PrincipalEntity principal) {
        principalEntityDao.save(principal);
    }

    public void update(PrincipalEntity principal) {
        principalEntityDao.update(principal);
    }

    public void delete(PrincipalEntity principal) {
        principalEntityDao.delete(principal);
    }

    public PrincipalEntity findByName(String name) {
        return principalEntityDao.findByName(name);
    }
}

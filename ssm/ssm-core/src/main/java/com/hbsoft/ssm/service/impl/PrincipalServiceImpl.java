package com.hbsoft.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.ssm.dao.impl.PrincipalDaoImpl;
import com.hbsoft.ssm.entity.Principal;
import com.hbsoft.ssm.service.PrincipalService;
@Service("principalService")
public class PrincipalServiceImpl implements PrincipalService {
	@Autowired
	PrincipalDaoImpl principalDao;
	
	public void save(Principal principal) {
		principalDao.save(principal);
	}

	public void update(Principal principal) {
		principalDao.update(principal);
	}

	public void delete(Principal principal) {
		principalDao.delete(principal);
	}
}

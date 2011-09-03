package com.hbsoft.ssm.service;

import com.hbsoft.ssm.entity.Principal;

public interface PrincipalService {
	void save(Principal principal);
	
	void update(Principal principal);
	
	void delete(Principal principal);
}

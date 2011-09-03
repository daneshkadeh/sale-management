package com.hbsoft.ssm.service;

import com.hbsoft.ssm.entity.User;

public interface UserService {
	
	void save(User user);
	
	void update(User user);
	
	void delete(User user);
	
	User findByUsername(String username);
}

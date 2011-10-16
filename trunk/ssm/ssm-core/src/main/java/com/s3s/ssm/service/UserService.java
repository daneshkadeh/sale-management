package com.s3s.ssm.service;

import com.s3s.ssm.entity.User;

public interface UserService {
	
	void save(User user);
	
	void update(User user);
	
	void delete(User user);
	
	User findByUsername(String username);
}

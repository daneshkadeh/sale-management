package com.s3s.ssm.entity.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.GrantedAuthority;

public class SecurityMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
		UserDetailManagerImpl userDetailsManager = new UserDetailManagerImpl();
		User user = new User();
		user.setUsername("Hoang Le");
		user.setPassword("123456");
		Set<Role> roles= new HashSet<Role>();
		roles.add(new Role("ADMIN"));
		user.setRoles(roles);
		userDetailsManager.createUser(user);
	}

}

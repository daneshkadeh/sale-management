package com.s3s.ssm.service.impl;



import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dao.IBaseDao;
import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.util.ConfigProvider;

public class UserDetailManagerImpl implements UserDetailsService {
	private IBaseDao<User> userDao = (IBaseDao<User>) ConfigProvider.getInstance().getDaoHelper().getDao(User.class);

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class)
			    .add( Property.forName("username").eq(username) );
		
			List<User> userDetailsList = userDao.findByCriteria(dc);
			
			if(userDetailsList.size() == 0) {
				throw new UsernameNotFoundException(username + " not found");
			} else {
				return userDetailsList.get(0);
			}
	}
	
	public void createUser(User user) {
		userDao.save(user);
	}
}

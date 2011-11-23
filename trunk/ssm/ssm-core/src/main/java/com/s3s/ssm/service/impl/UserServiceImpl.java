package com.s3s.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3s.ssm.dao.impl.UserDaoImpl;
import com.s3s.ssm.entity.User;
import com.s3s.ssm.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDaoImpl userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        return userDao.findByUsername(username);
    }
}

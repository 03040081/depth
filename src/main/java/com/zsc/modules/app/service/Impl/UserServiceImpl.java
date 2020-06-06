package com.zsc.modules.app.service.Impl;

import com.zsc.modules.app.dao.UserDao;
import com.zsc.modules.app.entity.UserEntity;
import com.zsc.modules.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity selectById(Long userId) {
        return userDao.selectById(userId);
    }

    @Override
    public UserEntity queryByMobile(String mobile) {
        return userDao.selectOne(mobile);
    }

    @Override
    public long insert(UserEntity user) {
        return userDao.insert(user);
    }

    @Override
    public int update(UserEntity user) {
        return userDao.update(user);
    }
}

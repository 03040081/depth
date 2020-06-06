package com.zsc.modules.app.dao;

import com.zsc.modules.app.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    /**
     * 根据主键id查找用户
     * @param userId
     * @return
     */
    UserEntity selectById(long userId);

    /**
     * 通过手机号获取用户信息
     * @param mobile
     * @return
     */
    UserEntity selectOne(String mobile);

    /**
     * 新增用户信息
     * @param user
     * @return
     */
    long insert(UserEntity user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int update(UserEntity user);
}

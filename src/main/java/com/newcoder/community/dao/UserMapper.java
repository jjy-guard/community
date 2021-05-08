package com.newcoder.community.dao;

import com.newcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 根据 id 查询用户信息
     * @param id
     * @return
     */
    User selectById(int id);

    /**
     * 根据 username 查询用户信息
     * @param username
     * @return
     */
    User selectByName(String username);

    /**
     * 根据 email 查询用户信息
     * @param email
     * @return
     */
    User selectByEmail(String email);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 根据 id 找到用户并更新 status 字段
     * @param id
     * @param status
     * @return
     */
    int updateStatus(int id, int status);

    /**
     * 根据 id 找到用户并更新 headerUrl 字段
     * @param id
     * @param headerUrl
     * @return
     */
    int updateHeader(int id, String headerUrl);

    /**
     * 根据 id 找到用户并更新 password 字段
     * @param id
     * @param password
     * @return
     */
    int updatePassword(int id, String password);
}

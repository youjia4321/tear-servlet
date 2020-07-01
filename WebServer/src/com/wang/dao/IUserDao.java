package com.wang.dao;

import com.wang.bean.User;

import java.util.List;

public interface IUserDao {

    User getUserByNameAndPass(User user);
    boolean saveUser(User user);
    List<User> getUserList();

}

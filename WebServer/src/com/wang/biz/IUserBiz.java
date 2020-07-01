package com.wang.biz;

import com.wang.bean.User;

import java.util.List;

public interface IUserBiz {

    boolean login(User user);
    boolean register(User user);
    List<User> getUserList();

}

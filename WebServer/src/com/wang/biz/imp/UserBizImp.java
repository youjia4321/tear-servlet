package com.wang.biz.imp;

import com.wang.bean.User;
import com.wang.biz.IUserBiz;
import com.wang.dao.IUserDao;
import com.wang.dao.imp.UserDaoImp;

import java.util.List;

public class UserBizImp implements IUserBiz {

    IUserDao userDao = new UserDaoImp();

    @Override
    public boolean login(User user) {
        return userDao.getUserByNameAndPass(user) != null;
    }

    @Override
    public boolean register(User user) {
        return userDao.saveUser(user);
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}

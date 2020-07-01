package com.wang.dao.imp;

import com.wang.bean.User;
import com.wang.dao.IUserDao;
import com.wang.utils.DBUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImp implements IUserDao {


    @Override
    public User getUserByNameAndPass(User user) {

        return DBUtil.queryBean(
                "select * from users where username=? and password=?",
                User.class,
                user.getUsername(),
                user.getPassword()
        );
    }

    @Override
    public boolean saveUser(User user) {

        return DBUtil.update(
                "insert into users(username, password, age, email) values(?,?,?,?)",
                user.getUsername(), user.getPassword(), user.getAge(), user.getEmail()
        );
    }

    @Override
    public List<User> getUserList() {
        List<User> list = new ArrayList<>();

        list = DBUtil.queryList(
                "select * from users",
                User.class
        );

        return list;
    }
}

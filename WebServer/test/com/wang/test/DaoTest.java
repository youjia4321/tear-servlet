package com.wang.test;

import com.wang.bean.User;
import com.wang.dao.IUserDao;
import com.wang.dao.imp.UserDaoImp;
import org.junit.Test;

public class DaoTest {

    IUserDao userDao = new UserDaoImp();

    @Test
    public void test01() {
        System.out.println(userDao.getUserByNameAndPass(new User("admin", "123456")));
    }

    @Test
    public void test02() {
        System.out.println(userDao.getUserList());
    }

}

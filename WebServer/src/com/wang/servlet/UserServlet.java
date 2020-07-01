package com.wang.servlet;

import com.alibaba.fastjson.JSON;
import com.javax.servlet.HttpServlet;
import com.javax.servlet.HttpServletRequest;
import com.javax.servlet.HttpServletResponse;
import com.wang.bean.User;
import com.wang.biz.IUserBiz;
import com.wang.biz.imp.UserBizImp;
import com.wang.core.HttpResponse;
import com.wang.utils.RespStatic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class UserServlet implements HttpServlet {

    IUserBiz userBiz = new UserBizImp();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String age = request.getParameter("age");
        String email = request.getParameter("email");

        String[] hobby = request.getParameterValues("hobby");
//        System.out.println(Arrays.toString(hobby));

        if(action.equals("userLogin")) {

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type:text/html;charset=utf-8");
            out.println();
            if(userBiz.login(new User(username, password))) {
                out.print("<script>alert('登录成功');location.href='index.html'</script>");
            } else {
                out.print("<script>alert('登录失败，账户名或密码错误');location.href='login.html'</script>");
            }

        } else if(action.equals("userRegister")) {
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type:text/html;charset=utf-8");
            out.println();

            User user = new User(username, password, Integer.parseInt(age), email);
            if(userBiz.register(user)) {
                out.print("<script>alert('注册成功');location.href='login.html'</script>");
            } else {
                out.print("<script>alert('注册失败');location.href='register.html'</script>");
            }

        } else if(action.endsWith("showUsers")) {
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type:application/json;charset=utf-8");
            out.println();
            List<User> userList = userBiz.getUserList();
            String jsonStr = JSON.toJSONString(userList);
            out.print(jsonStr);
        } else {
            RespStatic.resp404(out);
        }
        out.flush();
    }

}

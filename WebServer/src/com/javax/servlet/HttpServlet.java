package com.javax.servlet;

import java.io.PrintWriter;

public interface HttpServlet {

    void service(HttpServletRequest request, HttpServletResponse response);

}

package com.javax.servlet;

public interface HttpServletRequest {

    String getParameter(String name);

    String[] getParameterValues(String name);

}

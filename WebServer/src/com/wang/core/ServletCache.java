package com.wang.core;

import com.javax.servlet.HttpServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletCache {
    // 复用 单例思想 提高性能

    public static Map<String, HttpServlet> servletMap = new HashMap<>();

    public static void putServlet(String uri, HttpServlet servlet) {
        servletMap.put(uri, servlet);
    }

    public static HttpServlet getServlet(String uri) {
        return servletMap.get(uri);
    }

}

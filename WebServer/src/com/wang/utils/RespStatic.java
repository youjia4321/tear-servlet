package com.wang.utils;

import com.wang.core.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RespStatic {

    public static void resp404(PrintWriter out) {
        // 404找不到资源
        try {
            out.println("HTTP/1.1 404 NotFound");
            out.println("Content-Type:text/html;charset=utf-8");
            out.println();
            out.print(HttpResponse.response("./WebServer/webapps/OA/404.html"));
            out.flush();
        } catch (IOException ignored) {}
    }


}

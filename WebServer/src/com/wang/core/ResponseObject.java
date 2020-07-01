package com.wang.core;

import com.javax.servlet.HttpServletResponse;

import java.io.PrintWriter;

public class ResponseObject implements HttpServletResponse {

    private PrintWriter writer;

    public ResponseObject(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }
}

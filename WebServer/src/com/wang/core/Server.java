package com.wang.core;

import com.wang.utils.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        int port = ServerParser.getServerPort();
        ServerSocket serverSocket = new ServerSocket(port);
        long end = System.currentTimeMillis();
        Logger.log("init resource...");
        Logger.log("web server start in " + (end-start) + " ms...");
        Logger.log("server listening "+port+", http://127.0.0.1:"+port+"/OA/index.html");

        WebParser.parseWebXml("OA");


        while (true) {

            Socket socket = serverSocket.accept();
            new Thread(new HttpHandler(socket)).start();

        }
    }

}


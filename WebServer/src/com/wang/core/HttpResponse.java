package com.wang.core;

import java.io.*;

public class HttpResponse {

    public static void main(String[] args) throws Exception {
        System.out.println(response("./WebServer/webapps/res/images/gallery_4.jpg"));
    }

    public static String response(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));

        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }

        br.close();

        return sb.toString();

    }

}

package com.wang.core;

import com.javax.servlet.HttpServlet;
import com.wang.utils.Logger;
import com.wang.utils.RespStatic;

import java.io.*;
import java.net.Socket;

public class HttpHandler  implements Runnable {

    Socket socket = null;

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String ip = socket.getInetAddress().getHostAddress();
        InputStream inputStream = null;
        try {

            inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();

            if(line != null) {

                String requestURI = line.split(" ")[1];
                String method = line.split(" ")[0];
                String uri = "./WebServer/webapps" + requestURI;
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                if(requestURI.contains("static") || requestURI.endsWith(".html") || requestURI.endsWith(".ico")) {
                    Logger.log(ip + "：" + method + " " +requestURI);
                    resp200(out, uri); // 处理静态资源

                } else {
                    Logger.log(ip + "：" + method + " " +requestURI);
                    if(requestURI.contains("?")) {
                        userOperator(out, requestURI);
                    } else {
                        RespStatic.resp404(out);
                    }
                }
            }
        } catch (Exception ignored) {}
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ignored) {}
        }
    }


    // 反射
    private void userOperator(PrintWriter out, String requestURI) throws Exception {
        // 访问的URI
        String servletPath = requestURI.split("[?]")[0];

        RequestObject request = new RequestObject(requestURI);
        ResponseObject response = new ResponseObject(out);
        // 通过反射找到对应的包

        HttpServlet servlet = ServletCache.getServlet(servletPath);
        if(servlet == null) {
            String servletClassName = WebParser.servletInfos.get(servletPath);
            Class c = Class.forName(servletClassName);
            servlet = (HttpServlet) c.newInstance();
            ServletCache.putServlet(servletPath, servlet);
        }
//        System.out.println("当前的servlet对象："+servlet);
        servlet.service(request, response);

    }

    private void resp200(PrintWriter out, String uri) {

        try {
            String html = HttpResponse.response(uri);
            out.println("HTTP/1.1 200 OK");
            if(uri.contains(".css")) {
                out.println("Content-Type: text/css;charset=utf-8");
            } else if (uri.endsWith(".gif") || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith(".ico")) {
                if(uri.endsWith(".ico")) {
                    uri = "/OA/static/images/search.gif";
                    System.out.println("ico");
                }
                // 图片以字节流的方式输出倒浏览器
                // 创建输入流，从文件读取客户端需要的数据
                InputStream in = new FileInputStream(uri);
                byte[] bytes = new byte[1024 * 14];
                int len;
                // 创建网络输出流给客户端发送数据
                OutputStream os = socket.getOutputStream();
                // 产生响应头
                os.write("HTTP/1.1 200 OK\n".getBytes());
                os.write("Content-Type: image/*\n\n".getBytes());

                while ((len = in.read(bytes)) != -1) {
                    os.write(bytes,0, len);
                }
                os.close();
                return;
            } else if(uri.contains(".js")) {
                out.println("Content-Type: application/x-javascript;charset=utf-8");
            } else {
                out.println("Content-Type: text/html;charset=utf-8");

            }
            out.println();
            out.print(html);
            out.flush();
        } catch (IOException e) {
            if(e instanceof FileNotFoundException) {
                RespStatic.resp404(out);
            }
        }
    }

}




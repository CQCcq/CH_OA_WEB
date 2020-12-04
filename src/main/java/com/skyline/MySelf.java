package com.skyline;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class MySelf implements Servlet {
//    Servlet 初始化后调用 init () 方法。用于一次性初始化
//init() 方法简单地创建或加载一些数据，这些数据将被用于 Servlet 的整个生命周期。
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

//    Servlet 调用 service() 方法来处理客户端的请求。service() 方法是执行实际任务的主要方法。
//调用 service() 方法来处理来自客户端（浏览器）的请求，并把格式化的响应写回给客户端。
//每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。
// service() 方法检查 HTTP 请求类型（GET、POST、PUT、DELETE 等），并在适当的时候调用 doGet、doPost、doPut，doDelete 等方法。
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        PrintWriter out = servletResponse.getWriter();
        out.println("Hello, Java Web.");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

//    Servlet 销毁前调用 destroy() 方法。
    @Override
    public void destroy() {

    }
//    最后，Servlet 是由 JVM 的垃圾回收器进行垃圾回收的。
}

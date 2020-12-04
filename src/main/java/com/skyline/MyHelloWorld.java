package com.skyline;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyHelloWorld  extends HttpServlet {
    private String message;
    public void init() throws ServletException {
        System.out.println("servlet初始化-------------------------------------------");
        message = "你好我的世界!";
    }
//    public void doGet(){}
public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
        throws ServletException, IOException {
        //设置响应内容类型
        response.setContentType("text/html;charset=utf-8");

        //实际逻辑在这里
    PrintWriter out = response.getWriter();
    out.println("<h1>" + message + "</h1>");
    destroy();
}
public void destroy(){
    System.out.println("-------------------------------------------------------------");
    System.out.println(" Servlet 生命周期结束时被调用destroy() 方法可以让您的 Servlet 关闭数据库连接、停止后台线程、把 Cookie 列表或点击计数器写入到磁盘，并执行其他类似的清理活动。");
    super.destroy();
}
}

package com.filter;

import javax.servlet.*;

import java.io.IOException;

public class GetUserFilter implements Filter {
//    @Override
//    public boolean isLoggable(LogRecord record) {
//        return true;
//    }

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("什么情况===================================" + filterConfig);
//        String getFilterName();//得到filter的名称。
//        String getInitParameter(String name);//返回在部署描述中指定名称的初始化参数的值。如果不存在返回null.
//        Enumeration getInitParameterNames();//返回过滤器的所有初始化参数的名字的枚举集合。
//        public ServletContext getServletContext();//返回Servlet上下文对象的引用。
    };//初始化
//    和我们编写的Servlet程序一样，Filter的创建和销毁由WEB服务器负责。 web 应用程序启动时，web 服务器将创建Filter 的实例对象，并调用其init方法，读取web.xml配置，完成对象的初始化功能，从而为后续的用户请求作好拦截的准备工作（filter对象只会创建一次，init方法也只会执行一次）。开发人员通过init方法的参数，可获得代表当前filter配置信息的FilterConfig对象。


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        //解决以Post方式提交的中文乱码问题
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        chain.doFilter(request, response);
        this.destroy();
    };//拦截请求
//    这个方法完成实际的过滤操作。当客户请求访问与过滤器关联的URL的时候，Servlet过滤器将先执行doFilter方法。FilterChain参数用于访问后续过滤器。

    public void destroy(){};//销毁
//    Filter对象创建后会驻留在内存，当web应用移除或服务器停止时才销毁。在Web容器卸载 Filter 对象之前被调用。该方法在Filter的生命周期中仅执行一次。在这个方法中，可以释放过滤器使用的资源。
}

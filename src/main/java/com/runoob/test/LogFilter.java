package com.runoob.test;

//导入必需的 java 库
import javax.servlet.*;
import java.io.PrintWriter;
import java.util.*;

//实现 Filter 类
public class LogFilter implements Filter {
    public void  init(FilterConfig config) throws ServletException {
        // 获取初始化参数
        String site = config.getInitParameter("Site");
        String siteName = config.getInitParameter("name");
        String siteUrl = config.getInitParameter("url");

        // 输出初始化参数
        System.out.println("全部信息:"+config);
        System.out.println("----------------------------------------------------");
        System.out.println("网站名称: " + site);
        System.out.println("站名称: " + siteName);
        System.out.println("网站网址: " + siteUrl);
    }
    public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

//        // 输出站点名称
//        String name = request.getParameter("name");
//        // 过滤器核心代码逻辑
//        System.out.println("过滤器获取请求参数:"+name);
//        System.out.println("站点网址：http://www.runoob.com");
//
//        System.out.println("request===========================>" + request);
//
//        // 把请求传回过滤链
//        chain.doFilter(request,response);
        //获取请求信息(测试时可以通过get方式在URL中添加name)
        //http://localhost:8080/servlet_demo/helloword?name=123
        String name = request.getParameter("name");

        // 过滤器核心代码逻辑
        System.out.println("过滤器获取请求参数:"+name);
        System.out.println("第二个过滤器执行--网站名称：www.runoob.com");

        if("123".equals(name)){
            // 把请求传回过滤链
            chain.doFilter(request, response);
        }else{
            //设置返回内容类型
            response.setContentType("text/html;charset=GBK");

            //在页面输出响应信息
            PrintWriter out = response.getWriter();
            out.print("<b>name不正确，请求被拦截，不能访问web资源</b>");
            System.out.println("name不正确，请求被拦截，不能访问web资源");
        }
    }
    public void destroy( ){
        /* 在 Filter 实例被 Web 容器从服务移除之前调用 */
    }
}

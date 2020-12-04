package com.skyline;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.Map;

import com.alibaba.fastjson.*;
import com.google.gson.*;
import com.sun.org.apache.xpath.internal.objects.XObject;


@WebServlet("/MyDatabase")
public class MyDatabase extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/runoob?serverTimezone=UTC";

//    static final String SQL_SELECT = "SELECT id, name, url FROM websites";
//
//    static final String SQL_INTO = "INSERT INTO websites VALUES(?,?,?,?,?)";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "512508";

    public MyDatabase() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("请求参数处理" + request.getParameter("name"));
//        String name = new String(request.getParameter("name").getBytes("ISO8859-1"), "UTF-8");
        System.out.println("打印正文" + request.getInputStream());
        System.out.println("请求体信息" + request.getParameterMap());
        System.out.println("getParameterMap()" + request.getParameterMap());
//        System.out.println("post"+ name);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // 响应参数格式设置
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //-------------------------------------------------------------------
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        // 读取json数据
//        System.out.println("request = " + request.getReader() + ", response = " + response + ",reader.readLine():" + reader.readLine());

        StringBuffer buffer = new StringBuffer();
        String s;
        while ((s = reader.readLine()) != null) {
            buffer.append(s);
        }
        String json = buffer.toString();
        // json解析器，解析json数据
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        // json属于对象类型时
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();  // 转化为对象
//            JsonObject introduce = object.getAsJsonObject("object");
            Connection conn = null;
            Statement stmt = null;


            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // 执行 SQL 查询
                stmt = conn.createStatement();
//            str.getName();
//            prepareStatement ps =  PreparedStatement();
//            ps = conn.prepareStatement(SQL_INTO);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO websites(name,url,alexa,country) VALUES(?,?,?,?)");
//                ResultSet  length = stmt.executeQuery("select max(id) from websites");
//                Integer MaxNum  = length;
//                ResultSet MaxNum = length;
//            ps.setInt(1, length);
                ps.setString(1, (object.get("name")).getAsString());
                ps.setString(2, (object.get("url")).getAsString());
                ps.setInt(3, (object.get("alexa")).getAsInt());
                ps.setString(4, (object.get("country")).getAsString());
                String sql;
//            sql = "INSERT INTO websites VALUES(?,?,?,?,?)";
                ps.executeUpdate();
                ResultSet rs = stmt.executeQuery("SELECT id, name, url FROM websites");
                // 展开结果集数据库
                while (rs.next()) {
                    // 通过字段检索
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String url = rs.getString("url");

                    // 输出数据
                    out.println("ID: " + id);
                    out.println(", 站点名称: " + name);
                    out.println(", 站点 URL: " + url);
                    out.println("<br />");
                }
                // 完成后关闭
//            rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 最后是用于关闭资源的块
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
        //----------------------------------------------------------------------
        ;
        //编写预处理 SQL 语句
//        String sql= "INSERT INTO websites1 VALUES(?,?,?,?,?)";

////实例化 PreparedStatement
//        ps = conn.prepareStatement(sql);
//
////传入参数，这里的参数来自于一个带有表单的jsp文件，很容易实现
//        ps.setString(1, request.getParameter("id"));
//        ps.setString(2, request.getParameter("name"));
//        ps.setString(3, request.getParameter("url"));
//        ps.setString(4, request.getParameter("alexa"));
//        ps.setString(5, request.getParameter("country"));
//
////执行数据库更新操作，不需要SQL语句
//        ps.executeUpdate();
//        sql = "SELECT id, name, url FROM websites1";
//        ps = conn.prepareStatement(sql);
//
////获取查询结果
//        ResultSet rs = ps.executeQuery();
//        doGet(request, response);
    }
}

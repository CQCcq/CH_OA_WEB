package com.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@WebServlet("/editUser")
public class EdirtorUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sitedb?serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "512508";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html;charset=GBK");//解决中文乱码
        PrintWriter out = resp.getWriter();
        BufferedReader reader = req.getReader();
        StringBuffer buffer = new StringBuffer();
        String s;
        while ((s = reader.readLine()) != null) {
            buffer.append(s);
        }
        String str = buffer.toString();
        // json解析器，解析json数据
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(str);
        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();  // 转化为对象
            Connection conn = null;
            Statement stmt = null;
            try {
                Date date = new Date();
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // 执行 SQL 查询
                stmt = conn.createStatement();
//                if(){}
//                PreparedStatement ps = conn.prepareStatement("INSERT INTO users(userName,userMobile,userEmail,userAddress,userStartTime,userDescribe,userGender,userStatus,userId) VALUES(?,?,?,?,?,?,?,?,?)");
              PreparedStatement ps = conn.prepareStatement("update users set userName = ?,userMobile = ?,userEmail = ?, userAddress = ?,userStartTime = ?,userDescribe = ?,userGender = ?,userStatus = ? where userId = ?");
                ps.setString(1, (object.get("name")).getAsString());
                ps.setInt(2, Integer.parseInt(object.get("mobile").getAsString()));
                ps.setString(3, object.get("email").getAsString());
                ps.setString(4, (object.get("address")).getAsString());
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                Date t = ft.parse(object.get("date1").getAsString());
                String Date = ft.format(t);
                ps.setTimestamp(5, Timestamp.valueOf(Date));
                ps.setString(6, object.get("desc").getAsString());
                ps.setInt(7, object.get("gender").getAsInt());
                ps.setInt(8, Integer.valueOf(1));
                int userId = (int) Math.round((Math.random() * date.getTime())/10000);
                ps.setInt(9,object.get("userId").getAsInt());
                ps.executeUpdate();
                Map<String, Object> map1 = new HashMap<>();
                List list = new ArrayList<UserInfo>();
                map1.put("code", 20000);
                map1.put("status", "success");
                map1.put("total", 0);
                map1.put("data", list);
                String resData = JSON.toJSONString(map1, SerializerFeature.WriteMapNullValue);
                out.println(resData.toString());
                out.flush();
                out.close();
                // 完成后关闭
//            rs.close();
                stmt.close();
                conn.close();
                System.out.println("新增成功");
            } catch (Exception e) {
                System.out.println(e);
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
            System.out.println(object);
        }
//        super.doPost(req, resp);
    }
}

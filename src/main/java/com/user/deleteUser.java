package com.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.javafx.collections.MappingChange;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/deleteUser")
public class deleteUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sitedb?serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "512508";
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doDelete(req, resp);
        PrintWriter out = resp.getWriter();
        BufferedReader bufferedReader = req.getReader();
        StringBuffer bf = new StringBuffer();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            bf.append(s);
        }
        String strBf = bf.toString();
        System.out.println("得到结果" + strBf);
        // json解析器，解析json数据
        JsonParser parser = new JsonParser();
        JsonElement newPar = parser.parse(strBf);
        if (newPar.isJsonObject()) {
            JsonObject object = newPar.getAsJsonObject();
            System.out.println("转化后的结果" + object);
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                // 执行 SQL 查询
                stmt = conn.createStatement();
                PreparedStatement ps = conn.prepareStatement("delete from users where userId = ?");
                ps.setInt(1,object.get("userId").getAsInt());
                ps.executeUpdate();
                List list = new ArrayList<UserInfo>();
                Map<String, Object> map = new HashMap<>();
                map.put("code", 20000);
                map.put("status", "success");
                map.put("total", 0);
                map.put("data", list);
                String resData = JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
                out.println(resData.toString());
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
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
    }
}

package com.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mysql.cj.util.StringUtils;
import com.page.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/getuser")
public class GetUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/sitedb?serverTimezone=UTC&characterEncoding=utf8";
    static final String USER = "root";
    static final String PASS = "512508";

    public GetUser() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=GBK");//解决中文乱码
        PrintWriter out = response.getWriter();
        Page page = new Page();
        UserInfo userInfo = new UserInfo();
        Connection conn = null;
        Statement stmt = null;
        String Mobile_user = ""; //处理传参为空不能进行模糊查询
        try {
            int pageSize = Integer.parseInt(request.getParameter("pageSize"));
            String userName = request.getParameter("userName");
            Mobile_user = String.valueOf(request.getParameter("userMobile"));
            int pageCount = Integer.parseInt(request.getParameter("pageCurrent"));
            page.setPageNumber(pageCount);
            userInfo.setName(userName);
            page.setPageSize(pageSize);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String userNameOne = ""; //处理传参为空不能进行模糊查询
        if (userInfo.getName().equals("") || StringUtils.isNullOrEmpty(userInfo.getName())) {
            userNameOne = "";
        } else {
            userNameOne = userInfo.getName();
        }
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql_Count = "SELECT count(id) countNum FROM users where user_name LIKE '" + userNameOne + "%' AND user_mobile LIKE '" + Mobile_user + "%'";
            ResultSet count = stmt.executeQuery(sql_Count);
            while (count.next()) {
                int total = count.getInt(1);
                page.setTotalCount(total);
            }
//            int nums=count.getInt(0);
            String sql_search = "SELECT * FROM users limit " + (page.getPageNumber() - 1) * page.getPageSize() + "," + page.getPageSize();
            String search_word = "SELECT * FROM users where user_name LIKE '" + userNameOne + "%' AND user_mobile LIKE '" + Mobile_user + "%' limit " + (page.getPageNumber() - 1) * page.getPageSize() + "," + page.getPageSize();
            ResultSet rs = stmt.executeQuery(search_word);

            // 展开结果集数据库
            List list = new ArrayList<UserInfo>();
            while (rs.next()) {
                // 通过字段检索
                // 输出数据
                HashMap<Object, Object> map = new HashMap<Object, Object>();
                userInfo.setID(rs.getInt("id"));
                userInfo.setName(rs.getString("user_name"));
                userInfo.setGender(rs.getInt("user_gender"));
                userInfo.setMobile(rs.getInt("user_mobile"));
                userInfo.setEmail(rs.getString("user_email"));
                userInfo.setAddress(rs.getString("user_address"));
                userInfo.setStartTime(rs.getTimestamp("user_start_time"));
                userInfo.setDescribe(rs.getString("user_describe"));
                userInfo.setStatus(rs.getInt("user_status"));
                map.put("id", userInfo.getID());
                map.put("name", userInfo.getName());
                map.put("gender", userInfo.getGender());
                map.put("mobile", userInfo.getMobile());
                map.put("email", userInfo.getEmail());
                map.put("address", userInfo.getAddress());
                map.put("startTime", userInfo.getStartTime().toLocalDateTime());
                map.put("describe", userInfo.getDescribe());
                map.put("status", userInfo.getStatus());
                list.add(map);
            }
            Object[] obj = new Object[list.size()];
            Map<String, Object> map1 = new HashMap<>();
            map1.put("code", 20000);
            map1.put("status", "success");
            map1.put("total", page.getTotalCount());
            map1.put("data", list);
            String resData = JSON.toJSONString(map1, SerializerFeature.WriteMapNullValue);
            out.println(resData.toString());
            out.flush();
            out.close();
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

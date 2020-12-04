<%--
  Created by IntelliJ IDEA.
  User: 11691
  Date: 2020/11/23
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.LocalDateTime" %>
<html>
<body>
<h2>
    <%
        out.write(LocalDateTime.now().toString());
    %>
</h2>
</body>
</html>

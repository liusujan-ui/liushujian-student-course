<%--
  Created by IntelliJ IDEA.
  User: ThinkPad
  Date: 2025/11/12
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>这是springboot下的jsp</title>
  </head>
  <body>
      welcome！！！<%=request.getAttribute("name") %>
      你好，${name}
  </body>
</html>

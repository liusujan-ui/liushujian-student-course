<%--
  Created by IntelliJ IDEA.
  User: ThinkPad
  Date: 2025/11/12
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="utf-8">
    <title>Title</title>
    <title>登录页面</title>
<%--  jquery文件。务必在bootstrap.min.js 之前引入--%>
  <script src="http://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<%--  新bootstrap4 核心css文件--%>
  <link href="http://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet">
<%--  popper.min.js 用于弹窗、提示、下拉菜单--%>
  <script src="http://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
<%--  最新的bootstrap4 核心 javascript 文件--%>
  <script src="http://cdn.staticfile.org/twitter-bootstrap/4.1.0/js/bootstrap.min.js"></script>
  <script>
    function add(){
     // 弹出新窗口
      $('#myModal').modal('hide');
    }
  </script>
</head>
<body>
<div class="container">
  <table class="table table-dark table-hover">
    <thead>
    <tr>
      <th>empno</th>
      <th>ename</th>
      <th>job</th>
      <th>sal</th>
      <th>mgr</th>
      <th>comm</th>
      <th>deptno</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${emps}" var="emp">
    <tr>
      <td>${emp.empno}</td>
      <td>${emp.ename}</td>
      <td>${emp.job}</td>
      <td>${emp.sal}</td>
      <td>${emp.mgr}</td>
      <td>${emp.comm}</td>
      <td>${emp.deptno}</td>
      <th><button id="${emp.empno}" onclick="return confirm('你确定要删除这条数据吗？')">删除</button></th>
      <th><button id="${emp.empno}">修改</button></th>
    </tr>
    </c:forEach>
    </tbody>
  </table>
  <button onclick="add()">添加</button>
</div>
</body>
</html>

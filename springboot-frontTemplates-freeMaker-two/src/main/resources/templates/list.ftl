<html>
<head>
    <meta charset="utf-8">
    <title>Title</title>
    <title>登录页面</title>
<#--    <%--  jquery文件。务必在bootstrap.min.js 之前引入--%>-->
    <script src="http://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<#--    <%--  新bootstrap4 核心css文件--%>-->
    <link href="http://cdn.staticfile.org/twitter-bootstrap/4.1.0/css/bootstrap.min.css" rel="stylesheet">
<#--    <%--  popper.min.js 用于弹窗、提示、下拉菜单--%>-->
    <script src="http://cdn.staticfile.org/popper.js/1.12.5/umd/popper.min.js"></script>
<#--    <%--  最新的bootstrap4 核心 javascript 文件--%>-->
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

            <#list emplist as emp>
            <tr>
                <td>${emp.empno}</td>
                <td>${emp.ename}</td>
                <td>${emp.job}</td>
                <td>${emp.sal!"没有薪水"}</td>
                <td>${emp.mgr!"没有上司"}</td>
                <td>
                    <#if emp.comm??>
                        <#if emp.comm=1300>
                            奖金最高！！！
                        <#else>
                            ${emp.comm}
                        </#if>
                    <#else>
                        没有奖金！！！
                    </#if>
                </td>
                <td>${emp.deptno}</td>
                <th><button id="${emp.empno}" onclick="return confirm('你确定要删除这条数据吗？')">删除</button></th>
                <th><button id="${emp.empno}">修改</button></th>
            </tr>
            </#list>
        </tbody>
    </table>
    <button onclick="add()">添加</button>
</div>
</body>
</html>

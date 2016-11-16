<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>管理员页面</title>
    <%@include file="common/head.jsp"%>
    <%@include file="common/tag.jsp"%>
</head>
<style>
    #hunger td,  #hunger th{
        text-align: center;
    }
</style>
<body>
<%--页面显示部分--%>
<div class="container" id="hunger">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h1>申请列表页</h1>
        </div>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="../${sessionScope.organId}/menbers">成员列表</a></li>
            <li role="presentation"><a href="../${sessionScope.organId}/list">申请列表</a></li>
            <li role="presentation"><a href="#">更多</a></li>
        </ul>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>头像</th>
                    <th>名称</th>
                    <th>邮箱号</th>
                    <th>申请时间</th>
                    <th>处理</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty applys}">
                    <h2>没有申请信息！</h2>
                </c:if>
                <c:forEach var="apply" items="${applys}">
                    <tr>
                        <td>${apply.sender.picture}</td>
                        <td>${apply.sender.userName}</td>
                        <td>${apply.sender.email}</td>
                        <td><fmt:formatDate value="${apply.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                        <td><a class="btn btn-success" href="../${apply.organId}/${apply.sender.userId}/${apply.applyId}/1/execution">同意</a>
                        <a class="btn btn-danger" href="../${apply.organId}/${apply.sender.userId}/${apply.applyId}/2/execution">拒绝</a> </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>






</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>

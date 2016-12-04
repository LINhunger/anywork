<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>作业列表</title>
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
            <h1>作业列表</h1>
        </div>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation"><a href="../${sessionScope.organId}/list">申请列表</a></li>
            <li role="presentation"><a href="../${sessionScope.organId}/menbers">成员列表</a></li>
            <li role="presentation"class="active"><a href="../${sessionScope.organId}/homework">作业列表</a></li>
            <li role="presentation"><a href="/anywork/src/html/createHomework.html">发布试卷</a></li>
        </ul>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>作者</th>
                    <th>作业名</th>
                    <th>发布日期</th>
                    <th>截止日期</th>
                    <th>处理</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${empty homeworks}">
                    <h2>还没有作业信息！</h2>
                </c:if>
                <c:forEach var="homework" items="${homeworks}">
                    <tr>
                        <td>${homework.author.userName}</td>
                        <td>${homework.homeworkTitle}</td>
                        <td><fmt:formatDate value="${homework.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                        <td><fmt:formatDate value="${homework.endingTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                        <td><a class="btn btn-success" href="../${homework.organId}/${homework.homeworkId}/download">下载</a>
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

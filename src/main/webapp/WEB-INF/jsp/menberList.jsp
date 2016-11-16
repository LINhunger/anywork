<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>管理员页面</title>
    <%@include file="common/head.jsp"%>
    <%@include file="common/tag.jsp"%>
    <style>
        #hunger td,  #hunger th{
            text-align: center;
        }
    </style>
</head>
<body>
<%--页面显示部分--%>
<div class="container" id = "hunger">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h1>成员列表页</h1>
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
                    <th>权限</th>
                    <th>申请时间</th>
                    <th>设置/取消管理员</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="relation" items="${relations}">
                    <tr>
                        <td>${relation.user.picture}</td>
                        <td>${relation.user.userName}</td>
                        <td>${relation.user.email}</td>
                        <td>
                        <c:choose>
                            <c:when test="${relation.role==1}">
                                <p class="label label-warning">群主</p>
                            </c:when>
                            <c:when test="${relation.role==2}">
                                <p class="label label-success">管理员</p>
                            </c:when>
                            <c:when test="${relation.role==3}">
                                <p class="label label-primary">么么哒</p>
                            </c:when>
                        </c:choose>
                        </td>
                        <td><fmt:formatDate value="${relation.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                        <c:if test="${relation.role!=1}">
                            <td><a class="btn btn-info" href="../${relation.organId}/${relation.user.userId}/${relation.relationId}/${relation.role}/setting">link</a> </td>
                        </c:if>
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

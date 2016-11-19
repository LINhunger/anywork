<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta content="3;url=/QG/success.jsp" http-equiv="refresh">
    <title>服务器发生故障</title>

  
  <body>
<h1>对不起，服务器发生内部错误，在三秒后将跳转回主页面</h1>
  </body>
</html>
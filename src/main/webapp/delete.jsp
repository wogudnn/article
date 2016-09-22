<%@page import="com.psy.news.dao.NewsDao"%>
<%@page import="com.psy.news.dao.NewsDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String newsIdParam = request.getParameter("newsId");
	if(newsIdParam==null) {
		response.sendRedirect("./detail.jsp?newsId");
	}
	
	int newsId = Integer.parseInt(newsIdParam);
	
	NewsDao newsDao = new NewsDaoImpl();
	newsDao.deleteNews(newsId);
	
	response.sendRedirect("./news.jsp");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>delete</title>
</head>
<body>

</body>
</html>
<%@page import="com.psy.article.dao.ArticleDaoImpl"%>
<%@page import="com.psy.article.dao.ArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String	articleIdParam = request.getParameter("articleId");
	if(articleIdParam==null) {
		response.sendRedirect("./article.jsp");
	}
	
	int articleId = Integer.parseInt(articleIdParam);
	
	ArticleDao articleDao = new ArticleDaoImpl();
	articleDao.deleteArticle(articleId);
	
	response.sendRedirect("./article.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
<%@page import="com.psy.article.vo.ArticlesVO"%>
<%@page import="com.psy.news.vo.NewsVO"%>
<%@page import="com.psy.article.dao.ArticleDaoImpl"%>
<%@page import="com.psy.article.dao.ArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String articleIdParam = request.getParameter("articleId");

	if( articleIdParam == null) {
		response.sendRedirect("./article.jsp");
	}
	int articleId = Integer.parseInt(articleIdParam);
	
	ArticleDao articleDao = new ArticleDaoImpl();
	
	ArticlesVO articles = articleDao.getArticleAt(articleId);
	
%>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1><%= articles.getSubject() %></h1>
	<hr/>
	<h3><%=articles.getCategoryVO().getCategoryName() %></h3>
	<h3><%=articles.getCreatDate() %></h3>
	<p>
		<%=articles.getContent() %>
	</p>
	
	<a href="./deletearticle.jsp?articleId=<%=articles.getArticleId() %>">이 기사 삭제하기</a>

</body>
</html>
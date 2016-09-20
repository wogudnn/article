<%@page import="java.util.List"%>
<%@page import="com.psy.article.vo.ArticlesVO"%>
<%@page import="com.psy.article.dao.ArticleDaoImpl"%>
<%@page import="com.psy.article.dao.ArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArticleDao articleDao = new ArticleDaoImpl();
	List<ArticlesVO> articles = articleDao.getArticlesOf();

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Article</title>
</head>
<body>
	<h1>Article</h1>
	<hr/>
	<table style="width:100%; border-collapse: collapse; border:1px solid #000000; background-color: #cccccc;">
		<tr style="height:30px">
			<th>Article ID</th>
			<th>Subject</th>
			<th>Content</th>
			<th>CreatDate</th>
		</tr>
<%
int articlesSize = articles.size();
ArticlesVO articlesVO = null;
for(int i = 0 ; i < articlesSize; i++) {
	articlesVO = articles.get(i);
%>		
		<tr style="border-top:1px solid #000000;">
			<td style="text-align:center; border-right:1px solid #000000;"><%=articlesVO.getArticleId() %></td>
			<td style="text-align:center; border-right:1px solid #000000;"><%=articlesVO.getSubject() %></td>
			<td style="text-align:center; border-right:1px solid #000000;"><%=articlesVO.getContent() %></td>
			<td style="text-align:center; border-right:1px solid #000000;"><%=articlesVO.getCreatDate() %></td>
		</tr>
<%
}
%>		
	</table>


</body>
</html>
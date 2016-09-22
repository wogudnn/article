<%@page import="com.psy.article.vo.CategoryVO"%>
<%@page import="java.util.List"%>
<%@page import="com.psy.article.vo.ArticlesVO"%>
<%@page import="com.psy.article.dao.ArticleDaoImpl"%>
<%@page import="com.psy.article.dao.ArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String categoryIdParam = request.getParameter("categoryId");
	String parentCategoryIdParam = request.getParameter("parentCategoryId");
	if( categoryIdParam==null){
		categoryIdParam = "0";
	}
	if( parentCategoryIdParam == null){
		parentCategoryIdParam = "0";
	}
	
	int categoryId = Integer.parseInt(categoryIdParam);
	int parentCategoryId = Integer.parseInt(parentCategoryIdParam);
	
	ArticleDao articleDao = new ArticleDaoImpl();
	List<CategoryVO> categories = null;
	
	boolean isleafNode = articleDao.isCategoryLeafNode(categoryId);
	if(isleafNode) {
		categories = articleDao.getCategoriesOf(parentCategoryId);
	}
	else {
		categories = articleDao.getCategoriesOf(categoryId);
	}
	
	List<ArticlesVO> articles = articleDao.getArticlesOf(categoryId);

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Article</title>
</head>
<body>
	<h1>Article</h1>
	<a href="./article.jsp">처음으로</a>&nbsp;|&nbsp;<!-- nbsp의 의미는 한칸 띄우기 -->
<%
	int categoriesSize = categories.size();
	CategoryVO category = null;
	for( int i = 0; i< categoriesSize; i++){
		category = categories.get(i);
%>
	<a href="./article.jsp?categoryId=<%=category.getCategoryId() %>&parentCategoryId=<%=category.getParentCategoryId() %>">
	<%=category.getCategoryName() %>
	</a>&nbsp;|&nbsp;
<%} %>
	<hr/>
	<table style="width:100%; border-collapse: collapse; border:1px solid #000000; background-color: #cccccc;">
		<tr style="height:30px">
			<th>Article ID</th>
			<th>Subject</th>
			<th>Category</th>
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
			<td style="text-align:center; border-right:1px solid #000000;"><a href="detailarticle.jsp?articleId=<%=articlesVO.getArticleId() %>">
			<%=articlesVO.getSubject() %></a></td>
			<td style="text-align:center; border-right:1px solid #000000;"><%=articlesVO.getCategoryVO().getCategoryName() %></td>
			<td style="text-align:center; border-right:1px solid #000000;"><%=articlesVO.getCreatDate() %></td>
		</tr>
<%
}
%>		
	</table>
<a href="./articlewrite.jsp">기사쓰기</a>

</body>
</html>
<%@page import="java.util.List"%>
<%@page import="com.psy.article.vo.CategoryVO"%>
<%@page import="com.psy.article.dao.ArticleDaoImpl"%>
<%@page import="com.psy.article.dao.ArticleDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArticleDao articleDao = new ArticleDaoImpl();
	List<CategoryVO> categories = articleDao.getCategories();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
</head>
<body>
	<form name="writeForm" method="post" action="./doarticlewrite.jsp">
		<select name="categoryId">
			<option>카테고리 선택하세요</option>
			<%
				for(CategoryVO categoryVO : categories) {
			%>
			<option value="<%=categoryVO.getCategoryId() %>">
			<%=categoryVO.getCategoryName()%>
			</option>
			<%
				}
			%>
		</select><br/>
		기사제목 : <input type="text"name="subject"
		placeholder="기사제목을입력하세요" style="width: 500px"/><br/>
		기사제목 : <textarea name="content" placeholder="기사내용을 입력하세요"
		style="width: 500px; height: 300px;"></textarea> 
		<input type="submit" value="기사 등록"/>
	</form>

</body>
</html>
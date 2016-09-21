<%@page import="com.psy.news.vo.CategoryVO"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.List"%>
<%@page import="com.psy.news.vo.JournalistVO"%>
<%@page import="com.psy.news.dao.NewsDao"%>
<%@page import="com.psy.news.dao.NewsDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	NewsDao newsDao = new NewsDaoImpl();
	List<JournalistVO> journalists = newsDao.getJournalists();
	List<CategoryVO> categories = newsDao.getCategories();
%>  
  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기사 작성하기</title>
</head>
<body>
	<!-- 
		폼태그 안에서는 아래의 태그만 유효하다
		input, textareg, select
		그외의 다른 태크들은 Design만 담당한다.
	 -->
	<form name="writeForm" method="post" action="./dowrite.jsp">
		<select name="journalistId">
			<option>선택하세요</option>
			<%
				for( JournalistVO journalistVO : journalists) {
					
			%>
			<option value="<%= journalistVO.getJournalistId() %>">
				<%=journalistVO.getJournalistName() %>
			</option>
			<%
				}
			%>
		</select>
		<select name="categoryId">
			<option>선택하세요</option>
			<%
				for( CategoryVO categoryVO : categories ) {
			%>
			<option value="<%= categoryVO.getCategoryId() %>">
				<%=categoryVO.getCategoryName() %>
			</option>
			<%
				}
			%>
		</select><br/>
		기사 제목 : <input type="text"name="subject" 
		placeholder="기사제목을입력하세요" style="width: 500px;"/><br/>
		기사 내용 : <textarea name="contents" placeholder="기사제목을입력하세요" 
		style="width: 500px; height: 300px;"></textarea>
		<input type="submit" value="기사 등록"/>
	</form>

</body>
</html>
<%@page import="com.psy.news.vo.NewsVO"%>
<%@page import="com.psy.news.dao.NewsDao"%>
<%@page import="com.psy.news.dao.NewsDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String newsIdParam = request.getParameter("newsId");

	//만약 newsId 없을경우 news.jsp로 돌려보내기
	if(newsIdParam == null) {
		response.sendRedirect("./news.jsp");
	}
	
	int newsId = Integer.parseInt(newsIdParam);
	
	// 조회수 증가시킨다.
	
	NewsDao newsDao = new NewsDaoImpl();
	newsDao.updateRecommendCount(newsId);
	
	NewsVO news = newsDao.getNewsAt(newsId);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detail</title>
</head>
<body>

	<h1><%= news.getSubject() %></h1>
	<h6><%=news.getJournalistVO().getJournalistName() %></h6>
	<h6><%=news.getCategoryVO().getCategoryName() %></h6>
	<h6><%=news.getCreatedDate() %></h6>
	<h6><%=news.getRecommendCount() %></h6>
	<hr/>
	<p>
		<%= news.getContents()%>
	</p>
	
	<a href="./delete.jsp?newsId=<%=news.getNewsId()%>">이 기사 삭제하기</a>

</body>
</html>
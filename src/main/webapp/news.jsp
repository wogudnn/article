<%@page import="com.psy.news.vo.NewsVO"%>
<%@page import="com.psy.news.dao.NewsDao"%>
<%@page import="com.psy.news.dao.NewsDaoImpl"%>
<%@page import="java.util.List"%>
<%@page import="com.psy.news.vo.NewsVO"%>
<%@page import="com.psy.news.dao.NewsDaoImpl"%>
<%@page import="com.psy.news.dao.NewsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	NewsDao newsDao = new NewsDaoImpl();
	List<NewsVO> newses = newsDao.getNewsesOf();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>News</title>
</head>
<body>
	<h1>News</h1>
	<hr/>
	<table style="width:100%; border-collapse: collapse; border: 
			1px solid #000000; background-color: #21D2CC;">
		<tr style="height: 20px">
			<th>News ID</th>
			<th>Subject</th>
			<th>Content</th>
			<th>Recommend Count</th>
			<th>Journalist ID</th>
			<th>Category ID</th>
			<th>Created Date</th>
		</tr>
<%
	int newsSize = newses.size();
	NewsVO news = null;
	for(int i = 0; i< newsSize; i ++ ) {
		news = newses.get(i);

%>
		<tr style="border-top: 1px solid #000000; color: #ffffff;">
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getNewsId() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getSubject() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getContents() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getRecommendCount() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getJournalistId() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getCategoryId() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getCreatedDate() %></td>
		</tr>
<%
}
%>
	</table>

</body>
</html>
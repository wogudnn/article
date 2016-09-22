<%@page import="com.psy.news.vo.CategoryVO"%>
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
	String categoryParam = request.getParameter("categoryId");/* 무조선 스트링 */
	String parentCategoryIdParam = request.getParameter("parentCategoryId");
	if(categoryParam == null) {/* 제일 처음 들어왔을때는 파라미터가 없을것이기때문에 초기화 시켜주기 */
		categoryParam = "0";/*이걸 안 주면 넘버포멧 익셉션*/
	}
	if(parentCategoryIdParam == null) {
		parentCategoryIdParam = "0";
	}
	
	int categoryId = Integer.parseInt(categoryParam);/* 문자를 숫자로 캐스팅 */
	int parentCategoryId = Integer.parseInt(parentCategoryIdParam);
	
	NewsDao newsDao = new NewsDaoImpl();
	List<CategoryVO> categories = null;
	
	boolean isLeafNode = newsDao.isThisCategoryLeafNode(categoryId);
	if(isLeafNode) {/* 카테고리가 리프노드일때 부모카테고리의 하위 카테고리를 보여주겠다 즉 자기 자신레벨의 카테고리를 계속 보여주겠다. */
		categories = newsDao.getCategoriesOf(parentCategoryId);
	}
	else {/* 리프노드 아니면 하위 카테고리를 보여준다. */
		categories = newsDao.getCategoriesOf(categoryId);
	}
	
	List<NewsVO> newses = newsDao.getNewsesOf(categoryId);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>News</title>
</head>
<body>
	<h1>News</h1>

	<a href="./news.jsp">처음으로</a>&nbsp;|&nbsp;<!-- nbsp의 의미는 한칸 띄우기 -->
<%
	int categoriesSize = categories.size();
	CategoryVO category = null;
	for( int i = 0; i< categoriesSize; i++){
		category = categories.get(i);
%>
	<a href="./news.jsp?categoryId=<%=category.getCategoryId() %>&parentCategoryId=<%=category.getParentCategoryId() %>">
	<%=category.getCategoryName() %>
	</a>&nbsp;|&nbsp;
<%} %>
	<hr/>
	<table style="width:100%; border-collapse: collapse; border: 
			1px solid #000000; background-color: #21D2CC;">
		<tr style="height: 20px">
			<th>News ID</th>
			<th>Subject</th>
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
			<td style="text-align:left; border-left: 1px solid #000000;"><%=news.getNewsId() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;">
			<a href="./detail.jsp?newsId=<%=news.getNewsId() %>"><%=news.getSubject() %></a></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getRecommendCount() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getJournalistVO().getJournalistName() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getCategoryVO().getCategoryName() %></td>
			<td style="text-align:center; border-left: 1px solid #000000;"><%=news.getCreatedDate() %></td>
		</tr>
<%
}
%>
	</table>
<a href="./write.jsp">기사쓰기</a>
</body>
</html>
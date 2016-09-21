<%@page import="com.psy.news.dao.NewsDao"%>
<%@page import="com.psy.news.vo.NewsVO"%>
<%@page import="com.psy.news.dao.NewsDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	//한글 깨짐 방지 코드
	
	request.setCharacterEncoding("UTF-8");

	String subject = request.getParameter("subject");
	String contents = request.getParameter("contents");
	
	
	// 컨텐츠의 줄바꿈 문자를 <br/>로 바꾸기
	contents = contents.replaceAll("\n", "<br/>")
						.replaceAll("\r", "");
	
	String categoryIdParam = request.getParameter("categoryId");
	if( categoryIdParam == null) {
		categoryIdParam = "1";
	}
	
	int categoryId = Integer.parseInt(categoryIdParam);
	
	String journalistIdParam = request.getParameter("journalistId");
	if( journalistIdParam == null ) {
		journalistIdParam = "1";
	}
	
	int journalistId = Integer.parseInt(journalistIdParam);
	
	
	NewsVO newsVO = new NewsVO();
	newsVO.setCategoryId(categoryId);
	newsVO.setJournalistId(journalistId);
	newsVO.setSubject(subject);
	newsVO.setContents(contents);
	
	NewsDao newsDao = new NewsDaoImpl();
	newsDao.addNewNews(newsVO);
	
	
	//기사등록 완료 시점에 news.jsp로 이동한다.
	response.sendRedirect("/HelloWeb/news.jsp");
%>

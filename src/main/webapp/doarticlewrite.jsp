<%@page import="com.psy.article.dao.ArticleDaoImpl"%>
<%@page import="com.psy.article.dao.ArticleDao"%>
<%@page import="com.psy.article.vo.ArticlesVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	request.setCharacterEncoding("UTF-8");

	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	
	content = content.replaceAll("\n", "<br/>")
			.replaceAll("\r", "");
	
	String categoryIdParam = request.getParameter("categoryId");
	if( categoryIdParam == null) {
		categoryIdParam = "1";
	}
	
	int categoryId = Integer.parseInt(categoryIdParam);
	
	ArticlesVO articlesVO = new ArticlesVO();
	articlesVO.setCategoryId(categoryId);
	articlesVO.setSubject(subject);
	articlesVO.setContent(content);
	
	ArticleDao articleDao = new ArticleDaoImpl();
	articleDao.addArticle(articlesVO);
	
	response.sendRedirect("/HelloWeb/article.jsp");
	
%>

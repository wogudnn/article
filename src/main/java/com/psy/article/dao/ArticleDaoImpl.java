package com.psy.article.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.psy.article.vo.ArticlesVO;

public class ArticleDaoImpl implements ArticleDao {

	@Override
	public List<ArticlesVO> getArticlesOf() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","ARTCL","artcl");
			
			StringBuffer query = new StringBuffer();
			query.append(" SELECT	ARTCL_ID ");
			query.append(" 			, SBJ ");
			query.append(" 			, CONT ");
			query.append(" 			, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') NOWDATE ");
			query.append("FROM		ARTCL");
			
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			
			List<ArticlesVO> articles = new ArrayList<ArticlesVO>();
			ArticlesVO articlesVO = null;
			while(rs.next()){
				articlesVO = new ArticlesVO();
				articlesVO.setArticleId(rs.getInt("ARTCL_ID"));
				articlesVO.setSubject(rs.getString("SBJ"));
				articlesVO.setContent(rs.getString("CONT"));
				articlesVO.setCreatDate(rs.getString("NOWDATE"));
				
				articles.add(articlesVO);
			}	
			
			return articles;
		} 
		catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		finally{
			if(rs !=null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		
	}

}

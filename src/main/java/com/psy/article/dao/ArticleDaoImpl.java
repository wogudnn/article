package com.psy.article.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.psy.article.vo.ArticlesVO;
import com.psy.article.vo.CategoryVO;

public class ArticleDaoImpl implements ArticleDao {

	@Override
	public List<ArticlesVO> getArticlesOf(int categoryId) {
		
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
			query.append(" SELECT	A.ARTCL_ID ");
			query.append(" 			, A.SBJ ");
			query.append(" 			, A.CONT ");
			query.append("			, A.CTGR_ID ");
			query.append("			, C.CTGR_NM ");
			query.append(" 			, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') NOWDATE ");
			query.append(" FROM		ARTCL A ");
			query.append(" 			, CTGR C ");
			query.append(" WHERE		A.CTGR_ID = C.CTGR_ID ");
			query.append(" AND			A.CTGR_ID = ? ");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, categoryId);
			rs = pstmt.executeQuery();
			
			List<ArticlesVO> articles = new ArrayList<ArticlesVO>();
			ArticlesVO articlesVO = null;
			CategoryVO categoryVO = null;
			
			while(rs.next()){
				articlesVO = new ArticlesVO();
				articlesVO.setArticleId(rs.getInt("ARTCL_ID"));
				articlesVO.setSubject(rs.getString("SBJ"));
				articlesVO.setContent(rs.getString("CONT"));
				categoryVO = articlesVO.getCategoryVO();
				categoryVO.setCategoryId(rs.getInt("CTGR_ID"));
				categoryVO.setCategoryName(rs.getString("CTGR_NM"));
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

	public List<CategoryVO> getCategoriesOf(int parentCategoryId) {
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
			query.append(" SELECT	CTGR_ID ");
			query.append(" 			, CTGR_NM ");
			query.append(" 			, PRNT_CTGR_ID ");
			query.append(" FROM		CTGR ");
			query.append(" WHERE	PRNT_CTGR_ID = ? ");
			
		
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, parentCategoryId);
			rs = pstmt.executeQuery();
				
			List<CategoryVO> categories = new ArrayList<CategoryVO>();
			CategoryVO categoryVO = null;
			while(rs.next()) {
				categoryVO = new CategoryVO();
				categoryVO.setCategoryId(rs.getInt("CTGR_ID"));
				categoryVO.setCategoryName(rs.getString("CTGR_NM"));
				categoryVO.setParentCategoryId(rs.getInt("PRNT_CTGR_ID"));
				
				categories.add(categoryVO);
				
			}
			
			return categories;
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	@Override
	public boolean isCategoryLeafNode(int categoryId) {
		return getCategoriesOf(categoryId).size() == 0;
	}
	@Override
	public void addArticle(ArticlesVO articlesVO) {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","ARTCL","artcl");
			StringBuffer query = new StringBuffer();
			query.append(" INSERT	INTO	ARTCL ( ");
			query.append(" 						ARTCL_ID ");
			query.append(" 						, SBJ ");
			query.append(" 						, CONT ");
			query.append(" 						, CTGR_ID ");
			query.append(" 						, CRTDT ) ");
			query.append(" VALUES			( ARTCL_ID_SEQ.NEXTVAL, ?, ?, ?, SYSDATE ) ");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, articlesVO.getSubject());
			pstmt.setString(2, articlesVO.getContent());
			pstmt.setInt(3, articlesVO.getCategoryId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		finally {
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}
	@Override
	public List<CategoryVO> getCategories() {
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
				query.append(" SELECT	CTGR_ID ");
				query.append(" 			, ( ");
				query.append(" 				SELECT	CTGR_NM ");
				query.append(" 				FROM	CTGR ");
				query.append(" 				WHERE	C1.PRNT_CTGR_ID = CTGR_ID ");
				query.append(" 				) || '-' || CTGR_NM CTGR_NM ");
				query.append(" FROM		CTGR C1 ");
				query.append(" WHERE	NOT	EXISTS ( ");
				query.append("						SELECT	'1' ");
				query.append("						FROM	CTGR C2 ");
				query.append("						WHERE	C1.CTGR_ID = C2.PRNT_CTGR_ID ");
				query.append("						) ");
				
				pstmt = conn.prepareStatement(query.toString());
				rs = pstmt.executeQuery();
				List<CategoryVO> categories = new ArrayList<CategoryVO>();
				CategoryVO categoryVO = null;
				while(rs.next()){
					categoryVO = new CategoryVO();
					categoryVO.setCategoryId(rs.getInt("CTGR_ID"));
					categoryVO.setCategoryName(rs.getString("CTGR_NM"));
					
					categories.add(categoryVO);
				}
				
				return categories;
				
			} 
			 catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			 finally{
				 if(rs != null){
					 try {
						rs.close();
					} catch (SQLException e) {}
				 }
				 if( pstmt != null) {
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
	@Override
	public ArticlesVO getArticleAt(int articleId) {
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
			query.append(" SELECT	A.ARTCL_ID ");
			query.append(" 			, A.SBJ ");
			query.append(" 			, A.CONT ");
			query.append("			, A.CTGR_ID ");
			query.append("			, C.CTGR_NM ");
			query.append(" 			, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') NOWDATE ");
			query.append(" FROM		ARTCL A ");
			query.append(" 			, CTGR C ");
			query.append(" WHERE		A.CTGR_ID = C.CTGR_ID ");
			query.append(" AND			A.ARTCL_ID = ? ");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, articleId);
			rs = pstmt.executeQuery();
			
			ArticlesVO articlesVO = null;
			CategoryVO categoryVO = null;
			
			while(rs.next()){
				articlesVO = new ArticlesVO();
				articlesVO.setArticleId(rs.getInt("ARTCL_ID"));
				articlesVO.setSubject(rs.getString("SBJ"));
				articlesVO.setContent(rs.getString("CONT"));
				categoryVO = articlesVO.getCategoryVO();
				categoryVO.setCategoryId(rs.getInt("CTGR_ID"));
				categoryVO.setCategoryName(rs.getString("CTGR_NM"));
				articlesVO.setCreatDate(rs.getString("NOWDATE"));
			}	
			
			return articlesVO;
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
	@Override
	public void deleteArticle(int articleId) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","ARTCL","artcl");
			StringBuffer query = new StringBuffer();
			query.append(" DELETE ");
			query.append(" FROM		ARTCL ");
			query.append(" WHERE	ARTCL_ID = ? ");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, articleId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
			throw new RuntimeException(e.getMessage(), e);
		}
		finally {
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}

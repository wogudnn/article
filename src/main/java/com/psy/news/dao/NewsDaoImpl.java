package com.psy.news.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.psy.news.vo.CategoryVO;
import com.psy.news.vo.JournalistVO;
import com.psy.news.vo.NewsVO;

public class NewsDaoImpl implements NewsDao {

 @Override
 public List<CategoryVO> getCategoriesOf(int parentCategoryId) {
  
  // 1. 오라클에 접속할 수 있는 드라이버를 불러온다.
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
  } 
  catch (ClassNotFoundException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  
  // 2. Connection, PreparedStatement, ResultSet 객체를 선언한다.
  Connection conn = null; // Oracle에 접속하는 객체
  PreparedStatement pstmt = null; // Oracle에 쿼리를 실행하는 객체
  ResultSet rs = null; // Oracle 쿼리의 결과를 받아오는 객체, Select 일 때만 사용함.
  
  // 3. Oracle에 접속한다.
  try {
   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "NEWS", "NEWS");
   
   // 4. 실행할 쿼리를 준비한다.
   // ? 는 Binding 변수 라고 부른다.
   // ? 에 파라미터를 매핑한다.
   pstmt = conn.prepareStatement(" SELECT CTGR_ID, CTGR_NM, PRNT_CTGR_ID FROM CTGR WHERE PRNT_CTGR_ID = ? ");
   // 5. ? 에 파라미터를 맵핑한다.
   pstmt.setInt(1, parentCategoryId); // 첫 번째 물음표에 parentCategoryId 를 맵핑한다.
   
   // 6. 쿼리를 실행하고, 결과를 받아온다.
   rs = pstmt.executeQuery(); // Select 일 때만 사용함.
   
   // 리턴될 결과
   List<CategoryVO> categories = new ArrayList<CategoryVO>();
   // categories에 들어갈 각각의 카테고리 정보들
   CategoryVO categoryVO = null;
   
   // 7. 결과셋(ResultSet) 의 각각의 Row를 탐색한다.
   // rs.next() 는 행을 하나씩 내려가겠다 라는 의미다.
   // 만약, 쿼리의 결과가 여러개라면 while을 쓰고 하나라면 if를 쓴다.
   while ( rs.next() ) { 
    // categoryVO 를 초기화 시킨다.
    categoryVO = new CategoryVO();
    // Column 들을 하나씩 꺼내서, CategoryVO 에 담는다.
    // 결과셋에서 CTGR_ID 컬럼의 값을 categoryVO 에 저장한다.
    categoryVO.setCategoryId( rs.getInt("CTGR_ID") );
    // 결과셋에서 CTGR_NM 컬럼의 값을 categoryVO 에 저장한다.
    categoryVO.setCategoryName( rs.getString("CTGR_NM") );
    // 결과셋에서 PRNT_CTGR_ID 컬럼의 값을 categoryVO 에 저장한다.
    categoryVO.setParentCategoryId( rs.getInt("PRNT_CTGR_ID") );
    
    // 카테고리 목록에 카테고리를 추가한다.
    categories.add(categoryVO);
   }
   
   // 결과를 반환한다.
   return categories;
  
  } 
  catch (SQLException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  finally {
   // rs, pstmt, conn 을 순서대로 닫는다.
   if ( rs != null ) {
    try {
     rs.close();
    } catch (SQLException e) {}
   }
   if ( pstmt != null ) {
    try {
     pstmt.close();
    } catch (SQLException e) {}
   }
   if ( conn != null ) {
    try {
     conn.close();
    } catch (SQLException e) {}
   }
  }
  
 }

 @Override
 public boolean isThisCategoryLeafNode(int categoryId) {
  return getCategoriesOf(categoryId).size() == 0;
 }

 @Override
 public List<JournalistVO> getJournalists() {
  
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
  } 
  catch (ClassNotFoundException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  
  try {
   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "NEWS", "NEWS");
   pstmt = conn.prepareStatement(" SELECT JRNLST_ID, JRNLST_NM, JRNLST_EMAIL, CMPN FROM JRNLST ");
   rs = pstmt.executeQuery();
   
   List<JournalistVO> journalists = new ArrayList<JournalistVO>();
   JournalistVO journalistVO = null;
   
   while ( rs.next() ) {
    journalistVO = new JournalistVO();
    journalistVO.setJournalistId( rs.getInt("JRNLST_ID") );
    journalistVO.setJournalistName( rs.getString("JRNLST_NM") );
    journalistVO.setJournalistEmail( rs.getString("JRNLST_EMAIL") );
    journalistVO.setCompany( rs.getString("CMPN") );
    
    journalists.add(journalistVO);
   }
   
   return journalists;
  } 
  catch (SQLException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  finally {
   if ( rs != null ) {
    try {
     rs.close();
    } catch (SQLException e) {}
   }
   if ( pstmt != null ) {
    try {
     pstmt.close();
    } catch (SQLException e) {}
   }
   if ( conn != null ) {
    try {
     conn.close();
    } catch (SQLException e) {}
   }
  }
  
 }

 @Override
 public boolean addNewNews(NewsVO newsVO) {
  
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
  } 
  catch (ClassNotFoundException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  
  Connection conn = null;
  PreparedStatement pstmt = null;
  
  try {
   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "NEWS", "NEWS");
   
   StringBuffer query = new StringBuffer();
   query.append(" INSERT INTO NEWS ( ");
   query.append("    NEWS_ID, SBJ, CONT, ");
   query.append("    RCMD_CNT, JRNLST_ID, CTGR_ID, ");
   query.append("    CRT_DT )  ");
   query.append(" VALUES ( NEWS_ID_SEQ.NEXTVAL, ?, ?, 0, ?, ?, SYSDATE) ");
   
   pstmt = conn.prepareStatement(query.toString());
   pstmt.setString(1, newsVO.getSubject());
   pstmt.setString(2, newsVO.getContents());
   pstmt.setInt(3, newsVO.getJournalistId());
   pstmt.setInt(4, newsVO.getCategoryId());
   
   // insert, update, delete 할 때 사용됨.
   int insertCount = pstmt.executeUpdate(); 
   
   return insertCount > 0;
  } 
  catch (SQLException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  finally {
   if ( pstmt != null ) {
    try {
     pstmt.close();
    } catch (SQLException e) {}
   }
   if ( conn != null ) {
    try {
     conn.close();
    } catch (SQLException e) {}
   }
  }
 }

 @Override
 public List<NewsVO> getNewsesOf(int categoryId) {
  
  try {
   Class.forName("oracle.jdbc.driver.OracleDriver");
  } 
  catch (ClassNotFoundException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  
  try {
   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "NEWS", "NEWS");
   
   StringBuffer query = new StringBuffer();
   query.append("SELECT  N.NEWS_ID ");
   query.append("        , N.SBJ ");
   query.append("        , N.CONT ");
   query.append("  	     , N.RCMD_CNT ");
   query.append("        , J.JRNLST_ID ");
   query.append("        , J.JRNLST_NM ");
   query.append("        , J.JRNLST_EMAIL ");
   query.append("        , J.CMPN ");
   query.append("        , G.CTGR_ID ");
   query.append("        , G.CTGR_NM ");
   query.append("        , G.PRNT_CTGR_ID ");
   query.append("        ,  TO_CHAR (N.CRT_DT, 'YYYY-MM-DD HH24:MI:SS') CRT_DT ");
   query.append("FROM    NEWS N ");
   query.append("        , CTGR G ");
   query.append("        , JRNLST J ");
   query.append("WHERE   N.JRNLST_ID = J.JRNLST_ID ");
   query.append("AND     N.CTGR_ID = G.CTGR_ID ");
   query.append("AND     G.CTGR_ID = ? ");
   
   pstmt = conn.prepareStatement(query.toString());
   pstmt.setInt(1, categoryId);
   rs = pstmt.executeQuery();
   
   List<NewsVO> newses = new ArrayList<NewsVO>();
   NewsVO newsVO = null;
   
   JournalistVO journalistVO = null;
   CategoryVO categoryVO = null;
   
   while ( rs.next() ) {
    newsVO = new NewsVO();
    // SELECT NEWS_ID, SBJ, CONT, RCMD_CNT, 
    // JRNLST_ID, CTGR_ID, CRT_DT FROM NEWS
    newsVO.setNewsId( rs.getInt("NEWS_ID") );
    newsVO.setSubject( rs.getString("SBJ") );
    newsVO.setContents( rs.getString("CONT") );
    newsVO.setRecommendCount( rs.getInt("RCMD_CNT") );
    
    
    newsVO.setJournalistId( rs.getInt("JRNLST_ID") );
    journalistVO = newsVO.getJournalistVO();
    journalistVO.setJournalistName(rs.getString("JRNLST_NM"));
    journalistVO.setJournalistEmail(rs.getString("JRNLST_EMAIL"));
    journalistVO.setCompany(rs.getString("CMPN"));
    
    
    newsVO.setCategoryId( rs.getInt("CTGR_ID") );
    categoryVO = newsVO.getCategoryVO();
    categoryVO.setCategoryName(rs.getString("CTGR_NM"));
    categoryVO.setParentCategoryId(rs.getInt("PRNT_CTGR_ID"));
    newsVO.setCreatedDate( rs.getString("CRT_DT") );
    
    newses.add(newsVO);
   }
   
   return newses;
  } 
  catch (SQLException e) {
   throw new RuntimeException(e.getMessage(), e);
  }
  finally {
   if ( rs != null ) {
    try {
     rs.close();
    } catch (SQLException e) {}
   }
   if ( pstmt != null ) {
    try {
     pstmt.close();
    } catch (SQLException e) {}
   }
   if ( conn != null ) {
    try {
     conn.close();
    } catch (SQLException e) {}
   }
  }
  
 }

 @Override
	public NewsVO getNewsAt(int newsId) {

	 try {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
		  } 
		  catch (ClassNotFoundException e) {
		   throw new RuntimeException(e.getMessage(), e);
		  }
		  
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  
		  try {
		   conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "NEWS", "NEWS");
		   
		   StringBuffer query = new StringBuffer();
		   query.append("SELECT  N.NEWS_ID ");
		   query.append("        , N.SBJ ");
		   query.append("        , N.CONT ");
		   query.append("  	     , N.RCMD_CNT ");
		   query.append("        , J.JRNLST_ID ");
		   query.append("        , J.JRNLST_NM ");
		   query.append("        , J.JRNLST_EMAIL ");
		   query.append("        , J.CMPN ");
		   query.append("        , G.CTGR_ID ");
		   query.append("        , G.CTGR_NM ");
		   query.append("        , G.PRNT_CTGR_ID ");
		   query.append("        , N.CRT_DT ");
		   query.append("FROM    NEWS N ");
		   query.append("        , CTGR G ");
		   query.append("        , JRNLST J ");
		   query.append("WHERE   N.JRNLST_ID = J.JRNLST_ID ");
		   query.append("AND     N.CTGR_ID = G.CTGR_ID ");
		   query.append("AND     N.NEWS_ID = ? ");
		   
		   pstmt = conn.prepareStatement(query.toString());
		   pstmt.setInt(1, newsId);
		   rs = pstmt.executeQuery();
		   
		   NewsVO newsVO = null;
		   
		   JournalistVO journalistVO = null;
		   CategoryVO categoryVO = null;
		   
		   if ( rs.next() ) {
		    newsVO = new NewsVO();
		    // SELECT NEWS_ID, SBJ, CONT, RCMD_CNT, 
		    // JRNLST_ID, CTGR_ID, CRT_DT FROM NEWS
		    newsVO.setNewsId( rs.getInt("NEWS_ID") );
		    newsVO.setSubject( rs.getString("SBJ") );
		    newsVO.setContents( rs.getString("CONT") );
		    newsVO.setRecommendCount( rs.getInt("RCMD_CNT") );
		    
		    
		    newsVO.setJournalistId( rs.getInt("JRNLST_ID") );
		    journalistVO = newsVO.getJournalistVO();
		    journalistVO.setJournalistName(rs.getString("JRNLST_NM"));
		    journalistVO.setJournalistEmail(rs.getString("JRNLST_EMAIL"));
		    journalistVO.setCompany(rs.getString("CMPN"));
		    
		    
		    newsVO.setCategoryId( rs.getInt("CTGR_ID") );
		    categoryVO = newsVO.getCategoryVO();
		    categoryVO.setCategoryName(rs.getString("CTGR_NM"));
		    categoryVO.setParentCategoryId(rs.getInt("PRNT_CTGR_ID"));
		    newsVO.setCreatedDate( rs.getString("CRT_DT") );
		    

		   }
		   
		   return newsVO;
		  } 
		  catch (SQLException e) {
		   throw new RuntimeException(e.getMessage(), e);
		  }
		  finally {
		   if ( rs != null ) {
		    try {
		     rs.close();
		    } catch (SQLException e) {}
		   }
		   if ( pstmt != null ) {
		    try {
		     pstmt.close();
		    } catch (SQLException e) {}
		   }
		   if ( conn != null ) {
		    try {
		     conn.close();
		    } catch (SQLException e) {}
		   }
		  }
		  	 
	}
 
 @Override
	public void updateRecommendCount(int newsId) {

	 try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	} 
	 catch (ClassNotFoundException e) {
		throw new RuntimeException(e.getMessage(), e);
	}
	 
	 Connection conn = null;
	 PreparedStatement pstmt = null;
	 
	 try {
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","NEWS","NEWS");
		StringBuffer query = new StringBuffer();
		query.append(" UPDATE   NEWS ");
		query.append(" SET       RCMD_CNT = RCMD_CNT + 1 ");
		query.append(" WHERE    NEWS_ID = ? ");
		
		pstmt = conn.prepareStatement(query.toString());
		pstmt.setInt(1, newsId);
		pstmt.executeUpdate();
	} 
	 catch (SQLException e) {
		throw new RuntimeException(e.getMessage(), e);
	}
	
	finally{
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
	public void deleteNews(int newsId) {
		
	 try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	} catch (ClassNotFoundException e) {
		throw new RuntimeException(e.getMessage(), e);
	}
	 
	 Connection conn = null;
	 PreparedStatement pstmt = null;
	 
	 try {
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","NEWS","NEWS");
		
		StringBuffer query = new StringBuffer();
		query.append(" DELETE ");
		query.append(" FROM		NEWS ");
		query.append(" WHERE	NEWS_ID = ? ");
		
		pstmt = conn.prepareStatement(query.toString());
		pstmt.setInt(1, newsId);
		pstmt.executeUpdate();
		
		
	} catch (SQLException e) {
		throw new RuntimeException(e.getMessage(), e);
	}
	 finally {
		 if( pstmt !=null) {
			 try {
				pstmt.close();
			} catch (SQLException e) {}
		 }
		 if( conn !=null) {
			 try {
				conn.close();
			} catch (SQLException e) {}
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
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","NEWS","NEWS");
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
}







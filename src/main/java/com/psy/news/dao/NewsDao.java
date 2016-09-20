package com.psy.news.dao;

import java.util.List;

import com.psy.news.vo.CategoryVO;
import com.psy.news.vo.JournalistVO;
import com.psy.news.vo.NewsVO;

public interface NewsDao {

	/**
	 * 카테고리 가져오기
	 * @param parentCategoryId
	 * @return
	 */
	public List<CategoryVO> getCategoriesOf(int parentCategoryId);
	/**
	 * 카테고리 리프노드인지 확인하기
	 * @param categoryId
	 * @return
	 */
	public boolean isThisCategoryLeafNode(int categoryId);
	/**
	 * 모든 기자 가져오기
	 * @return
	 */
	public List<JournalistVO> getJournalists();
	/**
	 * 새로운 기사를 등록한다.
	 * 카테고리 아이디와 기자 아이디를 포함한다.
	 * @param newsVO 새로 등록될 기사의 정보
	 * @return
	 */
	public boolean addNewNews(NewsVO newsVO);
	
	
	/**
	 * 지정한 카테고리에 등록된 모든 기사를 가져온다.
	 * @param categoryId 지정 카테고리 아이디
	 * @return
	 */
	public List<NewsVO> getNewsesOf();
	/**
	 * 기사하나를 가져온다.
	 * @param newsId
	 * @return
	 */
	public NewsVO getNewsAt(int newsId);
	
	/**
	 * 기사의 추천수를 1 증가시킨다.
	 * @param newsId
	 */
	public void updateRecommendCount(int newsId);
	
	
	public void deleteNews(int newsId);
}

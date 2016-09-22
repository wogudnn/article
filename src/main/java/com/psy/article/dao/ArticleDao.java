package com.psy.article.dao;

import java.util.List;

import com.psy.article.vo.ArticlesVO;
import com.psy.article.vo.CategoryVO;

public interface ArticleDao {

	public List<ArticlesVO> getArticlesOf(int categoryId);
	
	public List<CategoryVO> getCategoriesOf(int parentCategoryId);
	
	public boolean isCategoryLeafNode(int categoryId);
	
	public void addArticle(ArticlesVO articlesVO);
	
	public List<CategoryVO> getCategories();
	
	public ArticlesVO getArticleAt(int articleId);
	
	public void deleteArticle(int articleId);
	
}

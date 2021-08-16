package fr.eni.encheres.bll;

import java.util.List;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.jdbc.ArticleDAOJdbcImpl;

public class ArticleManager {

  private static ArticleManager instance = null;

  public ArticleManager() {
    articleDAO = new ArticleDAOJdbcImpl();
  }

  public synchronized static ArticleManager getInstance() {
    if (instance == null) {
      instance = new ArticleManager();
    }
    return instance;
  }

  // Ref Dal
  private ArticleDAO articleDAO;

  public List<Article> getArticlesInProgress() {
    List<Article> listArticles = null;
    try {
      listArticles = articleDAO.getArticlesInProgress();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listArticles;
  }

  public List<Article> getPrice(List<Article> ls) {
    List<Article> listArticles = null;
    try {
      listArticles = articleDAO.getPrice(ls);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listArticles;
  }

  public List<Article> FilterNameAll(String search) {
    List<Article> listArticles = null;
    try {
      listArticles = articleDAO.FilterNameAll(search);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listArticles;
  }

  public List<Article> FilterNameCategory(String search, String category) {
    List<Article> listArticles = null;
    try {
      listArticles = articleDAO.FilterNameCategory(search, category);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listArticles;
  }

  public List<Article> FilterCategory(String category) {
    List<Article> listArticles = null;
    try {
      listArticles = articleDAO.FilterCategory(category);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listArticles;
     } 
     
	public int creerArticle(Article article) throws BLLException {
		
		try {
			return articleDAO.insert(article);
		} catch (DALException exception) {
			throw new BLLException(new Exception("L'insertion de l'article à échoué."));
		}
	}
}
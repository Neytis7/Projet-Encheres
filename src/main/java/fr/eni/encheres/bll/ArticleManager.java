package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDaoImpl;
import fr.eni.encheres.dal.DALException;

public class ArticleManager {

	private ArticleDaoImpl articleDAO = new ArticleDaoImpl();
	
	public ArticleDaoImpl getArticleDAO() {
		return articleDAO;
	}
	
	public int creerArticle(Article article) throws BLLException {
		
		try {
			return articleDAO.insert(article);
		} catch (DALException exception) {
			throw new BLLException(new Exception("L'insertion de l'article à échoué."));
		}
	}
}

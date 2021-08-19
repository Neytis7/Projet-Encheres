package fr.eni.encheres.bll;

import java.util.List;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ArticleDaoImpl;
import fr.eni.encheres.dal.DALException;

public class ArticleManager {

	private static ArticleManager instance = null;

	public ArticleManager() {
		articleDAO = new ArticleDaoImpl();
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

	public int createArticle(Article article) throws BLLException {
		try {
			return articleDAO.insert(article);
		} catch (DALException exception) {
			throw new BLLException(new Exception("L'insertion de l'article à échoué."));
		}
	}

	public List<Article> FilterConnectedSearchAuctionInProgress(String search, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchAuctionInProgress(search, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchCategoryAuctionInProgress(String search, String category,
			int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchCategoryAuctionInProgress(search, category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterCategoryAuctionInProgress(String category, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterCategoryAuctionInProgress(category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterMyAuctionInProgress(int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterMyAuctionInProgress(idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchMySellInProgress(String search, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchMySellInProgress(search, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchCategoryMySellInProgress(String search, String category,
			int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchCategoryMySellInProgress(search, category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterCategoryMySellInProgress(String category, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterCategoryMySellInProgress(category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterMySellInProgress(int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterMySellInProgress(idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchMySellNotBegin(String search, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchMySellNotBegin(search, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchCategoryMySellNotBegin(String search, String category,
			int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchCategoryMySellNotBegin(search, category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterCategoryMySellNotBegin(String category, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterCategoryMySellNotBegin(category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterMySellNotBegin(int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterMySellNotBegin(idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchMySellTerminate(String search, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchMySellTerminate(search, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchCategoryMySellTerminate(String search, String category,
			int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchCategoryMySellTerminate(search, category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterCategoryMySellTerminate(String category, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterCategoryMySellTerminate(category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterMySellTerminate(int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterMySellTerminate(idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchAuctionWin(String search, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchAuctionWin(search, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterConnectedSearchCategoryAuctionWin(String search, String category, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterConnectedSearchCategoryAuctionWin(search, category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterCategoryAuctionWin(String category, int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterCategoryAuctionWin(category, idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public List<Article> FilterMyAuctionWin(int idUserConnected) {
		List<Article> listArticles = null;
		try {
			listArticles = articleDAO.FilterMyAuctionWin(idUserConnected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listArticles;
	}

	public Article getArticleById(int idArticle) throws BLLException {
		isNotNull(idArticle);
		try {
			return articleDAO.getArticleById(idArticle);
		} catch (DALException dalException) {
			throw new BLLException(new Exception("La récupération de l'article a échoué."));
		}
	}

	public int finishSellArticle(int idArticle, int sellPrice) throws BLLException {
		try {
			return articleDAO.finshSellArticle(idArticle, sellPrice);
		} catch (DALException dalException) {
			throw new BLLException(new Exception("La récupération de l'article a échoué."));
		}
	}

	private void isNotNull(int idArticle) throws fr.eni.encheres.bll.BLLException {
		if (idArticle == 0) {
			throw new BLLException(new Exception("L'id article passé en param�tre est égal à 0."));
		}
	}
}

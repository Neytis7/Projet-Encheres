package fr.eni.encheres.dal;

import java.util.List;
import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

  List<Article> getArticlesInProgress() throws Exception;

  List<Article> getPrice(List<Article> listArticles) throws Exception;

  List<Article> FilterNameAll(String search) throws Exception;

  List<Article> FilterNameCategory(String search, String category) throws Exception;

  List<Article> FilterCategory(String category) throws Exception;

  int insert(Article article) throws DALException;

  List<Article> FilterConnectedSearchAuctionInProgress(String search, int idUserConnected)
      throws Exception;

  List<Article> FilterConnectedSearchCategoryAuctionInProgress(String search, String category,
      int idUserConnected) throws Exception;

  List<Article> FilterCategoryAuctionInProgress(String category, int idUserConnected)
      throws Exception;

  List<Article> FilterMyAuctionInProgress(int idUserConnected) throws Exception;

  List<Article> FilterConnectedSearchMySellInProgress(String search, int idUserConnected)
      throws Exception;

  List<Article> FilterConnectedSearchCategoryMySellInProgress(String search, String category,
      int idUserConnected) throws Exception;

  List<Article> FilterCategoryMySellInProgress(String category, int idUserConnected)
      throws Exception;

  List<Article> FilterMySellInProgress(int idUserConnected) throws Exception;

  List<Article> FilterConnectedSearchMySellNotBegin(String search, int idUserConnected)
      throws Exception;

  List<Article> FilterConnectedSearchCategoryMySellNotBegin(String search, String category,
      int idUserConnected) throws Exception;

  List<Article> FilterCategoryMySellNotBegin(String category, int idUserConnected) throws Exception;

  List<Article> FilterMySellNotBegin(int idUserConnected) throws Exception;

  List<Article> FilterConnectedSearchMySellTerminate(String search, int idUserConnected)
      throws Exception;

  List<Article> FilterConnectedSearchCategoryMySellTerminate(String search, String category,
      int idUserConnected) throws Exception;

  List<Article> FilterCategoryMySellTerminate(String category, int idUserConnected)
      throws Exception;

  List<Article> FilterMySellTerminate(int idUserConnected) throws Exception;

  List<Article> FilterConnectedSearchAuctionWin(String search, int idUserConnected)
      throws Exception;

  List<Article> FilterConnectedSearchCategoryAuctionWin(String search, String category,
      int idUserConnected) throws Exception;

  List<Article> FilterCategoryAuctionWin(String category, int idUserConnected) throws Exception;

  List<Article> FilterMyAuctionWin(int idUserConnected) throws Exception;

}

package fr.eni.encheres.dal;

import java.util.List;
import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

  List<Article> getArticlesInProgress() throws Exception;

  List<Article> getPrice(List<Article> listArticles) throws Exception;

  List<Article> FilterNameAll(String search) throws Exception;

  List<Article> FilterNameCategory(String search, String category) throws Exception;

  List<Article> FilterCategory(String category) throws Exception;

}

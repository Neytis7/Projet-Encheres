package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.ArticleDAO;

public class ArticleDAOJdbcImpl implements ArticleDAO {

  private static final String VIEW_ARTICLES_IN_PROGRESS =
      "USE DB_ENCHERES SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE est_supprime = 0 AND date_debut_encheres<=GETDATE() AND GETDATE()<date_fin_encheres";

  private static final String CHECK_MAX_MONTANT =
      "USE DB_ENCHERES SELECT Max(montant_enchere) AS me FROM ARTICLES_VENDUS INNER JOIN ENCHERES ON ARTICLES_VENDUS.no_article = ENCHERES.no_article where ENCHERES.no_article = ";
  private static final String FILTER_NAME_ALL =
      "USE DB_ENCHERES SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE est_supprime = 0 AND date_debut_encheres<=GETDATE() AND GETDATE()<date_fin_encheres AND nom_article LIKE ";
  private static final String FILTER_NAME_CATEGORY =
      "USE DB_ENCHERES SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE est_supprime = 0 AND date_debut_encheres<=GETDATE() AND GETDATE()<date_fin_encheres AND nom_article LIKE ";
  private static final String FILTER_NONE_CATEGORY =
      "USE DB_ENCHERES SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE est_supprime = 0 AND date_debut_encheres<=GETDATE() AND GETDATE()<date_fin_encheres AND libelle = ";


  @Override
  public List<Article> getArticlesInProgress() throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    ResultSet res = sts.executeQuery(VIEW_ARTICLES_IN_PROGRESS);
    List<Article> listArticles = new ArrayList<Article>();
    while (res.next()) {
      // Article
      int no_article = res.getInt("no_article");
      String name_article = res.getString("nom_article");
      String description = res.getString("description");
      Date start_date = res.getDate("date_debut_encheres");
      Date end_date = res.getDate("date_fin_encheres");
      int initial_price = res.getInt("prix_initial");
      int sell_price = res.getInt("prix_vente");
      boolean isDelete = res.getBoolean("est_supprime");
      // Category
      int no_category = res.getInt("no_categorie");
      String libelle = res.getString("libelle");
      Category category = new Category(no_category, libelle);
      // User
      int no_user = res.getInt("no_utilisateur");
      String pseudo = res.getString("pseudo");
      String name = res.getString("nom");
      String first_name = res.getString("prenom");
      String mail = res.getString("email");
      String phone_number = res.getString("telephone");
      String address = res.getString("rue");
      String zip_code = res.getString("code_postal");
      String city = res.getString("ville");
      String password = res.getString("mot_de_passe");
      int credit = res.getInt("credit");
      boolean isAdministrator = res.getBoolean("administrateur");
      User user = new User(no_user, pseudo, name, first_name, mail, phone_number, address, zip_code,
          city, password, credit, isAdministrator);

      Article article = new Article(no_article, name_article, description, start_date, end_date,
          initial_price, sell_price, user, category, isDelete);

      listArticles.add(article);
    }
    return listArticles;
  }


  @Override
  public List<Article> getPrice(List<Article> listArticles) throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    for (int i = 0; i <= listArticles.size() - 1; i++) {
      String finalSQL = "'" + listArticles.get(i).getNo_article() + "'";
      ResultSet res = sts.executeQuery(CHECK_MAX_MONTANT + finalSQL);
      while (res.next()) {
        if (res.getInt(1) != 0) {
          listArticles.get(i).setPrice_auction(res.getInt(1));
        }
      }
    }
    return listArticles;
  }

  @Override
  public List<Article> FilterNameAll(String search) throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    String finalArticle = "'" + search + "%'";
    ResultSet res = sts.executeQuery(FILTER_NAME_ALL + finalArticle);
    List<Article> listArticles = new ArrayList<Article>();
    while (res.next()) {
      // Article
      int no_article = res.getInt("no_article");
      String name_article = res.getString("nom_article");
      String description = res.getString("description");
      Date start_date = res.getDate("date_debut_encheres");
      Date end_date = res.getDate("date_fin_encheres");
      int initial_price = res.getInt("prix_initial");
      int sell_price = res.getInt("prix_vente");
      boolean isDelete = res.getBoolean("est_supprime");
      // Category
      int no_category = res.getInt("no_categorie");
      String libelle = res.getString("libelle");
      Category category = new Category(no_category, libelle);
      // User
      int no_user = res.getInt("no_utilisateur");
      String pseudo = res.getString("pseudo");
      String name = res.getString("nom");
      String first_name = res.getString("prenom");
      String mail = res.getString("email");
      String phone_number = res.getString("telephone");
      String address = res.getString("rue");
      String zip_code = res.getString("code_postal");
      String city = res.getString("ville");
      String password = res.getString("mot_de_passe");
      int credit = res.getInt("credit");
      boolean isAdministrator = res.getBoolean("administrateur");
      User user = new User(no_user, pseudo, name, first_name, mail, phone_number, address, zip_code,
          city, password, credit, isAdministrator);

      Article article = new Article(no_article, name_article, description, start_date, end_date,
          initial_price, sell_price, user, category, isDelete);

      listArticles.add(article);

    }
    return listArticles;
  }


  @Override
  public List<Article> FilterNameCategory(String search, String category) throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    String finalArticle = "'" + search + "%' ";
    String finalCategorie = "'" + category + "'";
    String sqlFinal = finalArticle + "AND libelle = " + finalCategorie;
    ResultSet res = sts.executeQuery(FILTER_NAME_CATEGORY + sqlFinal);
    List<Article> listArticles = new ArrayList<Article>();
    while (res.next()) {
      // Article
      int no_article = res.getInt("no_article");
      String name_article = res.getString("nom_article");
      String description = res.getString("description");
      Date start_date = res.getDate("date_debut_encheres");
      Date end_date = res.getDate("date_fin_encheres");
      int initial_price = res.getInt("prix_initial");
      int sell_price = res.getInt("prix_vente");
      boolean isDelete = res.getBoolean("est_supprime");
      // Category
      int no_category = res.getInt("no_categorie");
      String libelle = res.getString("libelle");
      Category categoryBis = new Category(no_category, libelle);
      // User
      int no_user = res.getInt("no_utilisateur");
      String pseudo = res.getString("pseudo");
      String name = res.getString("nom");
      String first_name = res.getString("prenom");
      String mail = res.getString("email");
      String phone_number = res.getString("telephone");
      String address = res.getString("rue");
      String zip_code = res.getString("code_postal");
      String city = res.getString("ville");
      String password = res.getString("mot_de_passe");
      int credit = res.getInt("credit");
      boolean isAdministrator = res.getBoolean("administrateur");
      User user = new User(no_user, pseudo, name, first_name, mail, phone_number, address, zip_code,
          city, password, credit, isAdministrator);

      Article article = new Article(no_article, name_article, description, start_date, end_date,
          initial_price, sell_price, user, categoryBis, isDelete);

      listArticles.add(article);

    }
    return listArticles;
  }


  @Override
  public List<Article> FilterCategory(String category) throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    String finalCategorie = "'" + category + "'";
    ResultSet res = sts.executeQuery(FILTER_NONE_CATEGORY + finalCategorie);
    List<Article> listArticles = new ArrayList<Article>();
    while (res.next()) {// Parcour ligne par ligne
      // Article
      int no_article = res.getInt("no_article");
      String name_article = res.getString("nom_article");
      String description = res.getString("description");
      Date start_date = res.getDate("date_debut_encheres");
      Date end_date = res.getDate("date_fin_encheres");
      int initial_price = res.getInt("prix_initial");
      int sell_price = res.getInt("prix_vente");
      boolean isDelete = res.getBoolean("est_supprime");
      // Category
      int no_category = res.getInt("no_categorie");
      String libelle = res.getString("libelle");
      Category categoryBis = new Category(no_category, libelle);
      // User
      int no_user = res.getInt("no_utilisateur");
      String pseudo = res.getString("pseudo");
      String name = res.getString("nom");
      String first_name = res.getString("prenom");
      String mail = res.getString("email");
      String phone_number = res.getString("telephone");
      String address = res.getString("rue");
      String zip_code = res.getString("code_postal");
      String city = res.getString("ville");
      String password = res.getString("mot_de_passe");
      int credit = res.getInt("credit");
      boolean isAdministrator = res.getBoolean("administrateur");
      User user = new User(no_user, pseudo, name, first_name, mail, phone_number, address, zip_code,
          city, password, credit, isAdministrator);

      Article article = new Article(no_article, name_article, description, start_date, end_date,
          initial_price, sell_price, user, categoryBis, isDelete);

      listArticles.add(article);

    }
    return listArticles;
  }

}

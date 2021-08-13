package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.dal.EncheresDAO;

public class EncheresDAOJdbcImpl implements EncheresDAO {
  private static final String VISUALISER_ENCHERES =
      "USE DB_ENCHERES SELECT * FROM ENCHERES INNER JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur";
  private static final String VISUALISER_ENCHERES_NAME_ALL =
      "USE DB_ENCHERES SELECT * FROM ENCHERES INNER JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur where nom_article LIKE ";

  private static final String VISUALISER_ENCHERES_NAME_CATEGORIE =
      "USE DB_ENCHERES SELECT * FROM ENCHERES INNER JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie  where nom_article LIKE ";
  private static final String VISUALISER_ENCHERES_CATEGORIE =
      "USE DB_ENCHERES SELECT * FROM ENCHERES INNER JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie  where libelle = ";

  @Override
  public List<Encheres> visualiserEncheres() throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    ResultSet res = sts.executeQuery(VISUALISER_ENCHERES);
    List<Encheres> listEncheres = new ArrayList<Encheres>();
    while (res.next()) {// Parcour ligne par ligne
      String pseudo = res.getString("pseudo");
      String nameArticle = res.getString("nom_article");
      Date endDate = res.getDate("date_fin_encheres");
      Integer price = res.getInt("montant_enchere");
      Encheres encheres = new Encheres(pseudo, nameArticle, endDate, price);
      listEncheres.add(encheres);
    }
    return listEncheres;
  }

  @Override
  public List<Encheres> visualiserEncheresNameAll(String article) throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    String finalArticle = "'" + article + "%'";

    ResultSet res = sts.executeQuery(VISUALISER_ENCHERES_NAME_ALL + finalArticle);
    List<Encheres> listEncheres = new ArrayList<Encheres>();
    while (res.next()) {// Parcour ligne par ligne
      String pseudo = res.getString("pseudo");
      String nameArticle = res.getString("nom_article");
      Date endDate = res.getDate("date_fin_encheres");
      Integer price = res.getInt("montant_enchere");
      Encheres encheres = new Encheres(pseudo, nameArticle, endDate, price);
      listEncheres.add(encheres);
    }
    return listEncheres;
  }

  @Override
  public List<Encheres> visualiserEncheresNameCategorie(String article, String categorie)
      throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    String finalArticle = "'" + article + "%' ";
    String finalCategorie = "'" + categorie + "'";
    String sqlFinal = finalArticle + "AND libelle = " + finalCategorie;
    ResultSet res = sts.executeQuery(VISUALISER_ENCHERES_NAME_CATEGORIE + sqlFinal);
    List<Encheres> listEncheres = new ArrayList<Encheres>();
    while (res.next()) {// Parcour ligne par ligne
      String pseudo = res.getString("pseudo");
      String nameArticle = res.getString("nom_article");
      Date endDate = res.getDate("date_fin_encheres");
      Integer price = res.getInt("montant_enchere");
      Encheres encheres = new Encheres(pseudo, nameArticle, endDate, price);
      listEncheres.add(encheres);
    }
    return listEncheres;
  }

  @Override
  public List<Encheres> visualiserEncheresCategorie(String categorie) throws Exception {
    Connection cnx = ConnexionProvider.getConnection();
    Statement sts = cnx.createStatement();
    String finalCategorie = "'" + categorie + "'";
    ResultSet res = sts.executeQuery(VISUALISER_ENCHERES_CATEGORIE + finalCategorie);
    List<Encheres> listEncheres = new ArrayList<Encheres>();
    while (res.next()) {// Parcour ligne par ligne
      String pseudo = res.getString("pseudo");
      String nameArticle = res.getString("nom_article");
      Date endDate = res.getDate("date_fin_encheres");
      Integer price = res.getInt("montant_enchere");
      Encheres encheres = new Encheres(pseudo, nameArticle, endDate, price);
      listEncheres.add(encheres);
    }
    return listEncheres;
  }

}

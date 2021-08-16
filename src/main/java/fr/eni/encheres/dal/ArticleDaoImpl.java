package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class ArticleDaoImpl {
	
	private static final String INSERT_ARTICLE = "USE DB_ENCHERES INSERT INTO ARTICLES_VENDUS"
			+ "(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, "
			+ "no_utilisateur, no_categorie, est_supprime ) VALUES (?,?,?,?,?,?,?,?)";

	public int insert(Article article) throws DALException {

		int result = 0;
		int idArticle = 0;
		
		try {
			
			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS);	
			ps.setString(1, article.getName_article());
			ps.setString(2, article.getDescription_article());
			ps.setDate(3, Date.valueOf(article.getDate_start_enchere()));
			ps.setDate(4, Date.valueOf(article.getDate_end_enchere()));
			ps.setInt(5, article.getInit_price());
			ps.setInt(6, article.getSeiler().getNo_user());
			ps.setInt(7, article.getCategory().getNo_category());
			ps.setBoolean(8, false);
			
			result = ps.executeUpdate();		
			
			if (result > 0) {
				try (ResultSet generatedKey = ps.getGeneratedKeys()) {
					if(generatedKey.next()) {
						article.setNo_article(generatedKey.getInt(1));	
						idArticle = article.getNo_article();
					}
		        }catch (SQLException e) {
		        	throw new DALException(new Exception("La génération de l'id de l'article a échoué"));
		        }
			}
				
		}catch(SQLException exception) {
			throw new DALException(new Exception("[Erreur] impossible de créer l'article."));
		}
		
		return idArticle;	
	}
}

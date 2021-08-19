package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class RetraitDaoImpl implements RetraitDAO{
	
	private static final String INSERT_RETRAIT = "USE DB_ENCHERES INSERT INTO RETRAITS "
			+ "(no_article, rue, code_postal, ville ) VALUES (?,?,?,?)";
	
	private static final String GET_RETRAIT_BY_ID_ARTICLE = "USE DB_ENCHERES SELECT * FROM RETRAITS WHERE no_article = (?)";
	
	@Override
	public int insert(Retrait retrait) throws DALException {
		
		int result = 0;
		
		try (Connection connection = ConnexionProvider.getConnection()){
			
			PreparedStatement ps = connection.prepareStatement(INSERT_RETRAIT);	
			ps.setInt(1, retrait.getNo_article());
			ps.setString(2, retrait.getAddress());
			ps.setString(3, retrait.getCode_postal());
			ps.setString(4, retrait.getCity());			
			result = ps.executeUpdate();		
				
		}catch(SQLException exception) {
			throw new DALException(new Exception("[Erreur] impossible de créer le retrait."));
		}
		
		return result;	
	}
	
	@Override
	public Retrait getRetraitByIdArticle(int idArticle) throws DALException {
		Retrait retrait = null;
		ResultSet resultSet;
		try {
			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_RETRAIT_BY_ID_ARTICLE);
			ps.setInt(1, idArticle);
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				retrait = new Retrait(resultSet.getInt("no_article")
								, resultSet.getString("rue")
								, resultSet.getString("ville")
								, resultSet.getString("code_postal"));
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			throw new DALException(new Exception("La récupération du point de retrait à échoué"));
		}
		
		return retrait;
	}
}

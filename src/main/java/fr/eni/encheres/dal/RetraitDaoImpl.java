package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class RetraitDaoImpl {
	
	private static final String INSERT_RETRAIT = "USE DB_ENCHERES INSERT INTO RETRAITS "
			+ "(no_article, rue, code_postal, ville ) VALUES (?,?,?,?)";
	
	public int insert(Retrait retrait) throws DALException {
		
		int result = 0;
		
		try {
			
			Connection connection = ConnexionProvider.getConnection();
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
}

package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class UserDaoImpl {
	private static final String SELECT_USER_ID = "USE DB_ENCHERES SELECT * FROM UTILISATEURS WHERE no_utilisateur = (?) ";
	
	
	
	public User selectUserById(int idUser) throws DALException {
		User user= null;
		try {
			Connection connexion = ConnexionProvider.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(SELECT_USER_ID);	
			preparedStatement.setInt(1,idUser);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				user = new User(resultSet.getInt("no_utilisateur"),
								resultSet.getString("pseudo"),	
								resultSet.getString("nom"),	
								resultSet.getString("prenom"),	
								resultSet.getString("email"),	
								resultSet.getString("telephone"),	
								resultSet.getString("rue"),	
								resultSet.getString("code_postal"),	
								resultSet.getString("ville"),	
								resultSet.getString("mot_de_passe"),	
								resultSet.getInt("credit"),	
								resultSet.getByte("administrateur") != 0);
			}
			System.out.println(user.toString());
			return user;
		}catch(SQLException exception) {
			throw new DALException(new Exception("Erreur lors de récupération de l'utilisateur " + idUser));
		}
		
	}
}

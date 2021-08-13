package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class UserDaoImpl {

	private static final String INSERT_USER = "USE DB_ENCHERES INSERT INTO UTILISATEURS "
			+ "(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_USER_ID = "USE DB_ENCHERES SELECT * FROM UTILISATEURS WHERE no_utilisateur = (?) ";
    
	public boolean insert(User user) throws DALException {
		
		int result = 0;
		boolean success = false;
		
		try {
			
			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);	
			ps.setString(1, user.getPseudo());
			ps.setString(2, user.getName());
			ps.setString(3, user.getFirst_name());
			ps.setString(4, user.getMail());
			ps.setString(5, user.getPhone_number());
			ps.setString(6, user.getAddress());
			ps.setString(7,user.getZip_code());
			ps.setString(8, user.getCity());
			ps.setString(9, user.getPassword());
			ps.setInt(10, 0);
			ps.setBoolean(11, false);
			result = ps.executeUpdate();		
			
			if (result > 0) {
				try (ResultSet generatedKey = ps.getGeneratedKeys()) {
					if(generatedKey.next()) {
						user.setNo_user(generatedKey.getInt(1));	
						success = true;
					}
		        }catch (SQLException e) {
		        	throw new DALException(new Exception("La génération de l'id de l'utilisateur a échoué"));
		        }
			}
				
		}catch(Exception exception) {
			throw new DALException(new Exception("[Erreur] impossible de créer l'utilisateur."));
		}
		
		return success;	
	}
    
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
			return user;
		}catch(SQLException exception) {
			throw new DALException(new Exception("An error occured while get user with id " + idUser));
		}
	}
}

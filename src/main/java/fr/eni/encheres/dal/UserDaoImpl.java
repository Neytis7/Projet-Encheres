package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class UserDaoImpl {

	private static final String INSERT_USER = "USE DB_ENCHERES INSERT INTO UTILISATEURS "
			+ "(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	private static final String SELECT_USER_ID = "USE DB_ENCHERES SELECT * FROM UTILISATEURS WHERE no_utilisateur = (?) ";
	
	private static final String UPDATE_USER_BY_ID = "USE DB_ENCHERES UPDATE UTILISATEURS "
													+ " SET pseudo = (?),"
													+ " nom = (?),"
													+ " prenom = (?),"
													+ " email = (?),"
													+ " telephone = (?),"
													+ " rue = (?),"
													+ " code_postal = (?),"
													+ " ville = (?),"
													+ " token = (?),"
													+ " passwordTokenDate = (?),"
													+ " mot_de_passe = (?)"
													+ " WHERE no_utilisateur = (?)";
    
	private static final String SELECT_USER_BY_LOGIN = "USE DB_ENCHERES SELECT * FROM UTILISATEURS WHERE pseudo = (?) or email = (?)";
	
	private static final String SELECT_ALL_USERS = "USE DB_ENCHERES SELECT pseudo, email FROM UTILISATEURS";
	
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
				
		}catch(SQLException exception) {
			throw new DALException(new Exception("[Erreur] impossible de créer l'utilisateur."));
		}
		
		return success;	
	}

	public User getUserByLogin(String loginUser) throws DALException {

		ResultSet result;
		User user = null;
		try {
			
			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_LOGIN);	
			ps.setString(1, loginUser);
			ps.setString(2, loginUser);
			result = ps.executeQuery();
			
			if(result.next()) {
				LocalDate tokenDatePassword = null; 
				if(result.getDate("passwordTokenDate") != null) {
					tokenDatePassword = result.getDate("passwordTokenDate").toLocalDate();
				}
				
				user = new User(
						result.getInt("no_utilisateur"),
						result.getString("pseudo"),
						result.getString("nom"),
						result.getString("prenom"),
						result.getString("email"),
						result.getString("telephone"),
						result.getString("rue"),
						result.getString("code_postal"),
						result.getString("ville"),
						result.getString("mot_de_passe"),
						result.getInt("credit"),
						result.getByte("administrateur") != 0,
						result.getString("token"),
						tokenDatePassword						
				);
			}
		}catch(SQLException exception) {
			throw new DALException(new Exception("[Erreur] impossible de récuperer l'utilisateur avec le login :" + loginUser));
		}
		return user;	
	}

	public ArrayList<User> selectAllUsersPseudoAndMail() throws DALException {

		ResultSet result;
		ArrayList<User> allUsers = new ArrayList<>();
		
		try {
			
			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS);	
			result = ps.executeQuery();
			
			while(result.next()) {
				
				allUsers.add(new User(result.getString("pseudo"),result.getString("email")));
			}
		}catch(SQLException exception) {
			
			throw new DALException(new Exception("[erreur] Récupération impossible de la liste des utilisateurs"));
		}
		
		return allUsers;
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
			}else {
				throw new DALException(new Exception("L'utilisateur n'existe pas"));
			}
				
		}catch(SQLException exception) {
			throw new DALException(new Exception("An error occured while get user with id " + idUser));
		}
		return user;
	}
    
    public boolean updateUserByID(User userToUpdate) throws DALException {
    	boolean success = false;
    	Date dateTokenPassword = null;
    	
    	if(userToUpdate.getTokenPasswordDate() != null) {
    		dateTokenPassword = Date.valueOf(userToUpdate.getTokenPasswordDate());
    	}
    	
    	try {
			Connection connexion = ConnexionProvider.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_USER_BY_ID);
			preparedStatement.setString(1, userToUpdate.getPseudo());
			preparedStatement.setString(2, userToUpdate.getName());
			preparedStatement.setString(3, userToUpdate.getFirst_name());
			preparedStatement.setString(4, userToUpdate.getMail());
			preparedStatement.setString(5, userToUpdate.getPhone_number());
			preparedStatement.setString(6, userToUpdate.getAddress());
			preparedStatement.setString(7, userToUpdate.getZip_code());
			preparedStatement.setString(8, userToUpdate.getCity());
			preparedStatement.setString(9, userToUpdate.getTokenPassword());
			preparedStatement.setDate(10, dateTokenPassword);
			preparedStatement.setString(11, userToUpdate.getPassword());
			preparedStatement.setInt(12, userToUpdate.getNo_user());
		
			int result = preparedStatement.executeUpdate();
			
			if (result > 0) {
				success = false;
			} else {
				throw new DALException(new Exception("An error occured while update user with id " + userToUpdate.getNo_user()));
			}
			
    	}catch(SQLException exception) {
    		exception.printStackTrace();
			throw new DALException(new Exception("An error occured while update user with id " + userToUpdate.getNo_user()));
		}
    	return success;
    }
}

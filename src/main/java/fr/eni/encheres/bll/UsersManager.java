package fr.eni.encheres.bll;

import java.util.ArrayList;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.UserDaoImpl;

public class UsersManager {

	private UserDaoImpl usersDAO = new UserDaoImpl();
    
    public UserDaoImpl getUserDAO() {
		return usersDAO;
	}

	public void setUserDAO(UserDaoImpl userDAO) {
		this.usersDAO = userDAO;
	}
	
	public User getUserById(int idUser) throws BLLException {
		isNotNull(idUser);
		try {
			return usersDAO.selectUserById(idUser);
		} catch (DALException dalException) {
			throw new BLLException(new Exception("La r�cup�ration de l'utilisateur � �chou�e."));
		}
	}
    
	public ArrayList<String> signUpUser(User user) throws BLLException {
	
		isNotNull(user);
		ArrayList<User> allUsers = new ArrayList<>();
		ArrayList<String> errors = new ArrayList<>();

		try {
			allUsers = usersDAO.selectAllUsersPseudoAndMail();
			
			for(User aUser : allUsers) {
				if(aUser.getPseudo().trim().equals(user.getPseudo())) {
					errors.add("Ce pseudo est déjà utilisé par une autre personne");
					break;
				}
				
				if(aUser.getMail().trim().equals(user.getMail())) {
					errors.add("Cet email est déjà utilisé par une autre personne");
					break;
				}
			}
			
			if(errors.size() == 0) {
				usersDAO.insert(user);
			}
			
		} catch (DALException e) {
			throw new BLLException(new Exception("L'inscription a échoué"));
		}
		return errors;		
	}
	
	public ArrayList<Object> signInUser(String loginUser, String password ) throws BLLException {
		
		ArrayList<Object> array = new ArrayList<>();
		User user = null;
		boolean access = false;

		try {
			user = usersDAO.getUserByLogin(loginUser);
		 
		 	if(user != null) {
		 		if(password.trim().equals(user.getPassword()) && 
		 				(loginUser.trim().equals(user.getPseudo()) || loginUser.trim().equals(user.getMail()))) {
		 			
		 			access = true;
		 			array.add(user);
		 			array.add(access);
		 		}
		 	}
		} catch (DALException e) {
			throw new BLLException(new Exception("La connexion a échoué"));
		}		
		
		return array;
	}
	
	public boolean updateUser(User user) throws BLLException {
		boolean success = false;
		isNotNull(user);
		try {
			success = usersDAO.updateUserByID(user);
		} catch (DALException e) {
			throw new BLLException(new Exception("La mise � jour des donn�es de l'utilsateur a �chou�"));
		}
		return success;
	}

	private void isNotNull(User user) throws BLLException {
		
		if(user == null) {
			throw new BLLException(new Exception("L'utilisateur est vide."));
		}
	}
    
    private void isNotNull(int idUser) throws fr.eni.encheres.bll.BLLException {
		if(idUser == 0) {
			throw new BLLException(new Exception("L'id user pass� en param�tre est �gal � 0."));
		}
	}
}
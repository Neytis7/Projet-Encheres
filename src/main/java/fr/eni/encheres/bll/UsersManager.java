package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.UserDaoImpl;

public class UsersManager {

	private UserDaoImpl usersDAO = new UserDaoImpl();

	public List<String> signUpUser(User user) throws BLLException {

		isNotNull(user);
		ArrayList<User> allUsers = new ArrayList<>();
		List<String> errors = new ArrayList<>();

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

	private void isNotNull(User user) throws BLLException {
		
		if(user == null) {
			throw new BLLException(new Exception("L'utilisateur est vide."));
		}
	}
}

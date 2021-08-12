package fr.eni.encheres.bll;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.UserDaoImpl;

public class UsersManager {

	private UserDaoImpl usersDAO = new UserDaoImpl();

	public void signUpUser(User user) throws BLLException {

		isNotNull(user);

		try {
			usersDAO.insert(user);
		} catch (DALException e) {
			throw new BLLException(new Exception("L'inscription a échoué"));
		}		
	}
	
	public void signInUser(String loginUser, String password ) throws BLLException {

		isNotNull(loginUser);
		isNotNull(password);

		try {
			usersDAO.getUserByLogin(loginUser);
		} catch (DALException e) {
			throw new BLLException(new Exception("L'inscription a échoué"));
		}		
	}

	private void isNotNull(String param) throws BLLException {
		if(param == null) {
			throw new BLLException(new Exception("Ce champ est vide."));
		}
		
	}

	private void isNotNull(User user) throws BLLException {
		
		if(user == null) {
			throw new BLLException(new Exception("L'utilisateur est vide."));
		}
	}
}

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

	private void isNotNull(User user) throws BLLException {
		
		if(user == null) {
			throw new BLLException(new Exception("L'utilisateur est vide."));
		}
	}
}

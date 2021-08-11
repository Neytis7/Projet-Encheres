package bll;

import bo.User;
import dal.DALException;
import dal.UserDaoImpl;

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

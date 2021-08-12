package fr.eni.encheres.bll;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.UserDaoImpl;

public class UsersManager {
private UserDaoImpl userDAO = new UserDaoImpl();
	
	public UserDaoImpl getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDaoImpl userDAO) {
		this.userDAO = userDAO;
	}
	
	public User getUserById(int idUser) throws BLLException {
		isNotNull(idUser);
		try {
			return userDAO.selectUserById(idUser);
		} catch (DALException dalException) {
			throw new BLLException(new Exception("La r�cup�ration de l'utilisateur � �chou�e."));
		}
	}
	
	
	private void isNotNull(int idUser) throws fr.eni.encheres.bll.BLLException {
		if(idUser == 0) {
			throw new BLLException(new Exception("L'id user pass� en param�tre est �gal � 0."));
		}
	}
}

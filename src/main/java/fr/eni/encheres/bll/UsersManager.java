package fr.eni.encheres.bll;

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
    
    private void isNotNull(int idUser) throws fr.eni.encheres.bll.BLLException {
		if(idUser == 0) {
			throw new BLLException(new Exception("L'id user pass� en param�tre est �gal � 0."));
		}
	}
}
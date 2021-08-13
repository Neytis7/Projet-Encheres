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
			throw new BLLException(new Exception("La rï¿½cupï¿½ration de l'utilisateur ï¿½ ï¿½chouï¿½e."));
		}
	}
    
	public void signUpUser(User user) throws BLLException {

		isNotNull(user);

		try {
			usersDAO.insert(user);
		} catch (DALException e) {
			throw new BLLException(new Exception("L'inscription a Ã©chouÃ©"));
		}		
	}
	
	public boolean updateUser(User user) throws BLLException {
		boolean success = false;
		isNotNull(user);
		try {
			success = usersDAO.updateUserByID(user);
		} catch (DALException e) {
			throw new BLLException(new Exception("La mise à jour des données de l'utilsateur a échoué"));
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
			throw new BLLException(new Exception("L'id user passï¿½ en paramï¿½tre est ï¿½gal ï¿½ 0."));
		}
	}
}
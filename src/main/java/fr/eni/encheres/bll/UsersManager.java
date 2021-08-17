package fr.eni.encheres.bll;

import java.util.ArrayList;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.bo.Utils;
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
      throw new BLLException(new Exception("La récupération de l'utilisateur à échouée."));
    }
  }

  public ArrayList<String> signUpUser(User user) throws BLLException {

    isNotNull(user);
    ArrayList<String> errors = new ArrayList<>();

    try {
   
      errors = Utils.uniqueLogin(usersDAO, user);

      if (errors.size() == 0) {
        usersDAO.insert(user);
      }

    } catch (DALException e) {
      throw new BLLException(new Exception("L'inscription a échoué"));
    }
    return errors;
  }

  public ArrayList<Object> signInUser(String loginUser, String password) throws BLLException {

    ArrayList<Object> array = new ArrayList<>();
    User user = null;
    boolean access = false;

    try {
      user = usersDAO.getUserByLogin(loginUser);

      if (user != null) {
        if (password.trim().equals(user.getPassword()) && (loginUser.trim().equals(user.getPseudo())
            || loginUser.trim().equals(user.getMail()))) {

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

  public ArrayList<String> updateUser(User user) throws BLLException {
	  
	  ArrayList<String> errors = new ArrayList<>();
	  boolean success = false;
	  isNotNull(user);
    
    
    try {
    	errors = Utils.uniqueLogin(usersDAO, user);
    	
    	if (errors.size() == 0) {
    		success = usersDAO.updateUserByID(user);
    		
    		if(!success) {
    			 throw new DALException(new Exception("Une erreur est survenue"));
    		}
    	}
    	
    } catch (DALException e) {
      throw new BLLException(new Exception("La mise à jour des données de l'utilsateur a échoué"));
    }
    return errors;
  }

  private void isNotNull(User user) throws BLLException {

    if (user == null) {
      throw new BLLException(new Exception("L'utilisateur est vide."));
    }
  }

  private void isNotNull(int idUser) throws BLLException {
    if (idUser == 0) {
      throw new BLLException(new Exception("L'id user passé en paramètre est égal à 0."));
    }
  }
}

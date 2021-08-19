package fr.eni.encheres.bo;

import java.util.ArrayList;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.UserDaoImpl;

public class Utils {

  final public static boolean isBlankString(String string) {
    return string == null || string.trim().isEmpty();
  }

  final public static ArrayList<String> uniqueLogin(UserDaoImpl userDAO, User user)
      throws DALException {

    ArrayList<String> errors = new ArrayList<>();
    ArrayList<User> allUsers = new ArrayList<>();
    int idUser = 0;

    if (user.getNo_user() != 0) {
      idUser = user.getNo_user();
    }

    try {

      allUsers = userDAO.selectAllUsersPseudoAndMail(idUser);
    } catch (DALException exception) {
      throw new DALException(
          new Exception("An error has occurred while checking available pseudo"));
    }

    if (allUsers != null) {
      for (User aUser : allUsers) {
        if (aUser.getPseudo().trim().equals(user.getPseudo())) {
          errors.add("This pseudo is already used. Choose an other");
        }

        if (aUser.getMail().trim().equals(user.getMail())) {
          errors.add("An account with this mail already exists");
        }
        if (errors.size() > 0) {
          break;
        }
      }
    }
    return errors;
  }
}

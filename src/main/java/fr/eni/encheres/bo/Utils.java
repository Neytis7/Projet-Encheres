package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
          new Exception("Une erreur est survenue pour la récupération de la liste des login"));
    }

    if (allUsers != null) {
      for (User aUser : allUsers) {
        if (aUser.getPseudo().trim().equals(user.getPseudo())) {
          errors.add("Ce pseudo est déjà utilisé par une autre personne");
        }

        if (aUser.getMail().trim().equals(user.getMail())) {
          errors.add("Cet email est déjà utilisé par une autre personne");
        }
        if (errors.size() > 0) {
          break;
        }
      }
    }
    return errors;
  }

  final public static boolean isEmailAdress(String email) {
    Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
    Matcher m = p.matcher(email.toUpperCase());
    return m.matches();
  }
}

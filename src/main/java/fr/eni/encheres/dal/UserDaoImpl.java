package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class UserDaoImpl implements UserDAO{

  private static final String INSERT_USER = "USE DB_ENCHERES INSERT INTO UTILISATEURS "
      + "(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
  private static final String SELECT_USER_ID =
      "USE DB_ENCHERES SELECT * FROM UTILISATEURS WHERE no_utilisateur = (?) ";

  private static final String SELECT_USER_BY_ID_ARTICLE =
      "USE DB_ENCHERES SELECT * FROM UTILISATEURS u INNER JOIN ARTICLES_VENDUS a ON a.no_utilisateur = u.no_utilisateur "
          + " WHERE a.no_article = (?) ";

  private static final String UPDATE_USER_BY_ID = "USE DB_ENCHERES UPDATE UTILISATEURS "
      + " SET pseudo = (?)," + " nom = (?)," + " prenom = (?)," + " email = (?),"
      + " telephone = (?)," + " rue = (?)," + " code_postal = (?)," + " ville = (?),"
      + " mot_de_passe = (?)," + "	credit = (?)" + " WHERE no_utilisateur = (?)";

  private static final String SELECT_USER_BY_LOGIN =
      "USE DB_ENCHERES SELECT * FROM UTILISATEURS WHERE (pseudo = (?) or email = (?)) and mot_de_passe = (?)";

  private static final String SELECT_ALL_USERS =
      "USE DB_ENCHERES SELECT pseudo, email FROM UTILISATEURS";

  private static final String DELETE_USER_BY_ID =
      "USE DB_ENCHERES UPDATE UTILISATEURS SET estSupprimee = (?) WHERE no_utilisateur = (?)";
  
  @Override
  public boolean insert(User user) throws DALException {

    int result = 0;
    boolean success = false;

    try (Connection connection = ConnexionProvider.getConnection()) {

      PreparedStatement ps =
          connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, user.getPseudo());
      ps.setString(2, user.getName());
      ps.setString(3, user.getFirst_name());
      ps.setString(4, user.getMail());
      ps.setString(5, user.getPhone_number());
      ps.setString(6, user.getAddress());
      ps.setString(7, user.getZip_code());
      ps.setString(8, user.getCity());
      ps.setString(9, user.getPassword());
      ps.setInt(10, 0);
      ps.setBoolean(11, false);
      result = ps.executeUpdate();

      if (result > 0) {
        try (ResultSet generatedKey = ps.getGeneratedKeys()) {
          if (generatedKey.next()) {
            user.setNo_user(generatedKey.getInt(1));
            success = true;
          }
        } catch (SQLException e) {
          throw new DALException(new Exception("La g??n??ration de l'id de l'utilisateur a ??chou??"));
        }
      }

    } catch (SQLException exception) {
      throw new DALException(new Exception("[Erreur] impossible de cr??er l'utilisateur."));
    }

    return success;
  }

  @Override
  public User getUserByLogin(String loginUser, String password) throws DALException {

    ResultSet result;
    User user = null;
    try (Connection connection = ConnexionProvider.getConnection()) {

      PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_LOGIN);
      ps.setString(1, loginUser);
      ps.setString(2, loginUser);
      ps.setString(3, password);
      result = ps.executeQuery();

      if (result.next()) {
        user = new User(result.getInt("no_utilisateur"), result.getString("pseudo"),
            result.getString("nom"), result.getString("prenom"), result.getString("email"),
            result.getString("telephone"), result.getString("rue"), result.getString("code_postal"),
            result.getString("ville"), result.getString("mot_de_passe"), result.getInt("credit"),
            result.getByte("administrateur") != 0, result.getByte("estSupprimee") != 0

        );
      }
    } catch (SQLException exception) {
      throw new DALException(new Exception(
          "[Erreur] impossible de r??cuperer l'utilisateur avec le login :" + loginUser));
    }
    return user;
  }
  
  @Override
  public ArrayList<User> selectAllUsersPseudoAndMail(int idUser) throws DALException {

    ResultSet result;
    ArrayList<User> allUsers = new ArrayList<>();

    try (Connection connection = ConnexionProvider.getConnection()) {

      PreparedStatement ps =
          connection.prepareStatement(SELECT_ALL_USERS + " where no_utilisateur != (?)");
      ps.setInt(1, idUser);
      result = ps.executeQuery();

      while (result.next()) {

        allUsers.add(new User(result.getString("pseudo"), result.getString("email")));
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new DALException(
          new Exception("[erreur] R??cup??ration impossible de la liste des utilisateurs"));
    }

    return allUsers;
  }
  
  @Override
  public User selectUserById(int idUser) throws DALException {
    User user = null;
    try (Connection connexion = ConnexionProvider.getConnection()) {

      PreparedStatement preparedStatement = connexion.prepareStatement(SELECT_USER_ID);
      preparedStatement.setInt(1, idUser);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        user = new User(resultSet.getInt("no_utilisateur"), resultSet.getString("pseudo"),
            resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
            resultSet.getString("telephone"), resultSet.getString("rue"),
            resultSet.getString("code_postal"), resultSet.getString("ville"),
            resultSet.getString("mot_de_passe"), resultSet.getInt("credit"),
            resultSet.getByte("administrateur") != 0, resultSet.getByte("estSupprimee") != 0);
      } else {
        throw new DALException(new Exception("L'utilisateur n'existe pas"));
      }

    } catch (SQLException exception) {
      throw new DALException(new Exception("An error occured while get user with id " + idUser));
    }
    return user;
  }
  @Override
  public User selectUserByIdArticle(int idArticle) throws DALException {
    User user = null;
    try {
      Connection connexion = ConnexionProvider.getConnection();
      PreparedStatement preparedStatement = connexion.prepareStatement(SELECT_USER_BY_ID_ARTICLE);
      preparedStatement.setInt(1, idArticle);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        user = new User(resultSet.getInt("no_utilisateur"), resultSet.getString("pseudo"),
            resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"),
            resultSet.getString("telephone"), resultSet.getString("rue"),
            resultSet.getString("code_postal"), resultSet.getString("ville"),
            resultSet.getString("mot_de_passe"), resultSet.getInt("credit"),
            resultSet.getByte("administrateur") != 0, resultSet.getByte("estSupprimee") != 0);
      } else {
        throw new DALException(new Exception("L'utilisateur n'existe pas"));
      }

    } catch (SQLException exception) {
      throw new DALException(
          new Exception("An error occured while get user on article " + idArticle));
    }
    return user;
  }

  @Override
  public boolean updateUserByID(User userToUpdate) throws DALException {
    boolean success = false;
    try (Connection connexion = ConnexionProvider.getConnection()) {

      PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_USER_BY_ID);
      preparedStatement.setString(1, userToUpdate.getPseudo());
      preparedStatement.setString(2, userToUpdate.getName());
      preparedStatement.setString(3, userToUpdate.getFirst_name());
      preparedStatement.setString(4, userToUpdate.getMail());
      preparedStatement.setString(5, userToUpdate.getPhone_number());
      preparedStatement.setString(6, userToUpdate.getAddress());
      preparedStatement.setString(7, userToUpdate.getZip_code());
      preparedStatement.setString(8, userToUpdate.getCity());
      preparedStatement.setString(9, userToUpdate.getPassword());
      preparedStatement.setInt(10, userToUpdate.getCredit());
      preparedStatement.setInt(11, userToUpdate.getNo_user());
      int result = preparedStatement.executeUpdate();

      if (result > 0) {
        success = true;
      } else {
        throw new DALException(new Exception(
            "An error occured while update user with id " + userToUpdate.getNo_user()));
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new DALException(
          new Exception("An error occured while update user with id " + userToUpdate.getNo_user()));
    }
    return success;
  }
  
  @Override
  public boolean delete(User user) throws DALException {

    boolean success = false;
    try {
      Connection connexion = ConnexionProvider.getConnection();
      PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_USER_BY_ID);
      preparedStatement.setInt(1, User.DELETED_ACCOUNT);
      preparedStatement.setInt(2, user.getNo_user());
      int result = preparedStatement.executeUpdate();

      if (result > 0) {
        success = true;
      }

    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new DALException(
          new Exception("Impossible de supprimer l'user avec l'id" + user.getNo_user()));
    }
    return success;
  }
}

package fr.eni.encheres.dal;

import java.util.ArrayList;

import fr.eni.encheres.bo.User;

public interface UserDAO {
	public boolean insert(User user) throws DALException;

	public boolean delete(User user) throws DALException;

	public boolean updateUserByID(User userToUpdate) throws DALException;

	public User selectUserByIdArticle(int idArticle) throws DALException;

	public User selectUserById(int idUser) throws DALException;

	public ArrayList<User> selectAllUsersPseudoAndMail(int idUser) throws DALException;

	public User getUserByLogin(String loginUser, String password) throws DALException;
}

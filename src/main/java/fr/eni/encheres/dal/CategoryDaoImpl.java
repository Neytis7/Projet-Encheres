package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni.encheres.bo.Category;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class CategoryDaoImpl {

	private static final String SELECT_CATEGORY_BY_ID = "USE DB_ENCHERES SELECT * FROM CATEGORIES where no_categorie = (?)";
	private static final String SELECT_ALL_CATEGORIES = "USE DB_ENCHERES SELECT * FROM CATEGORIES";
	
	public Category selectCategoryById(int idCategory) throws DALException {
		
		ResultSet result;
		Category category = null;
		
		try {
			
			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_CATEGORY_BY_ID);	
			ps.setInt(1, idCategory);
			result = ps.executeQuery();
			
			if(result.next()) {
				category = new Category(result.getInt("no_categorie"), result.getString("libelle"));
			}
		}catch(SQLException exception) {
			throw new DALException(new Exception("La récupération de la catégorie à échoué"));
		}
		return category;
	}

	public ArrayList<Category> selectAllCategories() throws DALException {
		
		ResultSet result;
		ArrayList<Category> allCategories = new ArrayList<>();
		
		try {
			
			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_CATEGORIES);	
			result = ps.executeQuery();
			
			while(result.next()) {
				Category category = new Category(result.getInt("no_categorie"), result.getString("libelle"));
				allCategories.add(category);
			}
		}catch(SQLException exception) {
			throw new DALException(new Exception("La récupération des catégories à échoué"));
		}
		return allCategories;
	}
}

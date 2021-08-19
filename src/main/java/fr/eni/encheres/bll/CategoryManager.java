package fr.eni.encheres.bll;

import java.util.ArrayList;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.dal.CategoryDaoImpl;
import fr.eni.encheres.dal.DALException;

public class CategoryManager {

	private CategoryDaoImpl categoryDAO = new CategoryDaoImpl();
	private static CategoryManager instance = null;

	public CategoryDaoImpl getCategoryDAO() {
		return categoryDAO;
	}

	public synchronized static CategoryManager getInstance() {
		if (instance == null) {
			instance = new CategoryManager();
		}
		return instance;
	}

	public Category getCategoryById(int idCategory) throws BLLException {

		try {
			return categoryDAO.selectCategoryById(idCategory);
		} catch (DALException exception) {
			throw new BLLException(new Exception("La récupération de la catégorie à échoué."));
		}
	}

	public ArrayList<Category> getAllCategories() throws BLLException {

		ArrayList<Category> allCategories = new ArrayList<>();

		try {
			allCategories = categoryDAO.selectAllCategories();
		} catch (DALException exception) {
			throw new BLLException(new Exception("La récupération des catégories à échoué."));
		}

		return allCategories;
	}

	public Category getCategoryByIdArticle(int idArticle) throws BLLException {
		Category articleCategory = null;
		try {
			articleCategory = categoryDAO.selectCategoryByIdArticle(idArticle);
		} catch (DALException exception) {
			exception.printStackTrace();
			throw new BLLException(new Exception("La récupération de la catégorie de l'article à échoué."));
		}
		return articleCategory;
	}
}

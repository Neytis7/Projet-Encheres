package fr.eni.encheres.bll;

import java.util.List;
import fr.eni.encheres.bo.Encheres;
import fr.eni.encheres.dal.EncheresDAO;
import fr.eni.encheres.dal.jdbc.EncheresDAOJdbcImpl;

public class EncheresManager {

  private static EncheresManager instance = null;

  public EncheresManager() {
    encheresDAO = new EncheresDAOJdbcImpl();
  }

  public synchronized static EncheresManager getInstance() {
    if (instance == null) {
      instance = new EncheresManager();
    }
    return instance;
  }

  // Stock la référence à la couche DAL active
  private EncheresDAO encheresDAO;

  public List<Encheres> visualiserEncheres() {
    List<Encheres> listEncheres = null;
    try {
      listEncheres = encheresDAO.visualiserEncheres();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listEncheres;

  }

  public List<Encheres> visualiserEncheresNameAll(String article) {
    List<Encheres> listEncheres = null;
    try {
      listEncheres = encheresDAO.visualiserEncheresNameAll(article);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listEncheres;
  }

  public List<Encheres> visualiserEncheresNameCategorie(String article, String categorie) {
    List<Encheres> listEncheres = null;
    try {
      listEncheres = encheresDAO.visualiserEncheresNameCategorie(article, categorie);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listEncheres;
  }

  public List<Encheres> visualiserEncheresCategorie(String categorie) {
    List<Encheres> listEncheres = null;
    try {
      listEncheres = encheresDAO.visualiserEncheresCategorie(categorie);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listEncheres;
  }

}

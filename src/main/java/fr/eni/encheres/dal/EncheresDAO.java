package fr.eni.encheres.dal;

import java.util.List;
import fr.eni.encheres.bo.Encheres;

public interface EncheresDAO {

  List<Encheres> visualiserEncheres() throws Exception;

  List<Encheres> visualiserEncheresNameAll(String article) throws Exception;

  List<Encheres> visualiserEncheresNameCategorie(String article, String categorie) throws Exception;

  List<Encheres> visualiserEncheresCategorie(String categorie) throws Exception;

}

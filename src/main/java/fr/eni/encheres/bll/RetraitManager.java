package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DALException;
import fr.eni.encheres.dal.RetraitDaoImpl;

public class RetraitManager {

	private RetraitDaoImpl retraitDAO = new RetraitDaoImpl();
	
	public RetraitDaoImpl getCategoryDAO() {
		return retraitDAO;
	}
	
	public int creerRetrait(Retrait retrait) throws BLLException {
		
		try {
			return retraitDAO.insert(retrait);
		} catch (DALException exception) {
			throw new BLLException(new Exception("La création de l'adresse de retrait à échoué."));
		}
	}
	
	public Retrait getRetraitByIdArticle(int idArticle) throws BLLException {
		
		try {
			return retraitDAO.getRetraitByIdArticle(idArticle);
		} catch (DALException exception) {
			throw new BLLException(new Exception("La récupération du point de retrait à échoué."));
		}
	}
}

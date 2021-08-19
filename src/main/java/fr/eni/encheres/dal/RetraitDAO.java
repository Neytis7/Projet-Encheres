package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;

public interface RetraitDAO {
	
	public int insert(Retrait retrait) throws DALException;
	public Retrait getRetraitByIdArticle(int idArticle) throws DALException;
}

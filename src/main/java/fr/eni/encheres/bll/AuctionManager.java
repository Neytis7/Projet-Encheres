package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Auction;
import fr.eni.encheres.dal.AuctionDAO;
import fr.eni.encheres.dal.AuctionDAOImpl;

public class AuctionManager {
	private static AuctionManager instance = null;

	public AuctionManager() {
		auctionDAO = new AuctionDAOImpl();
	}

	public synchronized static AuctionManager getInstance() {
		if (instance == null) {
			instance = new AuctionManager();
		}
		return instance;
	}

	// Ref Dal
	private AuctionDAO auctionDAO;
	
	public List<Auction> getListAuctionByIdArticle(int idArticle) throws BLLException {
		List<Auction> auctionsList = null;
		try {
			auctionsList = auctionDAO.getListAuctionByArticleId(idArticle);
		} catch (Exception e) {
			throw new BLLException(new Exception("La récupération des enchères a échoué."));
		}
		return auctionsList;
	}
	
	
	public int insertNewAuction(Auction auction, int idArticle) throws BLLException {
		try {
			return auctionDAO.insertNewAuction(auction, idArticle);
		} catch (Exception e) {
			throw new BLLException(new Exception("L'insert de la nouvelle enchère a échouée."));
		}
	}
}

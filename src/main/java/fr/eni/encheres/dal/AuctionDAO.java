package fr.eni.encheres.dal;

import java.util.ArrayList;

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bo.Auction;

public interface AuctionDAO {

	ArrayList<Auction> getListAuctionByArticleId(int idArticle) throws BLLException, DALException;
	int insertNewAuction(Auction auction, int idArticle) throws BLLException, DALException;
}
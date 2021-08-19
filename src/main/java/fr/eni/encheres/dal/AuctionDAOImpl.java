package fr.eni.encheres.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.eni.encheres.bll.BLLException;
import fr.eni.encheres.bll.UsersManager;
import fr.eni.encheres.bo.Auction;
import fr.eni.encheres.dal.jdbc.ConnexionProvider;

public class AuctionDAOImpl implements AuctionDAO {
	
	private static final String SELECT_LIST_AUCTION_BY_ID_ARTICLE = "USE DB_ENCHERES SELECT * FROM ENCHERES WHERE no_article = (?)";
	private static final String INSERT_AUCTION = "USE DB_ENCHERES INSERT INTO ENCHERES "
												+ "(no_utilisateur, no_article, date_enchere, montant_enchere) "
												+ "VALUES (?,?,?,?)";		
			
	@Override
	public ArrayList<Auction> getListAuctionByArticleId(int idArticle) throws BLLException, DALException {
		ResultSet result;
		ArrayList<Auction> auctionsList = new ArrayList<>();
		UsersManager usersManager = new UsersManager();
		try {

			Connection connection = ConnexionProvider.getConnection();
			PreparedStatement ps = connection.prepareStatement(SELECT_LIST_AUCTION_BY_ID_ARTICLE);
			ps.setInt(1, idArticle);
			result = ps.executeQuery();

			while (result.next()) {
				Auction auction = new Auction(result.getInt("no_enchere"), 
						result.getDate("date_enchere").toLocalDate(),
						result.getInt("montant_enchere"),
						usersManager.getUserById(result.getInt("no_utilisateur")));
						
				auctionsList.add(auction);
			}
		} catch (SQLException exception) {
			throw new DALException(new Exception("La récupération des enchères à échoué"));
		}
		return auctionsList;
	}


	@Override
	public int insertNewAuction(Auction auction, int idArticle) throws BLLException, DALException {
		int result = 0;		
		try(Connection connection = ConnexionProvider.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(INSERT_AUCTION);	
			ps.setInt(1, auction.getUserAuction().getNo_user());
			ps.setInt(2, idArticle);
			ps.setDate(3, Date.valueOf(auction.getDateAuction()));
			ps.setInt(4, auction.getPrice_auction());
			result = ps.executeUpdate();		
				
		}catch(SQLException exception) {
			exception.printStackTrace();
			throw new DALException(new Exception("[Erreur] impossible de créer l'enchère."));
		}
		
		return result;	
	}

}

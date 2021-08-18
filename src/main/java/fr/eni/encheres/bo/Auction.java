package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.Date;

public class Auction {
	private int no_auction;
	private LocalDate dateAuction;
	private int price_auction;
	private User userAuction;

	public Auction(int no_auction, LocalDate dateAuction, int price_auction, User userAuction) {
		super();
		this.no_auction = no_auction;
		this.dateAuction = dateAuction;
		this.price_auction = price_auction;
		this.userAuction = userAuction;
	}
	
	public User getUserAuction() {
		return userAuction;
	}

	public void setUserAuction(User userAuction) {
		this.userAuction = userAuction;
	}

	public int getNo_auction() {
		return no_auction;
	}

	public void setNo_auction(int no_auction) {
		this.no_auction = no_auction;
	}

	public LocalDate getDateAuction() {
		return dateAuction;
	}

	public void setDateAuction(LocalDate dateAuction) {
		this.dateAuction = dateAuction;
	}

	public int getPrice_auction() {
		return price_auction;
	}

	public void setPrice_auction(int price_auction) {
		this.price_auction = price_auction;
	}

}

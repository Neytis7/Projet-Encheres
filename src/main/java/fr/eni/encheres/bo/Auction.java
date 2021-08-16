package fr.eni.encheres.bo;

import java.util.Date;

public class Auction {
  private int no_auction;
  private Date dateAuction;
  private int price_auction;

  public Auction(int no_auction, Date dateAuction, int price_auction) {
    super();
    this.no_auction = no_auction;
    this.dateAuction = dateAuction;
    this.price_auction = price_auction;
  }

  public int getNo_auction() {
    return no_auction;
  }

  public void setNo_auction(int no_auction) {
    this.no_auction = no_auction;
  }

  public Date getDateAuction() {
    return dateAuction;
  }

  public void setDateAuction(Date dateAuction) {
    this.dateAuction = dateAuction;
  }

  public int getPrice_auction() {
    return price_auction;
  }

  public void setPrice_auction(int price_auction) {
    this.price_auction = price_auction;
  }



}

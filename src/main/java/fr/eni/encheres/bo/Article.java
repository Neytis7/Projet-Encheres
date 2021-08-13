package fr.eni.encheres.bo;

import java.util.Date;

public class Article {

  private int no_article;
  private String name_article;
  private String description;
  private Date start_date;
  private Date end_date;
  private int initial_price;
  private int sell_price;
  private User user;
  private Category category;
  private boolean isDelete;
  private int price_auction = 0;

  public Article(int no_article, String name_article, String description, Date start_date,
      Date end_date, int initial_price, int sell_price, User user, Category category,
      boolean isDelete) {
    super();
    this.no_article = no_article;
    this.name_article = name_article;
    this.description = description;
    this.start_date = start_date;
    this.end_date = end_date;
    this.initial_price = initial_price;
    this.sell_price = sell_price;
    this.user = user;
    this.category = category;
    this.isDelete = isDelete;
  }



  public int getPrice_auction() {
    return price_auction;
  }



  public void setPrice_auction(int a) {
    this.price_auction = a;
  }



  public int getNo_article() {
    return no_article;
  }

  public void setNo_article(int no_article) {
    this.no_article = no_article;
  }

  public String getName_article() {
    return name_article;
  }

  public void setName_article(String name_article) {
    this.name_article = name_article;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getStart_date() {
    return start_date;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  public Date getEnd_date() {
    return end_date;
  }

  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
  }

  public int getInitial_price() {
    return initial_price;
  }

  public void setInitial_price(int initial_price) {
    this.initial_price = initial_price;
  }

  public int getSell_price() {
    return sell_price;
  }

  public void setSell_price(int sell_price) {
    this.sell_price = sell_price;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public boolean isDelete() {
    return isDelete;
  }

  public void setDelete(boolean isDelete) {
    this.isDelete = isDelete;
  }

}

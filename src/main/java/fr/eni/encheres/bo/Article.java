package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Article {

  private int no_article;
  private String name_article;
  private String description;
  private LocalDate start_date;
  private LocalDate end_date;
  private int initial_price;
  private int sell_price;
  private User userSeller;
  private User userBuyer;
  private Category category;
  private boolean isDelete;
  private int price_auction = 0;
  private List<Auction> listAuction;

  public Article(int no_article, String name_article, String description, LocalDate start_date,
      LocalDate end_date, int initial_price, int sell_price, User userSeller, Category category,
      boolean isDelete) {
    super();
    this.no_article = no_article;
    this.name_article = name_article;
    this.description = description;
    this.start_date = start_date;
    this.end_date = end_date;
    this.initial_price = initial_price;
    this.sell_price = sell_price;
    this.userSeller = userSeller;
    this.category = category;
    this.isDelete = isDelete;
    this.listAuction = new ArrayList<Auction>();
  }

  public Article(String name_article, String description, LocalDate start_date, LocalDate end_date,
      int initial_price, int sell_price, User userSeller, Category category, boolean isDelete) {
    super();
    this.name_article = name_article;
    this.description = description;
    this.start_date = start_date;
    this.end_date = end_date;
    this.initial_price = initial_price;
    this.sell_price = sell_price;
    this.userSeller = userSeller;
    this.category = category;
    this.isDelete = isDelete;
    this.listAuction = new ArrayList<Auction>();
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

  public LocalDate getStart_date() {
    return start_date;
  }

  public void setStart_date(LocalDate start_date) {
    this.start_date = start_date;
  }

  public LocalDate getEnd_date() {
    return end_date;
  }

  public void setEnd_date(LocalDate end_date) {
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

  public User getUserSeller() {
    return userSeller;
  }

  public void setUserSeller(User userSeller) {
    this.userSeller = userSeller;
  }

  public User getUserBuyer() {
    return userBuyer;
  }

  public void setUserBuyer(User userBuyer) {
    this.userBuyer = userBuyer;
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

  public int getPrice_auction() {
    return price_auction;
  }

  public void setPrice_auction(int price_auction) {
    this.price_auction = price_auction;
  }

  public List<Auction> getListAuction() {
    return listAuction;
  }

  public void setListAuction(List<Auction> listAuction) {
    this.listAuction = listAuction;
  }

  public ArrayList<String> checkValueArticle(boolean checkCategory) {

    ArrayList<String> errors = new ArrayList<>();

    if (Utils.isBlankString(this.name_article)) {
      errors.add("Le nom de l'article est vide");
    }

    if (Utils.isBlankString(this.description)) {
      errors.add("La description de l'article est vide");
    }

    if (this.start_date == null) {
      errors.add("La date de début d'enchère de l'article est vide");
    } else if (LocalDate.now().isAfter(this.start_date)) {
      errors.add("La date de début d'enchère ne peut pas être dépassée");
    } else if (this.end_date != null && this.start_date.isAfter(this.end_date)) {
      errors.add("La date de fin d'enchère doit être supérieur à la date de début de l'article");
    }

    if (this.end_date == null) {
    } else if (LocalDate.now().isAfter(this.end_date)) {
      errors.add("La date de fin d'enchère de l'article est vide");
      errors.add("La date de fin d'enchère ne peut pas être dépassée");
    } else if (this.start_date != null && this.end_date.isBefore(this.start_date)) {
      errors.add("La date de début d'enchère doit être inférieur à la date de fin de l'article");
    }

    if (this.userSeller != null && Utils.isBlankString(this.userSeller.getPseudo())) {
      errors.add("Vos informations en tant que vendeur ne sont pas remplies");
    }

    if (checkCategory) {
      if (this.category != null && Utils.isBlankString(this.getCategory().getLibelle())) {
        errors.add("La categorie de l'article est vide");
      }
    }

    if (this.initial_price <= 0) {
      errors.add("Le prix initial de l'article doit être supérieur à 0");
    }

    return errors;
  }
}

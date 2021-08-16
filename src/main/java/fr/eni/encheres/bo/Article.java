package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  private List<Auction> listAuction = new ArrayList<Auction>();

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



  public List<Auction> getListAuction() {
    return listAuction;
  }

  public void setListAuction(List<Auction> listAuction) {
    this.listAuction = listAuction;
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

	public Article(String name_article, String description_article, LocalDate date_start_enchere,
			LocalDate date_end_enchere, int init_price, int final_price, User seiler, Category category, boolean isDelete) {
		super();
		
		this.name_article = name_article;
		this.description_article = description_article;
		this.date_start_enchere = date_start_enchere;
		this.date_end_enchere = date_end_enchere;
		this.init_price = init_price;
		this.final_price = final_price;
		this.seiler = seiler;
		this.category = category;
		this.isDelete = isDelete;
	}
	}
		this.isDelete = isDelete;
		this.seiler = seiler;
		this.final_price = final_price;
		this.init_price = init_price;
		this.date_start_enchere = date_start_enchere;
		this.date_end_enchere = date_end_enchere;
		this.description_article = description_article;
		this.name_article = name_article;
		super();
		
			LocalDate date_end_enchere, int init_price, int final_price, User seiler, boolean isDelete) {
	public Article(String name_article, String description_article, LocalDate date_start_enchere,
	public ArrayList<String> checkValueArticle(boolean checkCategory) {
		
		ArrayList<String> errors = new ArrayList<>();
		
		if(Utils.isBlankString(this.name_article)){
			errors.add("Le nom de l'article est vide");
		}
		
		if(Utils.isBlankString(this.description_article)){
			errors.add("La description de l'article est vide");	
		}
		
		if(this.date_start_enchere == null){
			errors.add("La date de début d'enchère de l'article est vide");
		}else if(LocalDate.now().isAfter(this.date_start_enchere)){
			errors.add("La date de début d'enchère ne peut pas être dépassée");
		}else if(this.date_end_enchere != null && this.date_start_enchere.isAfter(this.date_end_enchere)) {
			errors.add("La date de fin d'enchère doit être supérieur à la date de début de l'article");
		}
		
		if(this.date_end_enchere == null){
		}else if(LocalDate.now().isAfter(this.date_end_enchere)){
			errors.add("La date de fin d'enchère de l'article est vide");
			errors.add("La date de fin d'enchère ne peut pas être dépassée");			
		}else if(this.date_start_enchere != null && this.date_end_enchere.isBefore(this.date_start_enchere)) {
			errors.add("La date de début d'enchère doit être inférieur à la date de fin de l'article");
		}
		
		if(this.seiler != null && Utils.isBlankString(this.seiler.getPseudo())){
			errors.add("Vos informations en tant que vendeur ne sont pas remplies");
		}
		
		if(checkCategory) {
			if(this.category != null && Utils.isBlankString(this.category.getName_category())){
				errors.add("La categorie de l'article est vide");
			}
		}
		
		if(this.init_price <= 0){
			errors.add("Le prix initial de l'article doit être supérieur à 0");
		}
		
		return errors;
	}
}
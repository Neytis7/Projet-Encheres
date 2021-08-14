package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Article {

	private int no_article;
	private String name_article;
	private String description_article;
	private LocalDate date_start_enchere;
	private LocalDate date_end_enchere;
	private int init_price;
	private int final_price;
	private User seiler;
	private Category category;
	private boolean isDelete;
	
	public Article(int no_article, String name_article, String description_article, LocalDate date_start_enchere,
			LocalDate date_end_enchere, int init_price, int final_price, User seiler, Category category, boolean isDelete) {
		
		super();
		this.no_article = no_article;
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
	
	public Article(String name_article, String description_article, LocalDate date_start_enchere,
			LocalDate date_end_enchere, int init_price, int final_price, User seiler, boolean isDelete) {
		
		super();
		this.name_article = name_article;
		this.description_article = description_article;
		this.date_start_enchere = date_start_enchere;
		this.date_end_enchere = date_end_enchere;
		this.init_price = init_price;
		this.final_price = final_price;
		this.seiler = seiler;
		this.isDelete = isDelete;
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
	public String getDescription_article() {
		return description_article;
	}
	public void setDescription_article(String description_article) {
		this.description_article = description_article;
	}
	public LocalDate getDate_start_enchere() {
		return date_start_enchere;
	}
	public void setDate_start_enchere(LocalDate date_start_enchere) {
		this.date_start_enchere = date_start_enchere;
	}
	public LocalDate getDate_end_enchere() {
		return date_end_enchere;
	}
	public void setDate_end_enchere(LocalDate date_end_enchere) {
		this.date_end_enchere = date_end_enchere;
	}
	public int getInit_price() {
		return init_price;
	}
	public void setInit_price(int init_price) {
		this.init_price = init_price;
	}
	public int getFinal_price() {
		return final_price;
	}
	public void setFinal_price(int final_price) {
		this.final_price = final_price;
	}
	public User getSeiler() {
		return seiler;
	}
	public void setSeiler(User seiler) {
		this.seiler = seiler;
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
			errors.add("La date de fin d'enchère de l'article est vide");
		}else if(LocalDate.now().isAfter(this.date_end_enchere)){
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

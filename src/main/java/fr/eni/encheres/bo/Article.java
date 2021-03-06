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
	private boolean isFinished;
	private int price_auction = 0;
	private List<Auction> listAuction;
	private Retrait withdrawalPoint;

	public Article(int no_article, String name_article, String description, LocalDate start_date, LocalDate end_date,
			int initial_price, int sell_price, User userSeller, Category category, boolean isDelete) {
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

	public Article(String name_article, String description, LocalDate start_date, LocalDate end_date, int initial_price,
			int sell_price, User userSeller, Category category, boolean isDelete) {
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

	public Article(int no_article, String name_article, String description, LocalDate start_date, LocalDate end_date,
			int initial_price, int sell_price, boolean isFinished) {
		super();
		this.no_article = no_article;
		this.name_article = name_article;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.initial_price = initial_price;
		this.sell_price = sell_price;
		this.isFinished = isFinished;
	}

	public Article() {
		super();
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
		
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
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

	public Retrait getWithdrawalPoint() {
		return withdrawalPoint;
	}

	public void setWithdrawalPoint(Retrait withdrawalPoint) {
		this.withdrawalPoint = withdrawalPoint;
	}

	public ArrayList<String> checkValueArticle(boolean checkCategory) {

		ArrayList<String> errors = new ArrayList<>();

		if (Utils.isBlankString(this.name_article)) {
			errors.add("Article name is empty");
		}

		if (Utils.isBlankString(this.description)) {
			errors.add("Description is empty");
		}

		if (this.start_date == null) {
			errors.add("Auction start date is empty");
		} else if (LocalDate.now().isAfter(this.start_date)) {
			errors.add("Auction start date can't be passed");
		} else if (this.end_date != null && this.start_date.isAfter(this.end_date)) {
			errors.add("Auction end date must be greater than auction start date");
		}

		if (this.end_date == null) {
			errors.add("Auction end date is empty");
		} else if (LocalDate.now().isAfter(this.end_date)) {
			errors.add("Auction end date can't be passed");
		} else if (this.start_date != null && this.end_date.isBefore(this.start_date)) {
			errors.add("The auction start date must be less than auction end date ");
		}

		if (this.userSeller != null && Utils.isBlankString(this.userSeller.getPseudo())) {
			errors.add("Your seller informations are empty");
		}

		if (checkCategory) {
			if (this.category != null && Utils.isBlankString(this.getCategory().getLibelle())) {
				errors.add("Category is empty");
			}
		}

		if (this.initial_price <= 0) {
			errors.add("Article initial price must be greater than 0");
		}

		return errors;
	}

	public String getBestAuction() {
		int bestPrice = 0;
		User user = null;
		for (int i = 0; i < listAuction.size(); i++) {
			if (listAuction.get(i).getPrice_auction() > bestPrice) {
				bestPrice = listAuction.get(i).getPrice_auction();
				user = listAuction.get(i).getUserAuction();
			}
		}

		String returnString = "";
		if (bestPrice != 0) {
			returnString = "" + bestPrice + " credits by " + user.getPseudo();
		} else {
			returnString = "No current bid !";
		}
		return returnString;

	}
	
	public String finalAuction() {
		int bestPrice = 0;
		User user = null;
		for (int i = 0; i < listAuction.size(); i++) {
			if (listAuction.get(i).getPrice_auction() > bestPrice) {
				bestPrice = listAuction.get(i).getPrice_auction();
				user = listAuction.get(i).getUserAuction();
			}
		}

		String returnString = "";
		if (bestPrice != 0) {
			returnString = "Sell wins by " + user.getPseudo() +  " with " + bestPrice + " credits";
		} else {
			returnString = "Sell finished, no one make a bid";
		}
		return returnString;

	}
	
	public int getBestBid() {
		int bestPrice = 0;
		for (int i = 0; i < listAuction.size(); i++) {
			if (listAuction.get(i).getPrice_auction() > bestPrice) {
				bestPrice = listAuction.get(i).getPrice_auction();
			}
		}

		return bestPrice;
	}
	
	public User getUserWithBestBid() {
		User user = null;
		int bestPrice = 0;
		for (int i = 0; i < listAuction.size(); i++) {
			if (listAuction.get(i).getPrice_auction() > bestPrice) {
				user = listAuction.get(i).getUserAuction();
			}
		}
		
		return user;
	}

	@Override
	public String toString() {
		return "Article [no_article=" + no_article + ", name_article=" + name_article + ", description=" + description
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", initial_price=" + initial_price
				+ ", sell_price=" + sell_price + ", userSeller=" + userSeller + ", userBuyer=" + userBuyer
				+ ", category=" + category + ", isDelete=" + isDelete + ", price_auction=" + price_auction
				+ ", listAuction=" + listAuction + ", withdrawalPoint=" + withdrawalPoint + "]";
	}

}

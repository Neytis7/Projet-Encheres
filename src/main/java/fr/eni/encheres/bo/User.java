package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int no_user;
	private String pseudo;
	private String name;
	private String first_name;
	private String mail;
	private String phone_number;
	private String address;
	private String zip_code;
	private String city;
	private String password;
	private int credit;
	private boolean isAdministrator;
	private boolean isDelete;
	private List<Article> listArticleSell = new ArrayList<Article>();
	private List<Article> listArticleBuy = new ArrayList<Article>();
	private List<Auction> listAuctionCompleted = new ArrayList<Auction>();

	public final static int DELETED_ACCOUNT = 1;
	public final static int ACTIVE_ACCOUNT = 0;

	public User(int no_user, String pseudo, String name, String first_name, String mail,
			String phone_number, String address, String zip_code, String city, String password,
			int credit, boolean isAdministrator, boolean isDelete) {
		super();
		this.no_user = no_user;
		this.pseudo = pseudo;
		this.name = name;
		this.first_name = first_name;
		this.mail = mail;
		this.address = address;
		this.zip_code = zip_code;
		this.city = city;
		this.password = password;
		this.credit = credit;
		this.isAdministrator = isAdministrator;
		this.isDelete = isDelete;
		if (phone_number != null) {
			this.phone_number = phone_number;
		} else {
			this.phone_number = "" ;
		}
	}


	public User(String pseudo, String mail) {
		super();
		this.pseudo = pseudo;
		this.mail = mail;
	}


	public User(String pseudo, String name, String first_name, String mail,
			String phone_number, String address, String zip_code, String city, String password,
			int credit, boolean isAdministrator) {
		super();
		this.pseudo = pseudo;
		this.name = name;
		this.first_name = first_name;
		this.mail = mail;
		this.address = address;
		this.zip_code = zip_code;
		this.city = city;
		this.password = password;
		this.credit = credit;
		this.isAdministrator = isAdministrator;
		if (phone_number != null) {
			this.phone_number = phone_number;
		} else {
			this.phone_number = "" ;
		}
	}

	public List<Auction> getListAuctionCompleted() {
		return listAuctionCompleted;
	}

	public void setListAuctionCompleted(List<Auction> listAuctionCompleted) {
		this.listAuctionCompleted = listAuctionCompleted;
	}

	public List<Article> getListArticleSell() {
		return listArticleSell;
	}

	public void setListArticleSell(List<Article> listArticleSell) {
		this.listArticleSell = listArticleSell;
	}

	public List<Article> getListArticleBuy() {
		return listArticleBuy;
	}

	public void setListArticleBuy(List<Article> listArticleBuy) {
		this.listArticleBuy = listArticleBuy;
	}

	public int getNo_user() {
		return no_user;
	}

	public void setNo_user(int no_user) {
		this.no_user = no_user;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}				

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public ArrayList<String> checkInformations(){
		ArrayList<String> errors = new ArrayList<>();
		if (Utils.isBlankString(this.pseudo)) {
			errors.add("Le pseudo est manquant");		
		}

		if (!this.pseudo.matches("^[a-zA-Z0-9]*$")) {
			errors.add("Le pseudo doit �tre compos� uniquement de caract�re alphanum�rique !");
		}

		if (Utils.isBlankString(this.name)) {
			errors.add("Le nom est manquant");		
		}
		if (Utils.isBlankString(this.first_name)) {
			errors.add("Le prénom est manquant");		
		}
		if (Utils.isBlankString(this.mail)) {
			errors.add("L'email est manquant");		
		}
		if (Utils.isBlankString(this.address)) {
			errors.add("L'adresse est manquante");		
		}
		if (Utils.isBlankString(this.city)) {
			errors.add("La ville est manquante");		
		}
		if (Utils.isBlankString(this.zip_code)) {
			errors.add("Le code postal est manquant");		
		}
		if (Utils.isBlankString(this.password)) {
			errors.add("Le mot de passe est manquant");		
		}
		return errors;
	}

	@Override
	public String toString() {
		return "User [no_user=" + no_user + ", pseudo=" + pseudo + ", name=" + name + ", first_name=" + first_name
				+ ", mail=" + mail + ", phone_number=" + phone_number + ", address=" + address + ", zip_code="
				+ zip_code + ", city=" + city + ", password=" + password + ", credit=" + credit + ", isAdministrator="
				+ isAdministrator + "]";
	}
}
package fr.eni.encheres.bo;

import java.util.ArrayList;

public class Retrait {

	private int no_article;
	private String address;
	private String city;
	private String code_postal;
	
	public Retrait(int no_article, String address, String city, String code_postal) {
		super();
		this.no_article = no_article;
		this.address = address;
		this.city = city;
		this.code_postal = code_postal;
	}

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode_postal() {
		return code_postal;
	}

	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}
	
	public ArrayList<String> checkValueRetrait() {
		
		ArrayList<String> errors = new ArrayList<>();
		
		if(Utils.isBlankString(this.address)){
			errors.add("Address is empty");
		}
		
		if(Utils.isBlankString(this.code_postal)){
			errors.add("Zip Code is empty");	
		}
		
		if(this.city == null){
			errors.add("City is empty");
		}
		
		return errors;
	}
	
	
	public String displayFullAddress() {
		return this.address + "\n" + this.code_postal + "\n" + this.city;
	}
}

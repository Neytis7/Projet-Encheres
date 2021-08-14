package fr.eni.encheres.bo;

public class Category {

	private int no_category;
	private String name_category;
	
	public Category(int no_category, String name_category) {
		super();
		this.no_category = no_category;
		this.name_category = name_category;
	}
	
	public int getNo_category() {
		return no_category;
	}
	public void setNo_category(int no_category) {
		this.no_category = no_category;
	}
	
	public String getName_category() {
		return name_category;
	}
	public void setName_category(String name_category) {
		this.name_category = name_category;
	}
	
}

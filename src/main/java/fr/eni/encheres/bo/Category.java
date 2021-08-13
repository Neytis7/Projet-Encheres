package fr.eni.encheres.bo;

public class Category {

  private int no_category;
  private String libelle;

  public Category(int no_category, String libelle) {
    super();
    this.no_category = no_category;
    this.libelle = libelle;
  }

  public int getNo_category() {
    return no_category;
  }

  public void setNo_category(int no_category) {
    this.no_category = no_category;
  }

  public String getLibelle() {
    return libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

}

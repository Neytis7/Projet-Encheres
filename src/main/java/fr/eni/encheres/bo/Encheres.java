package fr.eni.encheres.bo;

import java.util.Date;

public class Encheres {
  private String pseudo;
  private String nameArticle;
  private Date endDate;
  private Integer price;


  public Encheres(String pseudo, String nameArticle, Date endDate, int price) {
    super();
    this.pseudo = pseudo;
    this.nameArticle = nameArticle;
    this.endDate = endDate;
    this.price = price;
  }


  public String getPseudo() {
    return pseudo;
  }


  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }


  public String getNameArticle() {
    return nameArticle;
  }


  public void setNameArticle(String nameArticle) {
    this.nameArticle = nameArticle;
  }


  public Date getEndDate() {
    return endDate;
  }


  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }


  public Integer getPrice() {
    return price;
  }


  public void setPrice(Integer price) {
    this.price = price;
  }

}

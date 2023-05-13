/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author louay
 */
public class Article {

    private int id;
    private String libelle;
    private String categorie;
    private String image_name;
    private String prix;
    private String stock;
    private String ref;
   
    public Article() {
    }
    
    public Article(int id, String libelle, String image_name, String prix,String stock , String ref) {
        this.id = id;
        this.libelle = libelle;
        this.categorie = categorie;
        this.image_name = image_name;
        this.prix = prix;
        this.stock = stock;
        this.ref = ref;
        

    }

    public Article(String libelle, String image_names, String prix,String stock , String ref,Double rate, String idcat) {
        this.libelle = libelle;
        this.categorie= idcat;
        this.image_name = image_name;
        this.prix = prix;
        this.stock = stock;
        this.ref = ref;
        

    }

    public Article(String libelle, String prix, String stock, String ref) {
        this.libelle = libelle;
        this.prix = prix;
        this.stock = stock;
        this.ref = ref;
    }
    

    public Article(String ref) {
        this.ref = ref;
    }

    public Article(String text, String toString, String text0, Integer valueOf, Integer valueOf0) {
    }

    public Article(Integer valueOf) {
    }

    public Article(String text, String text0, int parseInt, int parseInt0, int parseInt1, double parseDouble) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

   

    
   
    
   


  

    public int getId_article() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getIdcat() {
        return categorie;
    }
    

    public String getPrix() {
        return prix;
    }

    public void setId_article(int id_article) {
        this.id = id_article;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

   

    public void setIdcat(String idcat) {
        this.categorie = categorie;
    }
  
     
    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    

    @Override
    public String toString() {
        return "Article{" + "id_article=" + id + ", libelle=" + libelle + ", categorie=" + categorie + ", image_article=" + image_name+ ", prix=" + prix + ", qt_article=" + stock +", ref=" + ref  +'}';
    }

    public String getPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (this.prix != other.prix) {
            return false;
        }
        return true;
    }

   

}

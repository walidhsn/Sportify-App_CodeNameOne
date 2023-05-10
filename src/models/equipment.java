/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author MOLKA
 */
public class equipment {
    
    private int id;
    private String name;
    private String adress;
    private String type;
    private int quantity;
    private supplier supplier;
    private String Price;
    private category category;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(supplier supplier) {
        this.supplier = supplier;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public category getCategory() {
        return category;
    }

    public void setCategory(category category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public equipment() {
    }

    public equipment(String name, String adress, String type, int quantity, supplier supplier, String Price, category category, String image) {
        this.name = name;
        this.adress = adress;
        this.type = type;
        this.quantity = quantity;
        this.supplier = supplier;
        this.Price = Price;
        this.category = category;
        this.image = image;
    }

    public equipment(int id, String name, String adress, String type, int quantity, supplier supplier, String Price, category category, String image) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.type = type;
        this.quantity = quantity;
        this.supplier = supplier;
        this.Price = Price;
        this.category = category;
        this.image = image;
    }
    
}

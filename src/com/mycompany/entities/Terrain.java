/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;




/**
 *
 * @author WALID
 */
public class Terrain {
    
    private int id,capacity,postalCode,roadNumber;
    private String name,sportType,roadName,city,country,imageName,imageUrl;
    private float rentPrice;
    private boolean disponibility;
    private Date updatedAt;

    public Terrain() {
    }

    public Terrain(int id, int capacity, int postalCode, int roadNumber, String name, String sportType, String roadName, String city, String country, String imageName,String imageUrl,float rentPrice, boolean disponibility, Date updatedAt) {
        this.id = id;
        this.capacity = capacity;
        this.postalCode = postalCode;
        this.roadNumber = roadNumber;
        this.name = name;
        this.sportType = sportType;
        this.roadName = roadName;
        this.city = city;
        this.country = country;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.rentPrice = rentPrice;
        this.disponibility = disponibility;
        this.updatedAt = updatedAt;
    }

    public Terrain(int capacity, int postalCode, int roadNumber, String name, String sportType, String roadName, String city, String country, String imageName,String imageUrl, float rentPrice, boolean disponibility, Date updatedAt) {
        this.capacity = capacity;
        this.postalCode = postalCode;
        this.roadNumber = roadNumber;
        this.name = name;
        this.sportType = sportType;
        this.roadName = roadName;
        this.city = city;
        this.country = country;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.rentPrice = rentPrice;
        this.disponibility = disponibility;
        this.updatedAt = updatedAt;
    }

    public Terrain(int capacity, int postalCode, int roadNumber, String name, String sportType, String roadName, String city, String country, String imageName, String imageUrl, float rentPrice, boolean disponibility) {
        this.capacity = capacity;
        this.postalCode = postalCode;
        this.roadNumber = roadNumber;
        this.name = name;
        this.sportType = sportType;
        this.roadName = roadName;
        this.city = city;
        this.country = country;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.rentPrice = rentPrice;
        this.disponibility = disponibility;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(int roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public float getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(float rentPrice) {
        this.rentPrice = rentPrice;
    }

    public boolean isDisponibility() {
        return disponibility;
    }

    public void setDisponibility(boolean disponibility) {
        this.disponibility = disponibility;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;

/**
 *
 * @author ramib
 */


public class Academy {
    private int id ;
    private String name,category,image;
    private EncodedImage imageUrl;
    
    public Academy() {
    // Empty constructor
    }

    public Academy(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }
    public Academy(int id, String name, String category, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.image = image;
    }

    public Academy(String name, String category) {
        this.name = name;
        this.category = category;
    }
    
    public Academy(String name, String category, String image) {
        this.name = name;
        this.category = category;
        this.image = image;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public EncodedImage getImageUrl() {
        if (imageUrl == null && image != null) {
            imageUrl = URLImage.createToStorage(
                EncodedImage.createFromImage(Image.createImage(1, 1), true), // placeholder image
                "academy_" + id + ".png", // unique filename for the image
                image // URL of the image
            );
        }
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Academy{" + "id=" + id + ", name=" + name + ", category=" + category + ", image=" + image + '}';
    }   
    
    
}

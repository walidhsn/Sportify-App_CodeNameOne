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
public class Categorie {
     private int id;
    private String nomCat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nomCat;
    }

    public void setName(String name) {
        this.nomCat = name;
    }

    public Categorie() {
    }

    public Categorie(int id, String name) {
        this.id = id;
        this.nomCat= name;
    }

    public Categorie(String name) {
        this.nomCat = name;
    }

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", name=" + nomCat + '}';
    }
    
    
}

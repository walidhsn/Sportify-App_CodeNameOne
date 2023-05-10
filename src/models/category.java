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
public class category {
     private int id;
    private String name;

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

    public category() {
    }

    public category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public category(String name) {
        this.name = name;
    }
    
}

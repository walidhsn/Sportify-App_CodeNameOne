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
public class supplier {
    private int id;;
    private String name;
    private String phone;
    private String email;
    private String notes;
    private String adress;

    public supplier(String name, String phone, String email, String notes, String adress) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
        this.adress = adress;
    }

    public supplier(int id, String name, String phone, String email, String notes, String adress) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
        this.adress = adress;
    }

    public supplier() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    
}

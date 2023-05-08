/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

import java.util.ArrayList;

/**
 *
 * @author moata
 */
public class User {
    private int id;
    private String email;
    private ArrayList<String> roles = new ArrayList<String>();
    private String nomutilisateur;
    private String phone;
    private String firstname;
    private String lastname;
    private boolean status = false;
    
    public User(String email,ArrayList<String> roles,String nomutilisateur,String phone,String firstname,String lastname,boolean status){
        this.email = email;
        this.firstname = firstname;
        this.roles = roles;
        this.nomutilisateur = nomutilisateur;
        this.lastname = lastname;
        this.status = status;
        this.phone = phone;
    }
    
    public User(String email,String nomutilisateur,String phone,String firstname,String lastname,boolean status){
        this.email = email;
        this.firstname = firstname;
        this.nomutilisateur = nomutilisateur;
        this.lastname = lastname;
        this.status = status;
        this.phone = phone;
    }
    
    public User(String email,String nomutilisateur,String phone,String firstname,String lastname){
        this.email = email;
        this.firstname = firstname;
        this.nomutilisateur = nomutilisateur;
        this.lastname = lastname;
        this.phone = phone;
    }
    
    public User(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public String getNomutilisateur() {
        return nomutilisateur;
    }

    public void setNomutilisateur(String nomutilisateur) {
        this.nomutilisateur = nomutilisateur;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", roles=" + roles + ", nomutilisateur=" + nomutilisateur + ", phone=" + phone + ", firstname=" + firstname + ", lastname=" + lastname + ", status=" + status + '}';
    }
    
    
}

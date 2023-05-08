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
public class Reservation {
    private int id,nbPerson,terrain_id,client_id;
    private Date dateReservation,startTime,endTime;
    private boolean resStatus;
    public Reservation() {
    }

    public Reservation(int id, int nbPerson, int terrain_id, int client_id, Date dateReservation, Date startTime, Date endTime, boolean resStatus) {
        this.id = id;
        this.nbPerson = nbPerson;
        this.terrain_id = terrain_id;
        this.client_id = client_id;
        this.dateReservation = dateReservation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resStatus = resStatus;
    }

    public Reservation(int nbPerson, int terrain_id, int client_id, Date dateReservation, Date startTime, Date endTime, boolean resStatus) {
        this.nbPerson = nbPerson;
        this.terrain_id = terrain_id;
        this.client_id = client_id;
        this.dateReservation = dateReservation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.resStatus = resStatus;
    }

 
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(int nbPerson) {
        this.nbPerson = nbPerson;
    }

    public int getTerrain_id() {
        return terrain_id;
    }

    public void setTerrain_id(int terrain_id) {
        this.terrain_id = terrain_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public boolean isResStatus() {
        return resStatus;
    }

    public void setResStatus(boolean resStatus) {
        this.resStatus = resStatus;
    }
    
}

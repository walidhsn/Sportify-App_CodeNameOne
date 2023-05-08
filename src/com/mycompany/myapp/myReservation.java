/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reservation;
import com.mycompany.entities.Terrain;
import com.mycompany.services.ServiceReservation;
import com.mycompany.services.ServiceTerrain;
import com.mycompany.shared.SharedComponents;
import java.util.ArrayList;

/**
 *
 * @author WALID
 */
public class myReservation extends Form{
    
    public myReservation(Resources theme){
        super("My Reservations");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40); // Set the background color of the Form
        ArrayList<Reservation> reservations = ServiceReservation.getInstance().afficherReservation_idClient(SharedComponents.user.getId());
       
        for(Reservation reservation : reservations){
            // Create a container for each terrain
            Container reservationContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            reservationContainer.setUIID("ReservationsContainer");
            reservationContainer.setPreferredH(200);
        }
    }
}

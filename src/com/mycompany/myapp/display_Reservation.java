/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reservation;

/**
 *
 * @author WALID
 */
public class display_Reservation extends Form {

    private Container c1, c2, c3, c4,c5;
    
    private Label dateField;
    private Label nbPersonField;
    private Label StartTimeField;
    private Label EndTimeField;
    private Label resStatusField;

    public display_Reservation(Resources theme, Reservation reservation) {
        super("Reservation Details");

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40);
        c1 = new Container(new FlowLayout(LEFT));
        c2 = new Container(new FlowLayout(LEFT));
        c3 = new Container(new FlowLayout(LEFT));
        c4 = new Container(new FlowLayout(LEFT));
        c5 = new Container(new FlowLayout(LEFT));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        
        ComponentGroup dateTimeGroup = new ComponentGroup();
        dateTimeGroup.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        dateField = new Label(dateFormat.format(reservation.getDateReservation()));
        c1.addAll(new Label("Reservation Date:"), dateField);
        dateTimeGroup.addComponent(c1);
        
        StartTimeField = new Label(timeFormat.format(reservation.getStartTime()));
        c2.addAll(new Label("Start Time:"), StartTimeField);
        dateTimeGroup.addComponent(c2);

        EndTimeField = new Label(timeFormat.format(reservation.getEndTime()));
        c3.addAll(new Label("End Time:"), EndTimeField);
        dateTimeGroup.addComponent(c3);

        nbPersonField = new Label(String.valueOf(reservation.getNbPerson()));
        c4.addAll(new Label("Number Of People:"), nbPersonField);
        dateTimeGroup.addComponent(c4);

        if(reservation.isResStatus()){
            resStatusField = new Label("Paid");
        }else{
            resStatusField = new Label("Unpaid");
        }
        c5.addAll(new Label("Status :"), resStatusField);
        dateTimeGroup.addComponent(c5);
        addComponent(dateTimeGroup);

       
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                new myReservation(theme).show();
            }
        };
        getToolbar().addCommandToLeftBar(back);
    }

}

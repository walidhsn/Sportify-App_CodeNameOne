/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author WALID
 */
public class failPage extends Form{
    private Button backButton;

    public failPage(Resources theme) {
        super("Payment Failed");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40);  // Set the background color of the Form
        Label message = new Label("There's an Error in your online payment for your Reservation.");
        add(message);
        backButton = new Button("Back To List");
        backButton.addActionListener(e -> {
            removeAll();
            new myReservation(theme).show();
        });
        add(backButton);
        // Back button on the ToolBar :
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

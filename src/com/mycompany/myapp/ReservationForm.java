/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
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
import com.mycompany.entities.Terrain;
import com.mycompany.services.ServiceReservation;
import com.mycompany.shared.SharedComponents;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author WALID
 */
public class ReservationForm extends Form {

    private Picker datePicker;
    private Picker startTimePicker;
    private Picker endTimePicker;
    private TextField numPeopleField;
    private Button submitButton;
    private Container c1, c2, c3, c4;

    public ReservationForm(Resources theme, Terrain terrain) {
        super("Reservation Form");

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40); 
        c1 = new Container(new FlowLayout(LEFT));
        c2 = new Container(new FlowLayout(LEFT));
        c3 = new Container(new FlowLayout(LEFT));
        c4 = new Container(new FlowLayout(LEFT));
        // Create a component group for the date and time pickers
        // Create a text field for number of people
        numPeopleField = new TextField("", "Number of People", 4, TextField.NUMERIC);
        numPeopleField.setText("1");
        ComponentGroup dateTimeGroup = new ComponentGroup();
        dateTimeGroup.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        c1.addAll(new Label("Reservation Date:"), datePicker);
        dateTimeGroup.addComponent(c1);

        startTimePicker = new Picker();
        startTimePicker.setType(Display.PICKER_TYPE_TIME);
        c2.addAll(new Label("Start Time:"), startTimePicker);
        dateTimeGroup.addComponent(c2);

        endTimePicker = new Picker();
        endTimePicker.setType(Display.PICKER_TYPE_TIME);
        c3.addAll(new Label("End Time:"), endTimePicker);
        dateTimeGroup.addComponent(c3);

        c4.addAll(new Label("Number Of People:"), numPeopleField);
        dateTimeGroup.addComponent(c4);
        addComponent(dateTimeGroup);

        // Create a container for the submit button and text field
        Container submitContainer = new Container();
        submitContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        submitButton = new Button("Book");
        submitButton.setUIID("LoginButton");
        submitContainer.addComponent(submitButton);

        addComponent(submitContainer);
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                new displayTerrains(theme).show();
            }
        };
        getToolbar().addCommandToLeftBar(back);
        // Add a listener to the submit button
        submitButton.addActionListener(evt -> {

            // Get the selected date and time values from the pickers
            Date reservationDate = datePicker.getDate();
            int startTimeMinutes = (Integer) startTimePicker.getValue();
            int endTimeMinutes = (Integer) endTimePicker.getValue();
            // Get the current date and time
            Calendar calendar_c = Calendar.getInstance();
            Date currentDate = calendar_c.getTime();
// Create calendar instances for the reservation date
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(reservationDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(reservationDate);

// Set the start and end times on the calendar instances
            startCalendar.set(Calendar.HOUR_OF_DAY, startTimeMinutes / 60);
            startCalendar.set(Calendar.MINUTE, startTimeMinutes % 60);
            endCalendar.set(Calendar.HOUR_OF_DAY, endTimeMinutes / 60);
            endCalendar.set(Calendar.MINUTE, endTimeMinutes % 60);

// Get the Date objects for the start and end times
            Date startDate = startCalendar.getTime();
            Date endDate = endCalendar.getTime();

            int numPeople = Integer.parseInt(numPeopleField.getText());
            try {
                if (numPeopleField.getText().isEmpty() || numPeopleField.getText().equals("0")) {
                    Dialog.show("Error", "Please fill in all required fields.", "OK", null);
                } else if (reservationDate.getTime() < currentDate.getTime()) {
                    Dialog.show("Error", "Please Select a date in the Future.", "OK", null);
                } else {

                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    //set data : 
                    Reservation reservation = new Reservation();
                    reservation.setDateReservation(reservationDate);
                    reservation.setStartTime(startDate);
                    reservation.setEndTime(endDate);
                    reservation.setNbPerson(numPeople);

                    ServiceReservation.getInstance().ajouterReservation(reservation, SharedComponents.user.getId(), terrain.getId());
                    iDialog.dispose();
                    refreshTheme();
                    removeAll();
                    new displayTerrains(theme).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}

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
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reservation;
import com.mycompany.entities.Terrain;
import com.mycompany.services.ServiceReservation;
import com.mycompany.services.ServiceTerrain;
import com.mycompany.shared.SharedComponents;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WALID
 */
public class myReservation extends Form {

    public myReservation(Resources theme) {
        super("My Reservations");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40); // Set the background color of the Form

        ArrayList<Reservation> reservations = ServiceReservation.getInstance().afficherReservation_idClient(SharedComponents.user.getId());

        for (Reservation reservation : reservations) {
            Container c1, c2, c3, c4, c5;
            FontImage dateIcon = FontImage.createMaterial(FontImage.MATERIAL_DATE_RANGE, "date", 4.5f);
            FontImage timeIcon1 = FontImage.createMaterial(FontImage.MATERIAL_TIMER, "startTime", 4.5f);
            FontImage timeIcon2 = FontImage.createMaterial(FontImage.MATERIAL_TIMER, "endTime", 4.5f);
            FontImage PersonIcon = FontImage.createMaterial(FontImage.MATERIAL_PERSON, "NbPerson", 4.5f);
            FontImage PriceIcon = FontImage.createMaterial(FontImage.MATERIAL_PAYMENTS, "Price", 4.5f);
            FontImage cardIcon = FontImage.createMaterial(FontImage.MATERIAL_PAYMENT, "card", 4.5f);

            // Create the buttons and set their icons
            Button date_btn = new Button("", dateIcon);
            Button time1_btn = new Button("", timeIcon1);
            Button time2_btn = new Button("", timeIcon2);
            Button person_btn = new Button("", PersonIcon);
            Button price_btn = new Button("", PriceIcon);
            Button card_btn = new Button("Pay", cardIcon);

            // Set the UIID of the buttons to "IconButton" to make them look like icon buttons
            date_btn.setUIID("IconButton");
            time1_btn.setUIID("IconButton");
            time2_btn.setUIID("IconButton");
            person_btn.setUIID("IconButton");
            price_btn.setUIID("IconButton");
            card_btn.setUIID("IconButton");
            c1 = new Container(new FlowLayout(LEFT));
            c2 = new Container(new FlowLayout(LEFT));
            c3 = new Container(new FlowLayout(LEFT));
            c4 = new Container(new FlowLayout(LEFT));
            c5 = new Container(new FlowLayout(LEFT));
            float total_price = 0.0f;
            // Create a container for each terrain
            Container reservationContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            reservationContainer.setUIID("ReservationsContainer");
            reservationContainer.setPreferredH(200);
            // Add the name and capacity to the container
            Container detailsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            detailsContainer.setScrollableY(true);
            detailsContainer.setUIID("ReservationsDetails");
            detailsContainer.getAllStyles().setPadding(5, 5, 5, 5); // Set padding
            detailsContainer.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
            detailsContainer.getAllStyles().setBackgroundGradientEndColor(0xffffff); // Set the end color of the gradient to white
            detailsContainer.getAllStyles().setBackgroundGradientStartColor(0xffffff); // Set the start color of the gradient to white
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Label DateLabel = new Label("Reservation Date : " + dateFormat.format(reservation.getDateReservation()));
            Label StartTimeLabel = new Label("Start Time : " + timeFormat.format(reservation.getStartTime()));
            Label EndTimeLabel = new Label("End Time : " + timeFormat.format(reservation.getEndTime()));
            Label NbPersonLabel = new Label("Number of People : " + String.valueOf(reservation.getNbPerson()));
            Label ResStatusLabel = new Label();
            if (reservation.isResStatus()) {
                ResStatusLabel.setText("Status: Payed");
            } else {
                ResStatusLabel.setText("Status: UnPayed");

            }
            List<Terrain> terrains = new ArrayList<>();
            terrains = ServiceTerrain.getInstance().afficherTerrains();
            Terrain t = new Terrain();
            if (!terrains.isEmpty()) {
                for (Terrain terrain : terrains) {
                    if (terrain.getId() == reservation.getTerrain_id()) {
                        t = terrain;
                    }
                }
            }
            total_price = t.getRentPrice() * reservation.getNbPerson();
            Label TotalLabel = new Label("Total : " + String.valueOf(total_price) + " Dt");

            c1.addAll(date_btn, DateLabel);
            c2.addAll(time1_btn, StartTimeLabel);
            c3.addAll(time2_btn, EndTimeLabel);
            c4.addAll(person_btn, NbPersonLabel);
            c5.addAll(price_btn, TotalLabel);
            detailsContainer.addAll(c1, c2, c3, c4, c5);

            FontImage viewIcon = FontImage.createMaterial(FontImage.MATERIAL_VISIBILITY, "View", 4.5f);
            FontImage editIcon = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, "Edit", 4.5f);
            FontImage deleteIcon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, "Delete", 4.5f);

            // Create the buttons and set their icons
            Button view = new Button("View", viewIcon);
            Button edit = new Button("Edit", editIcon);
            Button delete = new Button("Delete", deleteIcon);

            // Set the UIID of the buttons to "IconButton" to make them look like icon buttons
            view.setUIID("IconButton");
            edit.setUIID("IconButton");
            delete.setUIID("IconButton");
            final float total_form = total_price;
            card_btn.addActionListener(e -> {
                removeAll();
                new PaymentForm(theme, total_form, reservation).show();
            });

            view.addActionListener(e -> {

                removeAll();
                new display_Reservation(theme, reservation).show();
            });
            edit.addActionListener(e -> {
                removeAll();
                new editReservation(theme, reservation).show();

            });
            delete.addActionListener(e -> {
                Command yes = new Command("Yes");
                Command no = new Command("No");
                Command[] cmds = new Command[]{yes, no};
                if (Dialog.show("Confirm", "Are you sure you want to delete this terrain?", cmds) == yes) {
                    ServiceReservation.getInstance().DeleteReservation(reservation.getId());
                    // Refresh the terrains list
                    removeAll();
                    new myReservation(theme).show();
                    revalidate();
                }
            });
            reservationContainer.add(detailsContainer);
            reservationContainer.add(view);
            if (!reservation.isResStatus()) {
                reservationContainer.add(edit);
            }
            reservationContainer.add(delete);
            if (!reservation.isResStatus()) {
                reservationContainer.add(card_btn);
            }
            add(reservationContainer);
        }
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                new ProfileForm(theme).show();
            }
        };
        getToolbar().addCommandToLeftBar(back);
    }
}

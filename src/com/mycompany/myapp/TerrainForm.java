/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.OnOffSwitch;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Terrain;
import com.mycompany.services.ServiceTerrain;
import com.mycompany.shared.SharedComponents;

/**
 *
 * @author WALID
 */
public class TerrainForm extends Form {

    private TextField capacityField, postalCodeField, roadNumberField,
            nameField, roadNameField, cityField, countryField,
            rentPriceField;
    private OnOffSwitch disponibilityField;
    private ComboBox<String> sportTypeField;
    private Container c1, c2, c3, c4, c5, c6, c7, c8, c9, c10;

    public TerrainForm(Resources theme) {
        super("Add Terrain", BoxLayout.y());
        getStyle().setBgColor(0x343a40);
        Label nameLabel = new Label("Name:");
        nameField = new TextField("", "Terrain Name", 50, TextField.ANY);
        Label sportTypeLabel = new Label("Sport Type:");
        sportTypeField = new ComboBox<>();
        sportTypeField.addItem("football");
        sportTypeField.addItem("basketball");
        sportTypeField.addItem("handball");
        sportTypeField.addItem("volleyball");
        sportTypeField.addItem("baseball");
        sportTypeField.addItem("tennis");
        sportTypeField.addItem("golf");
        Label capacityLabel = new Label("Capacity:");
        capacityField = new TextField("", "00", 4, TextField.DECIMAL);
        Label rentPriceLabel = new Label("Rent Price:");
        rentPriceField = new TextField("", "00.DT", 7, TextField.NUMERIC);
        Label disponibilityLabel = new Label("Disponibility:");
        disponibilityField = new OnOffSwitch();
        Label postalCodeLabel = new Label("Postal Code:");
        postalCodeField = new TextField("", "Code Postal", 8, TextField.NUMERIC);
        Label roadNameLabel = new Label("Road Name:");
        roadNameField = new TextField("", "Road Name", 50, TextField.ANY);
        Label roadNumberLabel = new Label("Road Number:");
        roadNumberField = new TextField("", "Road Nubmer", 5, TextField.NUMERIC);
        Label cityLabel = new Label("City:");
        cityField = new TextField("", "City", 35, TextField.ANY);
        Label countryLabel = new Label("Country:");
        countryField = new TextField("", "Country", 35, TextField.ANY);
        c1 = new Container(new FlowLayout(LEFT));
        c2 = new Container(new FlowLayout(LEFT));
        c3 = new Container(new FlowLayout(LEFT));
        c4 = new Container(new FlowLayout(LEFT));
        c5 = new Container(new FlowLayout(LEFT));
        c6 = new Container(new FlowLayout(LEFT));
        c7 = new Container(new FlowLayout(LEFT));
        c8 = new Container(new FlowLayout(LEFT));
        c9 = new Container(new FlowLayout(LEFT));
        c10 = new Container(new FlowLayout(LEFT));

        c1.addAll(nameLabel, nameField);
        c2.addAll(sportTypeLabel, sportTypeField);
        c3.addAll(capacityLabel, capacityField);
        c4.addAll(rentPriceLabel, rentPriceField);
        c5.addAll(disponibilityLabel, disponibilityField);
        c6.addAll(postalCodeLabel, postalCodeField);
        c7.addAll(roadNameLabel, roadNameField);
        c8.addAll(roadNumberLabel, roadNumberField);
        c9.addAll(cityLabel, cityField);
        c10.addAll(countryLabel, countryField);

        Button submitButton = new Button("Submit");
        submitButton.setUIID("LoginButton");
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                new ProfileForm(theme).show();
            }
        };
        getToolbar().addCommandToLeftBar(back);
        addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if ("".equals(nameField.getText()) || "".equals(capacityField.getText()) || "".equals(sportTypeField.getSelectedItem()) || "".equals(postalCodeField.getText()) || "".equals(roadNumberField.getText()) || "".equals(roadNameField.getText()) || "".equals(cityField.getText())) {
                        Dialog.show("Error", "Missing Some Informations", "OK", null);
                    } else {
                        InfiniteProgress ip = new InfiniteProgress();
                        final Dialog iDialog = ip.showInfiniteBlocking();

                        Terrain terrain = new Terrain();

                        terrain.setName(nameField.getText());
                        terrain.setCapacity(Integer.parseInt(capacityField.getText()));
                        terrain.setSportType((String) sportTypeField.getSelectedItem());
                        terrain.setRentPrice(Float.parseFloat(rentPriceField.getText()));
                        terrain.setPostalCode(Integer.parseInt(postalCodeField.getText()));
                        terrain.setRoadNumber(Integer.parseInt(roadNumberField.getText()));
                        terrain.setRoadName(roadNameField.getText());
                        terrain.setCity(cityField.getText());
                        terrain.setCountry(countryField.getText());
                        terrain.setDisponibility(disponibilityField.isValue() ? true : false);

                        ServiceTerrain.getInstance().ajouterTerrain(terrain, SharedComponents.user.getId());
                        iDialog.dispose();
                        refreshTheme();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}

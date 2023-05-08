/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Terrain;
import com.mycompany.utils.Statics;

/**
 *
 * @author WALID
 */
public class Terrain_details extends Form {

    private Container c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;

    public Terrain_details(Resources theme, Terrain terrainDetails) {
        super("Terrain Details");

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        // Create a ComponentGroup to hold the labels
        ComponentGroup labelGroup = new ComponentGroup();
        labelGroup.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        String imgUrl = Statics.BASE_URL + "/uploads/terrain/";

        // Add the image to the container
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(getWidth() / 4, getHeight() / 4, 0xffcccccc), true);
        URLImage imageUrl = URLImage.createToStorage(placeholder, terrainDetails.getName(), imgUrl + terrainDetails.getImageName());
        ImageViewer imageViewer = new ImageViewer(imageUrl.scaled(getWidth() / 4, getHeight() / 4));

        // Create labels for each property
        Label nameLabel = new Label("Name:");
        Label sportTypeLabel = new Label("Sport Type:");
        Label roadNameLabel = new Label("Road Name:");
        Label roadNumberLabel = new Label("Road Number:");
        Label postalCodeLabel = new Label("Postal Code:");
        Label cityLabel = new Label("City:");
        Label countryLabel = new Label("Country:");
        Label capacityLabel = new Label("Capacity:");
        Label rentPriceLabel = new Label("Rent Price:");
        Label disponibilityLabel = new Label("Disponibility:");
        Label updatedAtLabel = new Label("Updated At:");

// Create fields for each property
        Label nameValueLabel = new Label(terrainDetails.getName());
        Label sportTypeValueLabel = new Label(terrainDetails.getSportType());
        Label roadNameValueLabel = new Label(terrainDetails.getRoadName());
        Label roadNumberValueLabel = new Label(String.valueOf(terrainDetails.getRoadNumber()));
        Label postalCodeValueLabel = new Label(String.valueOf(terrainDetails.getPostalCode()));
        Label cityValueLabel = new Label(terrainDetails.getCity());
        Label countryValueLabel = new Label(terrainDetails.getCountry());
        Label capacityValueLabel = new Label(String.valueOf(terrainDetails.getCapacity()));
        Label rentPriceValueLabel = new Label(String.valueOf(terrainDetails.getRentPrice()) + " Dt");
        Label disponibilityValueLabel = new Label("");
        if (terrainDetails.isDisponibility()) {
            disponibilityValueLabel.setText("- YES -");
        } else {
            disponibilityValueLabel.setText("- NO -");
        }

        Label updatedAtValueLabel = new Label(terrainDetails.getUpdatedAt().toString());
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
        c11 = new Container(new FlowLayout(LEFT));

        c1.addAll(nameLabel, nameValueLabel);
        c2.addAll(sportTypeLabel, sportTypeValueLabel);
        c3.addAll(roadNameLabel, roadNameValueLabel);
        c4.addAll(roadNumberLabel, roadNumberValueLabel);
        c5.addAll(postalCodeLabel, postalCodeValueLabel);
        c6.addAll(cityLabel, cityValueLabel);
        c7.addAll(countryLabel, countryValueLabel);
        c8.addAll(capacityLabel, capacityValueLabel);
        c9.addAll(rentPriceLabel, rentPriceValueLabel);
        c10.addAll(disponibilityLabel, disponibilityValueLabel);
        c11.addAll(updatedAtLabel, updatedAtValueLabel);
        // Add Labels to the Component Group : 
        labelGroup.addComponent(c1);

        labelGroup.addComponent(c2);

        labelGroup.addComponent(c3);

        labelGroup.addComponent(c4);

        labelGroup.addComponent(c5);

        labelGroup.addComponent(c6);
        
        labelGroup.addComponent(c7);

        labelGroup.addComponent(c8);
       
        labelGroup.addComponent(c9);    

        labelGroup.addComponent(c10);
       
        labelGroup.addComponent(c11);
        
        labelGroup.addComponent(imageViewer);
        
        addComponent(labelGroup);
        
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                new ownerList(theme).show();
            }
        };
        getToolbar().addCommandToLeftBar(back);

    }
}

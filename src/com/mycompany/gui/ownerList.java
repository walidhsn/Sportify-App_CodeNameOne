/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Terrain;
import com.mycompany.services.ServiceTerrain;
import com.mycompany.utils.Statics;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 *
 * @author WALID
 */
public class ownerList extends Form {

    public ownerList(Resources theme) {
        super("Terrains");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x29293d); // Set the background color of the Form
        ArrayList<Terrain> terrains = ServiceTerrain.getInstance().afficherTerrains_owner(2);
        String imgUrl = Statics.BASE_URL + "/uploads/terrain/";

        for (Terrain terrain : terrains) {
            // Create a container for each terrain
            Container terrainContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            terrainContainer.setUIID("TerrainsContainer");
            terrainContainer.setPreferredH(200);

            // Add the image to the container
            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(getWidth() / 4, getHeight() / 4, 0xffcccccc), true);
            URLImage imageUrl = URLImage.createToStorage(placeholder, terrain.getName(), imgUrl + terrain.getImageName());
            ImageViewer imageViewer = new ImageViewer(imageUrl.scaled(getWidth() / 4, getHeight() / 4));
            terrainContainer.add(imageViewer);

            // Add the name and capacity to the container
            Container detailsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            detailsContainer.setScrollableY(true);
            detailsContainer.setUIID("TerrainsDetails");
            detailsContainer.getAllStyles().setPadding(5, 5, 5, 5); // Set padding
            detailsContainer.getAllStyles().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
            detailsContainer.getAllStyles().setBackgroundGradientEndColor(0xffffff); // Set the end color of the gradient to white
            detailsContainer.getAllStyles().setBackgroundGradientStartColor(0xffffff); // Set the start color of the gradient to white
            Label nameLabel = new Label(terrain.getName());
            Label rentPriceLabel = new Label("Rent Price: " + terrain.getRentPrice() + ".DT");
            Label roadLabel = new Label("Road: " + terrain.getRoadName());
            Label cityLabel = new Label("City: " + terrain.getCity());
            Label countryLabel = new Label("Country: " + terrain.getCountry());
            Label sportTypeLabel = new Label("Sport Type: " + terrain.getSportType());
            Label capacityLabel = new Label("Capacity: " + terrain.getCapacity());
            detailsContainer.addAll(nameLabel, rentPriceLabel, roadLabel, cityLabel, countryLabel, sportTypeLabel, capacityLabel);

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
            //bookButton.setUIID("LoginButton");
            view.addActionListener(e -> {
                //new BookingForm(theme, terrain).show();
            });
            edit.addActionListener(e -> {
                try {
                    new EditTerrain(theme,terrain.getId()).show();
                } catch (UnsupportedEncodingException ex) {
                    
                }
            });
            delete.addActionListener(e -> {
                Command yes = new Command("Yes");
                Command no = new Command("No");
                Command[] cmds = new Command[]{yes, no};
                if (Dialog.show("Confirm", "Are you sure you want to delete this terrain?", cmds) == yes) {
                    ServiceTerrain.getInstance().supprimerTerrain(terrain.getId());
                    // Refresh the terrains list
                    removeAll();
                    new ownerList(theme).show();
                    revalidate();
                }
            });
            terrainContainer.add(detailsContainer);
            terrainContainer.add(view);
            terrainContainer.add(edit);
            terrainContainer.add(delete);

            add(terrainContainer);
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

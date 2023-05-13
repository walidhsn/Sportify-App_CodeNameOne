package com.mycompany.gui;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Categorie;
import com.mycompany.services.ServiceCategorie;
import java.util.ArrayList;

/**
 *
 * @author [Your Name]
 */
public class ListCatsForm extends Form {

    private Resources resources;
    private Form current;

    public ListCatsForm(Resources res) {
        resources = res;
        setTitle("Categories");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        ArrayList<Categorie> categories = ServiceCategorie.getInstance().getAllCategories();
        if (categories != null) {
            for (Categorie categorie : categories) {
                Container card = createCategoryCard(categorie);
                addComponent(card);
            }
        }
        System.out.println("Number of categories retrieved from the database: " + categories.size());
    }

    private Container createCategoryCard(Categorie categorie) {
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

        Label nameLabel = new Label(categorie.getName());
        nameLabel.getUnselectedStyle().setFont(fnt);
        nameLabel.getUnselectedStyle().setAlignment(Component.CENTER);

        

        Button editButton = new Button("Edit");
        editButton.addActionListener(e -> {
            new editCat(resources, categorie).show();
        });

        Container card = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        card.getStyle().setPadding(Component.TOP, 5);
        card.getStyle().setPadding(Component.BOTTOM, 5);
        card.getStyle().setPadding(Component.LEFT, 5);
        card.getStyle().setPadding(Component.RIGHT, 5);
        card.getStyle().setBorder(Border.createLineBorder(2, ColorUtil.rgb(66, 66, 66)));
        card.getStyle().setBgTransparency(255);
        card.getStyle().setBgColor(ColorUtil.WHITE);
        card.addComponent(nameLabel);
      //  card.addComponent(descriptionLabel);
        card.addComponent(editButton);

        return card;
    }
}

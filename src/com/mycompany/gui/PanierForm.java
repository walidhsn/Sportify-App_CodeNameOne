package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.CardItem;
import java.util.List;

// ...

public class PanierForm extends com.codename1.ui.Form {
    private Container vboxCardItems;
    private TextField totalTextField;
    private Resources resources;
    
    public PanierForm(List<CardItem> parsedCardItems) {
        super(new BorderLayout());

        // Create the main content container
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        // Create the VBox container for the card items
        vboxCardItems = new Container(new BoxLayout(BoxLayout.Y_AXIS));
final float[] total = { 0.0f };        
//float total = 0.0f;
       // float tt = 0.0f;
        // Iterate over the card items and display them
        for (CardItem cardItem : parsedCardItems) {
            Container cardItemContainer = new Container(new BorderLayout());

            // Create labels for quantity, libelle, and price
            Label quantityLabel = new Label("Quantity: " + cardItem.getQuantite());
            Label libelleLabel = new Label("Libelle: " + cardItem.getLibelle());
            Label priceLabel = new Label("Price: " + cardItem.getPrix());

            // Add labels to the card item container
            cardItemContainer.add(BorderLayout.NORTH, quantityLabel);
            cardItemContainer.add(BorderLayout.CENTER, libelleLabel);
            cardItemContainer.add(BorderLayout.SOUTH, priceLabel);
            total[0]+=cardItem.getQuantite()*cardItem.getPrix();
             // tt=total;
            // Add the card item container to the main VBox container
            vboxCardItems.add(cardItemContainer);
        }
        content.add(vboxCardItems);

        // Create the "Commander" button
        Button commanderBtn = new Button("Commander");
        content.add(commanderBtn);
        commanderBtn.addActionListener(e -> {
            new CommandForm(1,2,total[0]).show();
        });
        // Create the "Supprimer Card" button
        Button supprimerCardBtn = new Button("Supprimer Card");
        supprimerCardBtn.setUIID("SupprimerCardButton"); // Set UIID for custom styling if needed
        content.add(supprimerCardBtn);

        // Create the GridPane for the total label and text field
        Container gridPane = new Container(new BorderLayout());
        Label totalLabel = new Label("Total :");
        totalTextField = new TextField();
        totalTextField.setEditable(false);
        totalTextField.setText(String.valueOf(total)+ " Dt");
        gridPane.add(BorderLayout.WEST, totalLabel);
        gridPane.add(BorderLayout.CENTER, totalTextField);
        content.add(gridPane);

        // Create the "Liste produit" button
        Button listeProduitBtn = new Button("Liste produit");
        content.add(listeProduitBtn);
        listeProduitBtn.addActionListener(e -> {
            new ListArticlesForm2(resources).show();
        });
        // Add the main content container to the form
        add(BorderLayout.CENTER, content); // Specify BorderLayout.CENTER constraint

        // Set styles
        Style style = getAllStyles();
        style.setBgColor(0xFFFFFF);

        // Set form title
        setTitle("Panier");

        // Refresh the UI
        revalidate();
    }
}

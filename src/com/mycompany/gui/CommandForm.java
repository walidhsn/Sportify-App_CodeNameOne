package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.mycompany.entities.Commande;
import com.mycompany.services.CardService;

public class CommandForm extends Form {
    private int cardId;
    private int userId;
    private float total;

    private TextField firstnameField;
    private TextField lastnameField;
    private TextField emailField;
    private TextField telField;
    private TextField adresseField;
    private TextField cityField;

    public CommandForm(int cardId, int userId , float total) {
        this.cardId = cardId;
        this.userId = userId;
        this.total=total;
        setTitle("Command Form");

        firstnameField = new TextField();
        addComponent(new Label("First Name:"));
        addComponent(firstnameField);

        lastnameField = new TextField();
        addComponent(new Label("Last Name:"));
        addComponent(lastnameField);

        emailField = new TextField();
        addComponent(new Label("Email:"));
        addComponent(emailField);

        telField = new TextField();
        addComponent(new Label("Tel:"));
        addComponent(telField);

        adresseField = new TextField();
        addComponent(new Label("Adresse:"));
        addComponent(adresseField);

        cityField = new TextField();
        addComponent(new Label("City:"));
        addComponent(cityField);

        Button submitButton = new Button("Submit");
        addComponent(submitButton);

        Button checkoutButton = new Button("Checkout");
        addComponent(checkoutButton);

        submitButton.addActionListener((evt) -> {
            performCheckout();
        });

        checkoutButton.addActionListener((evt) -> {
            performCheckout();
        });
    }

    private void performCheckout() {
        // Retrieve the form field values
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String email = emailField.getText();
        String tel = telField.getText();
        String adresse = adresseField.getText();
        String city = cityField.getText();
         // Replace with your logic to calculate the total

        // Create a new Command instance with the retrieved values
        Commande commande = new Commande();
        commande.setCard_id(cardId);
        commande.setUser_id(userId);
        commande.setFirstname(firstname);
        commande.setLastname(lastname);
        commande.setEmail(email);
        commande.setTel(tel);
        commande.setAdresse(adresse);
        commande.setCity(city);
        commande.setTotal(total);

        // Perform any necessary validation or processing with the command instance

        // Call the checkout method
        CardService.getInstance().checkout(cardId, userId,commande);
       
    }
    private float calculateTotal() {
        // Replace with your logic to calculate the total
        return 0.0f;
    }
}

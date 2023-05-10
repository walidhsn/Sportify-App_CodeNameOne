/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author WALID
 */
public class Payment extends Form {

    private Container c1, c2, c3, c4, c5, c6;
    private TextField nameField, emailField, cardNumberField, cvcField;
    private ComboBox<String> monthField, yearField;
    private Button submitButton;

    public Payment(Resources theme, float total) {
        super("Payment");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40);  // Set the background color of the Form
        Label nameLabel = new Label("Name :");
        Label emailLabel = new Label("Email :");
        Label cardNumberLabel = new Label("Card Number :");
        Label cvcLabel = new Label("CVC :");
        Label monthLabel = new Label("Month :");
        Label yearLabel = new Label("Year :");

        nameField = new TextField("", "Name", 20, TextField.ANY);
        emailField = new TextField("", "exemple@gmail.com", 40, TextField.EMAILADDR);
        cardNumberField = new TextField("", "4242 4242 4242 4242", 20, TextField.NUMERIC);
        cvcField = new TextField("", "123", 4, TextField.NUMERIC);
        monthField = new ComboBox<>("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        yearField = new ComboBox<>("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040");
        submitButton = new Button("Submit");
        submitButton.addActionListener(e -> {
            // Code for processing the payment goes here
        });
        c1.addAll(nameLabel,nameField);
        c2.addAll(emailLabel,emailField);
        c3.addAll(cardNumberLabel,cardNumberField);
        c4.addAll(monthLabel,monthField);
        c5.addAll(yearLabel,yearField);
        c6.addAll(cvcLabel,cvcField);

        ComponentGroup group = new ComponentGroup();
        group.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        group.addAll(c1,c2,c3,c4,c5,c6,submitButton);
        add(group);
    }

}

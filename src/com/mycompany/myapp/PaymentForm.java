/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reservation;
import com.mycompany.services.PaymentProcessor;
import com.mycompany.services.ServiceReservation;
import com.stripe.exception.StripeException;
import java.util.Calendar;

/**
 *
 * @author WALID
 */
public class PaymentForm extends Form {

    private Container c1, c2, c3, c4, c5, c6,c7;
    private TextField nameField, emailField, cardNumberField, cvcField;
    private ComboBox<String> monthField, yearField;
    private Button submitButton;

    public PaymentForm(Resources theme, float total, Reservation reservation) {
        super("Payment Form");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0x343a40);  // Set the background color of the Form
        c1 = new Container(new FlowLayout(LEFT));
        c2 = new Container(new FlowLayout(LEFT));
        c3 = new Container(new FlowLayout(LEFT));
        c4 = new Container(new FlowLayout(LEFT));
        c5 = new Container(new FlowLayout(LEFT));
        c6 = new Container(new FlowLayout(LEFT));
        c7 = new Container(new FlowLayout(LEFT));
        Label nameLabel = new Label("Name :");
        Label emailLabel = new Label("Email :");
        Label cardNumberLabel = new Label("Card Number :");
        Label cvcLabel = new Label("CVC :");
        Label monthLabel = new Label("Month :");
        Label yearLabel = new Label("Year :");
        Label totalLabel = new Label("Total : "+ String.valueOf(total) + " Dt.");
        
        nameField = new TextField("", "Name", 20, TextField.ANY);
        emailField = new TextField("", "exemple@gmail.com", 40, TextField.EMAILADDR);
        cardNumberField = new TextField("", "4242 4242 4242 4242", 20, TextField.NUMERIC);
        cvcField = new TextField("", "123", 4, TextField.NUMERIC);
        monthField = new ComboBox<>("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        yearField = new ComboBox<>("2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040");
        submitButton = new Button("Submit");
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String cardNumber = cardNumberField.getText();
            String cvc = cvcField.getText();
            int month = Integer.parseInt(monthField.getSelectedItem());
            int year = Integer.parseInt(yearField.getSelectedItem());

            if (name.isEmpty()) {
                Dialog.show("Error", "Please You need to input your Name.", "OK", null);
            } else if (email.isEmpty()) {
                Dialog.show("Error", "Please You need to input your Email.", "OK", null);
            } else if (!verifyEmail(email)) {
                Dialog.show("Error", "Please enter a valid Email address.", "OK", null);
            } else if (cardNumber.isEmpty()) {
                Dialog.show("Error", "You need to input your Card Number.", "OK", null);
            } else if (!verifyCardNumber(cardNumber)) {
                Dialog.show("Error", "Please enter a valid Card Number.", "OK", null);
            } else if (!verifyDate(month, year)) {
                Dialog.show("Error", "Please enter a valid expiration date.", "OK", null);
            } else if (cvc.isEmpty()) {
                Dialog.show("Error", "Please You need to input your CVC Number.", "OK", null);
            } else if (!verifyCvc(cvc)) {
                Dialog.show("Error", "Please enter a valid CVC Number.", "OK", null);
            } else {
                try {
                    
                    boolean payment_result = PaymentProcessor.processPayment(name, email, total, cardNumber, month, year, cvc);
                    
                    if (payment_result) {
                        Dialog.show("Success", "Successful Payment.", "OK", null);
                        reservation.setResStatus(true);
                        ServiceReservation.getInstance().UpdateReservation_payment(reservation);
                        removeAll();
                        new successPage(theme, reservation).show();
                    } else {
                        Dialog.show("Error", "Payment Failed.", "OK", null);
                        removeAll();
                        new failPage(theme).show();
                    }
                } catch (StripeException ex) {
                    ex.printStackTrace();
                }
            }
        });
        c1.addAll(nameLabel, nameField);
        c2.addAll(emailLabel, emailField);
        c3.addAll(cardNumberLabel, cardNumberField);
        c4.addAll(monthLabel, monthField);
        c5.addAll(yearLabel, yearField);
        c6.addAll(cvcLabel, cvcField);
        c7.add(totalLabel);
        ComponentGroup group = new ComponentGroup();
        group.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        group.addAll(c1, c2, c3, c4, c5, c6,c7, submitButton);
        add(group);
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                removeAll();
                new myReservation(theme).show();
            }
        };
        getToolbar().addCommandToLeftBar(back);
    }

    private static boolean verifyCardNumber(String cardNumber) {
        if (cardNumber.length() != 16) {
            return false;
        }
        try {
            Long.parseLong(cardNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        int sum = 0;
        boolean doubleDigit = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = cardNumber.charAt(i) - '0';
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        return (sum % 10 == 0);
    }

    private static boolean verifyDate(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        return (year > currentYear || (year == currentYear && month >= currentMonth));
    }

    private static boolean verifyCvc(String cvc) {
        if (cvc.length() != 3) {
            return false;
        }
        try {
            Integer.parseInt(cvc);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

  private static boolean verifyEmail(String email) {
    if (email == null || email.isEmpty()) {
        return false;
    }
    int atIndex = email.indexOf('@');
    int dotIndex = email.lastIndexOf('.');
    if (atIndex <= 0 || dotIndex <= atIndex || dotIndex == email.length() - 1) {
        return false;
    }
    String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@._-";
    for (int i = 0; i < email.length(); i++) {
        char c = email.charAt(i);
        if (validChars.indexOf(c) == -1) {
            return false;
        }
    }
    return true;
}


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Academy;
import com.mycompany.entities.Coach;
import com.mycompany.services.AcademyService;
import com.mycompany.services.CoachService;

public class AddCoachForm extends Form {
    private CoachService coachService = CoachService.getInstance();
    private AcademyService academyService = AcademyService.getInstance();
    private CoachForm previous;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private ComboBox<String> academyComboBox;
    

    public AddCoachForm(CoachForm previous, Resources theme) {
        this.previous = previous;
        setTitle("Add Coach");
        setLayout(new BorderLayout());
        // Create the back command
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show the previous form
                previous.showBack();
            }
        };
        getToolbar().addCommandToLeftBar(back);

        nameField = new TextField("", "Name", 20, TextField.ANY);
        emailField = new TextField("", "Email", 20, TextField.EMAILADDR);
//        emailField = new TextField("", "Email", 20, TextField.ANY);
        phoneField = new TextField("", "Phone", 20, TextField.PHONENUMBER);
//        phoneField = new TextField("", "Phone", 20, TextField.ANY);
        academyComboBox = new ComboBox<>("");

            // Call the method to add items to the ComboBox
            addAcademiesToComboBox();

        Button addButton = new Button("Add");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            if (name == null || name.length() == 0) {
                Dialog.show("Error", "Name is required", "OK", null);
            } else if (email == null || email.length() == 0) {
                Dialog.show("Error", "Email is required", "OK", null);
            } else if (phone == null || phone.length() == 0) {
                Dialog.show("Error", "Phone is required", "OK", null);
            } else {
            coachService.addCoach(new Coach(name, email,phone));
                Dialog.show("Success", "Coach added", "OK", null);
                previous.updateCoachesList();
                previous.showBack();
            }
        });
        

        Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        formContainer.getStyle().setMarginTop(20);
        formContainer.getStyle().setMarginLeft(20);
        formContainer.getStyle().setMarginRight(20);

        formContainer.add(nameField);
        formContainer.add(emailField);
        formContainer.add(phoneField);
        formContainer.add(academyComboBox);
        formContainer.add(addButton);

        add(BorderLayout.CENTER, formContainer);
    }
    private void addAcademiesToComboBox() {
        for (Academy academy : academyService.getAllAcademies()) {
            academyComboBox.addItem(academy.getName());
        }
    }
}

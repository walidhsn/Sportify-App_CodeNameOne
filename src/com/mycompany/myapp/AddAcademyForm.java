/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

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
import com.mycompany.services.AcademyService;

public class AddAcademyForm extends Form {
    
    private AcademyService academyService = AcademyService.getInstance();
    private AcademyForm previous;
    private ComboBox<String> categoryField;

    public AddAcademyForm(AcademyForm previous, Resources theme) {
        this.previous = previous;
        setTitle("Add Academy");
        setLayout(new BorderLayout());
        // Create the back command
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show the previous form
                previous.showBack();
            }
        };

        // Add the back command to the toolbar
        getToolbar().addCommandToLeftBar(back);

        TextField nameField = new TextField("", "Academy Name", 20, TextField.ANY);
        categoryField = new ComboBox<>("Football", "Handball", "Basketball", "Volleyball", "Tennis", "Golf");
        Button saveButton = new Button("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String category = categoryField.getSelectedItem();
            if (name == null || name.length() == 0) {
                Dialog.show("Error", "Name is required", "OK", null);
            } else if (category == null || category.length() == 0) {
                Dialog.show("Error", "Category is required", "OK", null);
            } else {
                academyService.addAcademy(new Academy(name, category));
                Dialog.show("Success", "Academy added", "OK", null);
                previous.updateAcademiesList();
                previous.showBack();
            }
        });

        Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        formContainer.getStyle().setMarginTop(20);
        formContainer.getStyle().setMarginLeft(20);
        formContainer.getStyle().setMarginRight(20);

        formContainer.add(nameField);
        formContainer.add(categoryField);
        formContainer.add(saveButton);

        add(BorderLayout.CENTER, formContainer);
    }
}

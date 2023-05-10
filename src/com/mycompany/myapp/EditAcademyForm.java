package com.mycompany.myapp;


import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import static com.codename1.ui.events.ActionEvent.Type.Theme;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Academy;
import com.mycompany.services.AcademyService;


public class EditAcademyForm extends Form {
    public EditAcademyForm(Form previous, AcademyService service, Academy academy) {
        setTitle("Edit Academy");
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show the previous form
                previous.showBack();
            }
        };
        getToolbar().addCommandToLeftBar(back);
        TextField name = new TextField("", academy.getName());
        
        ComboBox<String> category = new ComboBox<>("Category",
                "Football", "Handball", "Basketball", "Volleyball", "Tennis", "Golf");
        category.setSelectedItem(academy.getCategory());

        Button saveButton = new Button("Save");
        saveButton.addActionListener(e -> {
            if (name == null || name.getText().length() == 0) {
                    Dialog.show("Error", "Name is required", "OK", null);
                
                } else {
                academy.setName(name.getText());
                academy.setCategory(category.getSelectedItem());
                Dialog.show("Success", "Academy Updated", "OK", null);
                service.updateAcademy(academy);
                }
            // Refresh the academies list on the previous form
            if (previous instanceof AcademyForm) {
                ((AcademyForm) previous).updateAcademiesList();
            }
            
            previous.showBack();
        });

        addAll(name, category, saveButton);
    }
}

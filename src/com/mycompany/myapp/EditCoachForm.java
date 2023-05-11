package com.mycompany.myapp;


import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import static com.codename1.ui.events.ActionEvent.Type.Theme;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Academy;
import com.mycompany.entities.Coach;
import com.mycompany.services.AcademyService;
import com.mycompany.services.CoachService;


public class EditCoachForm extends Form {
    private CoachService coachService = CoachService.getInstance();
    private CoachForm previous;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private AcademyService academyService = AcademyService.getInstance();
    private ComboBox<String> academyComboBox;
    
    public EditCoachForm(Form previous, CoachService service, Coach coach) {
        setTitle("Edit Coach");
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Show the previous form
                previous.showBack();
            }
        };
        getToolbar().addCommandToLeftBar(back);
        TextField name = new TextField("", coach.getName());
        TextField email = new TextField("", coach.getEmail());
        TextField phone = new TextField("", coach.getPhone());
        academyComboBox = new ComboBox<>("");

            // Call the method to add items to the ComboBox
            addAcademiesToComboBox();

        Button saveButton = new Button("Save");
        saveButton.addActionListener(e -> {
            coach.setName(name.getText());
            coach.setEmail(email.getText());
            coach.setPhone(phone.getText());
            Dialog.show("Success", "Coach Updated", "OK", null);
            service.updateCoach(coach);
            
            // Refresh the academies list on the previous form
            if (previous instanceof CoachForm) {
                ((CoachForm) previous).updateCoachesList();
            }
            
            previous.showBack();
        });

        addAll(name, email, phone, academyComboBox, saveButton);
    }
    private void addAcademiesToComboBox() {
        for (Academy academy : academyService.getAllAcademies()) {
            academyComboBox.addItem(academy.getName());
        }
    }
}

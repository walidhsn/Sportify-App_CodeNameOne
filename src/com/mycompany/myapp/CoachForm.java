package com.mycompany.myapp;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Academy;
import com.mycompany.entities.Coach;
import com.mycompany.services.CoachService;

import java.util.ArrayList;

public class CoachForm extends Form {

    private CoachService coachService = CoachService.getInstance();
    private Container coachesListContainer;
    private ArrayList<Coach> coaches;

    public CoachForm(Form previous, Resources theme) {
        setTitle("Coaches List");
        setLayout(new BorderLayout());

        // Back command
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                previous.showBack();
            }
        };
        getToolbar().addCommandToLeftBar(back);

        // Add Coach button
        Button addButton = new Button("Add Coach");
        addButton.addActionListener(e -> new AddCoachForm(this, theme).show());

        // Academies list container
        coaches = coachService.getAllCoaches();
        coachesListContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        for (Coach academy : coaches) {
            Container academyContainer = createCoachContainer(academy);
            coachesListContainer.add(academyContainer);
        }

        // Search command
        this.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            searchAcademies(text);
        }, 4);

        add(BorderLayout.CENTER, coachesListContainer);
        add(BorderLayout.SOUTH, addButton);
    }

    private Container createCoachContainer(Coach academy) {
        Container academyContainer = new Container(new BorderLayout());
        academyContainer.getStyle().setMargin(20, 20, 20, 20);

        // Coach label
        SpanLabel academyLabel = new SpanLabel(academy.getName() + " (" + academy.getEmail() + ")"+ " (" + academy.getPhone() + ")");
        academyLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        academyContainer.add(BorderLayout.CENTER, academyLabel);

        // Buttons container
        Container buttonsContainer = new Container(new FlowLayout(Component.CENTER));
        Button editButton = new Button("Edit");
        editButton.addActionListener(event -> new EditCoachForm(this, coachService, academy).show());

        Button deleteButton = new Button("Delete");
        deleteButton.addActionListener(event -> {
            Command yes = new Command("Yes");
            Command no = new Command("No");
            Command[] commands = new Command[]{yes, no};
            if (Dialog.show("Confirm", "Are you sure you want to delete this Coach?", commands) == yes) {
                coachService.deleteCoach(academy.getId());
                coachesListContainer.removeComponent(academyContainer);
                coachesListContainer.animateLayout(150);
            }
        });

        buttonsContainer.add(editButton);
        buttonsContainer.add(deleteButton);

        academyContainer.add(BorderLayout.SOUTH, buttonsContainer);
        return academyContainer;
    }

    private void searchAcademies(String text) {
        if (text == null || text.length() == 0) {
            // clear search
            coachesListContainer.removeAll();
            for (Coach academy : coaches) {
                Container academyContainer = createCoachContainer(academy);
                coachesListContainer.add(academyContainer);
            }
            coachesListContainer.animateLayout(150);
        } else {
            text = text.toLowerCase();
            coachesListContainer.removeAll();
            for (Coach academy : coaches) {
                if (academy.getName().toLowerCase().contains(text) || academy.getEmail().toLowerCase().contains(text)) {
                    Container academyContainer = createCoachContainer(academy);
                    coachesListContainer.add(academyContainer);
                }
            }
            coachesListContainer.animateLayout(150);
        }
    }
    
    public void updateCoachesList() {
        coaches = coachService.getAllCoaches();
        coachesListContainer.removeAll();
        for (Coach coach : coaches) {
            Container academyContainer = createCoachContainer(coach);
            coachesListContainer.add(academyContainer);
        }
        coachesListContainer.animateLayout(150);
    }


    private void installSidemenu(Resources theme) {
        Toolbar tb = getToolbar();
        tb.addMaterialCommandToSideMenu("Coaches", FontImage.MATERIAL_HOME, e -> {
            new CoachForm(this, theme).show();
        });
    }
}

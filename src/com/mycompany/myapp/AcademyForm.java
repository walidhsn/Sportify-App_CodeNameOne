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
import com.mycompany.services.AcademyService;

import java.util.ArrayList;

public class AcademyForm extends Form {

    private AcademyService academyService = AcademyService.getInstance();
    private Container academiesListContainer;
    private ArrayList<Academy> academies;

    public AcademyForm(Form previous, Resources theme) {
        setTitle("Academies List");
        setLayout(new BorderLayout());

        // Back command
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                previous.showBack();
            }
        };
        getToolbar().addCommandToLeftBar(back);

        // Add Academy button
        Button addButton = new Button("Add Academy");
        addButton.addActionListener(e -> new AddAcademyForm(this, theme).show());

        // Academies list container
        academies = academyService.getAllAcademies();
        academiesListContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        for (Academy academy : academies) {
            Container academyContainer = createAcademyContainer(academy);
            academiesListContainer.add(academyContainer);
        }

        // Search command
        this.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            searchAcademies(text);
        }, 4);

        add(BorderLayout.CENTER, academiesListContainer);
        add(BorderLayout.SOUTH, addButton);
    }

    private Container createAcademyContainer(Academy academy) {
        Container academyContainer = new Container(new BorderLayout());
        academyContainer.getStyle().setMargin(20, 20, 20, 20);

        // Academy label
        SpanLabel academyLabel = new SpanLabel(academy.getName() + " (" + academy.getCategory() + ")");
        academyLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        academyContainer.add(BorderLayout.CENTER, academyLabel);

        // Buttons container
        Container buttonsContainer = new Container(new FlowLayout(Component.CENTER));
        Button editButton = new Button("Edit");
        editButton.addActionListener(event -> new EditAcademyForm(this, academyService, academy).show());

        Button deleteButton = new Button("Delete");
        deleteButton.addActionListener(event -> {
            Command yes = new Command("Yes");
            Command no = new Command("No");
            Command[] commands = new Command[]{yes, no};
            if (Dialog.show("Confirm", "Are you sure you want to delete this Academy?", commands) == yes) {
                academyService.deleteAcademy(academy.getId());
                academiesListContainer.removeComponent(academyContainer);
                academiesListContainer.animateLayout(150);
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
            academiesListContainer.removeAll();
            for (Academy academy : academies) {
                Container academyContainer = createAcademyContainer(academy);
                academiesListContainer.add(academyContainer);
            }
            academiesListContainer.animateLayout(150);
        } else {
            text = text.toLowerCase();
            academiesListContainer.removeAll();
            for (Academy academy : academies) {
                if (academy.getName().toLowerCase().contains(text) || academy.getCategory().toLowerCase().contains(text)) {
                    Container academyContainer = createAcademyContainer(academy);
                    academiesListContainer.add(academyContainer);
                }
            }
            academiesListContainer.animateLayout(150);
        }
    }
    
    public void updateAcademiesList() {
        academies = academyService.getAllAcademies();
        academiesListContainer.removeAll();
        for (Academy academy : academies) {
            Container academyContainer = createAcademyContainer(academy);
            academiesListContainer.add(academyContainer);
        }
        academiesListContainer.animateLayout(150);
    }

    private void installSidemenu(Resources theme) {
        Toolbar tb = getToolbar();
        tb.addMaterialCommandToSideMenu("Academies", FontImage.MATERIAL_HOME, e -> {
            new AcademyForm(this, theme).show();
        });
    }
}

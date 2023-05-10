/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import models.category;
import services.Category_Service;

/**
 *
 * @author MOLKA
 */
public class Admin_CategoryForm  extends Form {

    Resources theme = UIManager.initFirstTheme("/theme_1");

    public Admin_CategoryForm(Form previous) {
        super("Category", BoxLayout.y());

        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();

                try {
                    for (category c : new Category_Service().getAllcategorys()) {
                        this.add(addIteam_category(c));
                    }
                } catch (Exception ex) {

                }

                this.revalidate();
            });
        });

        this.getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : this.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                this.getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : this.getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1
                            || line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                this.getContentPane().animateLayout(150);
            }
        }, 4);
         this.getToolbar().addCommandToLeftBar("Back", null, ev -> {
           Admin_InterfaceForm w = new Admin_InterfaceForm(previous);
        w.showBack();
         });
        this.getToolbar().addCommandToOverflowMenu("Add Category", null, ev -> {

            Form ajout = new Form("ADD Category", BoxLayout.y());
            ajout.getToolbar().addCommandToLeftBar("back", null, evx -> {
                this.showBack();
            });
            TextField name = new TextField("", "name", 20, TextArea.ANY);
                       String text_saisir_des_caracteres = "^[0-9]+$";
            Validator val_name = new Validator();
            val_name.addConstraint(name, new LengthConstraint(8));

            val_name.addConstraint(name, new RegexConstraint(text_saisir_des_caracteres, ""));

           
            ajout.add("Name : ").add(name);
          

            Button submit = new Button("Submit");
            ajout.add(submit);

            submit.addActionListener(aj
                    -> {
                if (name.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de name ", "OK", null);

                } else if (val_name.isValid()) {
                    Dialog.show("Erreur name !", "il faut saisir des caracteres  !", "OK", null);
                }  else {
                    category s = new category(name.getText());
                   new Category_Service().addcategory(s);
                    new Admin_CategoryForm(this).show();
                }

            }
            );

            ajout.show();

        });
    }

    public MultiButton addIteam_category(category c) {
        MultiButton m = new MultiButton();
        m.setTextLine1(c.getName());
        m.setEmblem(theme.getImage("round.png"));

        m.addActionListener(l
                -> {

            Form f2 = new Form("Details category", BoxLayout.y());

            Label name = new Label(c.getName());

            Button sup = new Button("supprimer");

            Button Modifier = new Button("Modifier");
            Modifier.addActionListener(mod
                    -> {

                Form fmodifier = new Form("EDIT category", BoxLayout.y());
                Button submit = new Button("Submit");
                AutoCompleteTextField name_edit = new AutoCompleteTextField(c.getName());
                name_edit.setMinimumElementsShownInPopup(1);

            

                fmodifier.add("Name: ").add(name_edit).add(submit);
                
                fmodifier.getToolbar().addCommandToLeftBar("back", null, (evt) -> {
                    this.show();
                });
                submit.addActionListener(sub
                        -> {
                   if (name_edit.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de name ", "OK", null);

                }
                    else {
                    category s = new category(c.getId(), name_edit.getText());
                    new Category_Service().updateCategory(s);
                    new Admin_CategoryForm(this).show();
                    }
                }
                );

                fmodifier.show();
            }
            );

            sup.addActionListener(supq
                    -> {
                try {
                   new Category_Service().DeleteCategory(c.getId());
                    new Admin_CategoryForm(f2).showBack();
                } catch (Exception ex) {

                }
            }
            );

            f2.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                this.showBack();
            });
            f2.add("Name : ").add(name).add(Modifier).add(sup);

            f2.show();

        }
        );
        return m;
    }

}

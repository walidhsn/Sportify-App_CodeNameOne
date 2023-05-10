/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import models.supplier;
import services.Supplier_Service;

/**
 *
 * @author MOLKA
 */
public class Admin_SupplierForm extends Form {

    Resources theme = UIManager.initFirstTheme("/theme_1");

    public Admin_SupplierForm(Form previous) {
        super("Suppliers", BoxLayout.y());
this.getToolbar().addCommandToLeftBar("Back", null, ev -> {
           Admin_InterfaceForm w = new Admin_InterfaceForm(previous);
        w.showBack();
         });
        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();

                try {
                    for (supplier c : new Supplier_Service().getAllSuppliers()) {

                        System.out.println(c);

                        this.add(addIteam_Supplier(c));
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
        this.getToolbar().addCommandToOverflowMenu("Add Supplier", null, ev -> {

            Form ajout = new Form("ADD Supplier", BoxLayout.y());
            ajout.getToolbar().addCommandToLeftBar("back", null, evx -> {
                this.showBack();
            });
            TextField name = new TextField("", "name", 20, TextArea.ANY);
            TextField phone = new TextField("", "phone", 20, TextArea.ANY);
            TextField email = new TextField("", "email", 20, TextArea.ANY);
            TextField notes = new TextField("", "notes", 20, TextArea.ANY);
            TextField adress = new TextField("", "adress", 20, TextArea.ANY);
            String text_saisir_des_caracteres = "^[0-9]+$";
            Validator val_name = new Validator();
            val_name.addConstraint(name, new LengthConstraint(8));

            val_name.addConstraint(name, new RegexConstraint(text_saisir_des_caracteres, ""));

            Validator val_phone = new Validator();
            val_phone.addConstraint(phone, new LengthConstraint(8));
            val_phone.addConstraint(phone, new RegexConstraint(text_saisir_des_caracteres, ""));

            Validator val_email = new Validator();
            val_email.addConstraint(email, new LengthConstraint(8));
            val_email.addConstraint(email, new RegexConstraint(text_saisir_des_caracteres, ""));

            Validator val_notes = new Validator();
            val_notes.addConstraint(notes, new LengthConstraint(8));
            val_notes.addConstraint(notes, new RegexConstraint(text_saisir_des_caracteres, ""));

            Validator val_adress = new Validator();
            val_adress.addConstraint(adress, new LengthConstraint(8));
            val_adress.addConstraint(adress, new RegexConstraint(text_saisir_des_caracteres, ""));

            ajout.add("Name : ").add(name);
            ajout.add("Adress : ").add(adress);
            ajout.add("Phone : ").add(phone);
            ajout.add("Notes : ").add(notes);
            ajout.add("Email : ").add(email);

            Button submit = new Button("Submit");
            ajout.add(submit);

            submit.addActionListener(aj
                    -> {
                if (name.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de name ", "OK", null);

                } else if (val_name.isValid()) {
                    Dialog.show("Erreur name !", "il faut saisir des caracteres  !", "OK", null);
                } else if (adress.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de adress ", "OK", null);

                } else if (val_adress.isValid()) {
                    Dialog.show("Erreur adress !", "il faut saisir des caracteres  !", "OK", null);
                } else if (phone.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de phone ", "OK", null);

                } else if (val_phone.isValid()) {
                    Dialog.show("Erreur phone !", "il faut saisir des caracteres  !", "OK", null);
                } else if (notes.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de notes ", "OK", null);

                } else if (val_notes.isValid()) {
                    Dialog.show("Erreur notes !", "il faut saisir des caracteres  !", "OK", null);
                } else if (email.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de email ", "OK", null);

                } else if (val_email.isValid()) {
                    Dialog.show("Erreur email !", "il faut saisir des caracteres  !", "OK", null);
                } else {
                    supplier s = new supplier(name.getText(), phone.getText(), email.getText(), notes.getText(), adress.getText());
                    new Supplier_Service().addSupplier(s);
                    new Admin_SupplierForm(this).show();
                }

            }
            );

            ajout.show();

        });
    }

    public MultiButton addIteam_Supplier(supplier c) {
        MultiButton m = new MultiButton();
        m.setTextLine1(c.getName());
        m.setTextLine2(c.getEmail());

        m.setEmblem(theme.getImage("round.png"));

        m.addActionListener(l
                -> {

            Form f2 = new Form("Details Supplier", BoxLayout.y());

            Label name = new Label(c.getName());
            Label email = new Label(c.getEmail());
            Label adress = new Label(c.getAdress());
            Label notes = new Label(c.getNotes());
            Label phone = new Label(c.getPhone());

            Button sup = new Button("supprimer");

            Button Modifier = new Button("Modifier");
            Modifier.addActionListener(mod
                    -> {

                Form fmodifier = new Form("EDIT Supplier", BoxLayout.y());
                Button submit = new Button("Submit");
                AutoCompleteTextField name_edit = new AutoCompleteTextField(c.getName());
                name_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField email_edit = new AutoCompleteTextField(c.getEmail());
                email_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField adress_edit = new AutoCompleteTextField(c.getAdress());
                adress_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField notes_edit = new AutoCompleteTextField(c.getNotes());
                notes_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField phone_edit = new AutoCompleteTextField(c.getPhone());
                phone_edit.setMinimumElementsShownInPopup(1);
            

                fmodifier.add("Name: ").add(name_edit).add("Email: ").add(email_edit).add("Adress : ").add(adress_edit).add("Notes: ").add(notes_edit).add("Phone: ").add(phone_edit).add(submit);
                
                fmodifier.getToolbar().addCommandToLeftBar("back", null, (evt) -> {
                    this.show();
                });
                submit.addActionListener(sub
                        -> {
                    supplier s = new supplier(c.getId(), name_edit.getText(), phone_edit.getText(), email_edit.getText(), notes_edit.getText(), adress_edit.getText());
                   
                    new Supplier_Service().updateSupplier(s);
                    new Admin_SupplierForm(this).show();

                }
                );

                fmodifier.show();
            }
            );

            sup.addActionListener(supq
                    -> {
                try {
                    new Supplier_Service().DeleteSupplier(c.getId());
                    new Admin_SupplierForm(f2).showBack();
                } catch (Exception ex) {

                }
            }
            );

            f2.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                this.showBack();
            });
            f2.add("Name : ").add(name).add("Email : ").add(email).add("Adress : ").add(adress).add("Notes : ").add(notes).add("Phone : ").add(phone).add(Modifier).add(sup);

            f2.show();

        }
        );
        return m;
    }

}

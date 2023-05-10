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
import models.category;
import models.equipment;
import models.supplier;
import services.Category_Service;
import services.Equipment_Service;
import services.Supplier_Service;

/**
 *
 * @author MOLKA
 */
public class Admin_EquipmentForm extends Form {

    Resources theme = UIManager.initFirstTheme("/theme_1");

    public Admin_EquipmentForm(Form previous) {
        super("Equipments", BoxLayout.y());
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
                    for (equipment c : new Equipment_Service().getAllequipments()) {

                        System.out.println(c);

                        this.add(addIteam_equipment(c));
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
        this.getToolbar().addCommandToOverflowMenu("Add Equipment", null, ev -> {

            Form ajout = new Form("ADD Equipment", BoxLayout.y());
            ajout.getToolbar().addCommandToLeftBar("back", null, evx -> {
                this.showBack();
            });
            String text_saisir_des_caracteres = "^[0-9]+$";
            TextField name = new TextField("", "name", 20, TextArea.ANY);

            Validator val_name = new Validator();
            val_name.addConstraint(name, new LengthConstraint(8));

            val_name.addConstraint(name, new RegexConstraint(text_saisir_des_caracteres, ""));
            TextField adress = new TextField("", "adress", 20, TextArea.ANY);

            Validator val_adress = new Validator();
            val_adress.addConstraint(adress, new LengthConstraint(8));

            val_adress.addConstraint(adress, new RegexConstraint(text_saisir_des_caracteres, ""));
            TextField type = new TextField("", "type", 20, TextArea.ANY);

            Validator val_type = new Validator();
            val_type.addConstraint(type, new LengthConstraint(8));

            val_type.addConstraint(type, new RegexConstraint(text_saisir_des_caracteres, ""));
            TextField quantity = new TextField("", "quantity", 20, TextArea.NUMERIC);

            Validator val_quantity = new Validator();

            val_quantity.addConstraint(quantity, new LengthConstraint(8));

            val_quantity.addConstraint(quantity, new RegexConstraint(text_saisir_des_caracteres, ""));

            TextField price = new TextField("", "price", 20, TextArea.NUMERIC);

            Validator val_price = new Validator();
            val_price.addConstraint(price, new LengthConstraint(8));

            val_price.addConstraint(price, new RegexConstraint(text_saisir_des_caracteres, ""));
            ComboBox Category_equipment = new ComboBox();
            ComboBox Supplier_equipment = new ComboBox();
            for (category c : new Category_Service().getAllcategorys()) {
                Category_equipment.addItem(c.getId());
            }
            for (supplier c : new Supplier_Service().getAllSuppliers()) {
                Supplier_equipment.addItem(c.getId());
            }

            ajout.add("Name : ").add(name).add("Adress : ").add(adress).add("type : ").add(type).add("quantity : ").add(quantity).add("price : ").add(price).add("Category : ").add(Category_equipment).add("Supplier : ").add(Supplier_equipment);

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
                } else if (type.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de type ", "OK", null);

                } else if (val_type.isValid()) {
                    Dialog.show("Erreur adress !", "il faut saisir des caracteres  !", "OK", null);
                } else if (quantity.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de quantity ", "OK", null);

                } else if (!val_quantity.isValid()) {
                    Dialog.show("Erreur quantity !", "il faut saisir des numeros  !", "OK", null);
                } else if (price.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de price ", "OK", null);

                } else if (!val_price.isValid()) {
                    Dialog.show("Erreur price !", "il faut saisir des numeros  !", "OK", null);
                } else {
                    supplier s = new supplier();
                    s.setId(Integer.valueOf(Supplier_equipment.getSelectedItem().toString()));
                    category ca = new category();
                    ca.setId(Integer.valueOf(Category_equipment.getSelectedItem().toString()));
                    equipment eq = new equipment(name.getText(), adress.getText(), type.getText(), Integer.valueOf(quantity.getText()), s, price.getText(), ca, "");

                    new Equipment_Service().addEquipment(eq);
                    new Admin_EquipmentForm(this).show();
                }

            }
            );

            ajout.show();

        });
    }

    public MultiButton addIteam_equipment(equipment c) {
        MultiButton m = new MultiButton();
        m.setTextLine1(c.getName());
        m.setTextLine2(c.getType());
        m.setTextLine3(c.getAdress());
        m.setEmblem(theme.getImage("round.png"));
        Image imge;
        EncodedImage enc;
        enc = EncodedImage.createFromImage(theme.getImage("round.png"), false);
        imge = URLImage.createToStorage(enc, c.getImage(), c.getImage());
        m.setIcon(imge);
        m.addActionListener(l
                -> {

            Form f2 = new Form("Details Equipment", BoxLayout.y());
            Label name = new Label(c.getName());
            Label adress = new Label(c.getAdress());
            Label type = new Label(c.getType());
            Label quantity = new Label(String.valueOf(c.getQuantity()));
            Label Price = new Label(c.getPrice());
            Label name_supplier = new Label(c.getSupplier().getName());
            Label email_supplier = new Label(c.getSupplier().getEmail());
            Label phone_supplier = new Label(c.getSupplier().getPhone());
            Label name_Category = new Label(c.getCategory().getName());

            Button sup = new Button("supprimer");

            Button Modifier = new Button("Modifier");
            Modifier.addActionListener(mod
                    -> {

                Form fmodifier = new Form("EDIT Equipment", BoxLayout.y());
                Button submit = new Button("Submit");
                AutoCompleteTextField name_edit = new AutoCompleteTextField(c.getName());
                name_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField adress_edit = new AutoCompleteTextField(c.getAdress());
                adress_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField type_edit = new AutoCompleteTextField(c.getType());
                type_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField quantity_edit = new AutoCompleteTextField(String.valueOf(c.getQuantity()));
                quantity_edit.setMinimumElementsShownInPopup(1);
                AutoCompleteTextField price_edit = new AutoCompleteTextField(String.valueOf(c.getPrice()));
                quantity_edit.setMinimumElementsShownInPopup(1);

                ComboBox Category_equipment = new ComboBox();
                ComboBox Supplier_equipment = new ComboBox();
                for (category cat : new Category_Service().getAllcategorys()) {
                    Category_equipment.addItem(cat.getId());
                }
                for (supplier supp : new Supplier_Service().getAllSuppliers()) {
                    Supplier_equipment.addItem(supp.getId());
                }

                fmodifier.add("Name : ").add(name_edit).add("Adress : ").add(adress_edit).add("type : ").add(type_edit).add("quantity : ").add(quantity_edit).add("price : ").add(price_edit).add("Category : ").add(Category_equipment).add("Supplier : ").add(Supplier_equipment);

                fmodifier.getToolbar().addCommandToLeftBar("back", null, (evt) -> {
                    this.show();
                });
                submit.addActionListener(sub
                        -> {
                    supplier s = new supplier();
                    s.setId(Integer.valueOf(Supplier_equipment.getSelectedItem().toString()));
                    category ca = new category();
                    ca.setId(Integer.valueOf(Category_equipment.getSelectedItem().toString()));
                    equipment eq = new equipment(c.getId(), name_edit.getText(), adress_edit.getText(), type_edit.getText(), Integer.valueOf(quantity_edit.getText()), s, price_edit.getText(), ca, "");

                    new Equipment_Service().updateEquipment(eq);
                    new Admin_EquipmentForm(this).show();

                }
                );
                fmodifier.add(submit);
                fmodifier.show();
            }
            );

            sup.addActionListener(supq
                    -> {
                try {
                    new Equipment_Service().DeleteEquipment(c.getId());
                    new Admin_EquipmentForm(f2).showBack();
                } catch (Exception ex) {

                }
            }
            );

            f2.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                this.showBack();
            });

            f2.add("Name : ")
                    .add(name)
                    .add("adress : ")
                    .add(adress)
                    .add("type : ")
                    .add(type)
                    .add("quantity : ")
                    .add(quantity)
                    .add("Price : ")
                    .add(Price)
                    .add("Supplier : ")
                    .add("Name Supplier : ")
                    .add(name_supplier)
                    .add("Email Supplier : ")
                    .add(email_supplier)
                    .add("Phone Supplier : ")
                    .add(phone_supplier)
                    .add("Category : ")
                    .add("Name Category : ")
                    .add(name_Category)
                    .add(Modifier)
                    .add(sup);

            f2.show();

        }
        );
        return m;
    }

}

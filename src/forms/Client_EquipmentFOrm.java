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
public class Client_EquipmentFOrm  extends Form {

    Resources theme = UIManager.initFirstTheme("/theme_1");

    public Client_EquipmentFOrm( int id_c) {
        super("Equipments", BoxLayout.y());
        this.add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {
            // this will take a while...

            Display.getInstance().callSerially(() -> {
                this.removeAll();

                try {
                    for (equipment c : new Equipment_Service().getAllequipments()) {

                           if (c.getCategory().getId() == id_c) {
                               this.add(addIteam_equipment(c));
                           }
                        
                    }
                } catch (Exception ex) {

                }

                this.revalidate();
            });
        });
 this.getToolbar().addCommandToLeftBar("Back", null, ev -> {
     Form hi = new Form("Hi World", BoxLayout.y());
           Client_CtegoryForm w = new Client_CtegoryForm(hi);
        w.showBack();
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

          

            Button rent = new Button("Rent");
            rent.addActionListener(mod
                    -> {

               new Client_RentForm(c.getId(), f2).show();
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
                    .add(rent);
                 

            f2.show();

        }
        );
        return m;
    }

}


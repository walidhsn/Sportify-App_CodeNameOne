/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
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
import models.rent;
import models.supplier;
import services.Rent_Service;
import services.Supplier_Service;

/**
 *
 * @author MOLKA
 */
public class Client_RentForm extends Form {

    Resources theme = UIManager.initFirstTheme("/theme_1");

    public Client_RentForm(int id_e,Form previous) {
        super("Add Rent", BoxLayout.y());
this.getToolbar().addCommandToLeftBar("Back", null, ev -> {
           
        new Client_CtegoryForm(previous).showBack();
         });
        TextField date = new TextField("", "date", 20, TextArea.ANY);
        this.add("rent").add(date);
      Button submit = new Button("Submit");
         submit.addActionListener(aj
                    -> {
                if (date.getText().equals("")) {
                    Dialog.show("Erreur", "Champ vide de date ", "OK", null);

                }  else {
                    new Rent_Service().addrent(id_e, date.getText());
                    new Client_CtegoryForm(this).showBack();
                }

            }
            );
            this.add(submit);
    }

  

}

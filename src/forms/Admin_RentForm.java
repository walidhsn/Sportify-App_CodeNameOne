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
import models.rent;
import services.Rent_Service;

/**
 *
 * @author MOLKA
 */
public class Admin_RentForm extends Form {

    Resources theme = UIManager.initFirstTheme("/theme_1");

    public Admin_RentForm(Form previous) {
        super("Rents", BoxLayout.y());
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
                    for (rent c : new Rent_Service().getAllRents()) {
                        
                        this.add(addIteam_rent(c));
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
     
    }

    public MultiButton addIteam_rent(rent c) {
        MultiButton m = new MultiButton();
        m.setTextLine1(c.getDate());
        m.setEmblem(theme.getImage("round.png"));

        m.addActionListener(l
                -> {

            Form f2 = new Form("Details rent", BoxLayout.y());

            Label date = new Label(c.getDate());

            Button sup = new Button("supprimer");

       

            sup.addActionListener(supq
                    -> {
                try {
                   new Rent_Service().DeleteRent(c.getId());
                    new Admin_RentForm(f2).showBack();
                } catch (Exception ex) {

                }
            }
            );

            f2.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
                this.showBack();
            });
            f2.add("rent AT : ").add(date).add(sup);

            f2.show();

        }
        );
        return m;
    }

}

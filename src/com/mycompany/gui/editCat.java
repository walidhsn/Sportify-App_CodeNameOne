/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.entities.Categorie;
import com.mycompany.services.ServiceCategorie;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ArwaBj
 */
public class editCat extends Form {




    ArrayList<Float> myList = new ArrayList<>();

    public editCat(Resources previous, Categorie r) {
    
       
        setTitle("Edit My  Category");
        setLayout(BoxLayout.y());
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
       
      
        
 TextField tfnomcat = new TextField(r.getName());
        tfnomcat.setEditable(true);
   
   
       

           
            Button btnValider = new Button("Edit Category");
        btnValider.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent evt) {
                if ((tfnomcat.getText().length() == 0) ) {
                    Dialog.show("Alert", "Please fill all the fields", "OK", null);
                } else {
                    try {

                        r.setName(tfnomcat.getText());
                       
                       
                        if( ServiceCategorie.getInstance().updateCategory(r)){
                             Dialog.show("Success", "Connection accepted", "OK", null);
//                              ListCatsForm f = new ListCatsForm(previous);
//                            f.setTransitionOutAnimator(CommonTransitions.createEmpty());
                             }
                             
                        else
                             Dialog.show("Error", "Server ERROR", "OK", null);

                    } catch (NumberFormatException e) {
                        Dialog.show("Alert", "Please fill all the fields", "OK", null);
                    }

                }

            }
        });
        addAll(tfnomcat, btnValider);
        setTransitionOutAnimator(CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 250));
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
//                 e -> new ListCatsForm(previous));
    //this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());             
   
           
           
    }
   

}

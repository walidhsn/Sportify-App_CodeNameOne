/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.l10n.L10NManager;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.entities.Categorie;
import com.mycompany.services.ServiceArticle;
import com.mycompany.services.ServiceCategorie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ArwaBj
 */
/*public class editArticle extends Form {

    ArrayList<Double> myList = new ArrayList<>();

    public editArticle(Resources theme, Article r) {
        
        setTitle("Edit My Article");

        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);

        TextField tflibelle = new TextField(r.getLibelle());

        int id = r.getId();
        TextField tfqt = new TextField(r.getStock());

        TextField tfprix = new TextField(r.getPrix());

        TextField tfref = new TextField(r.getRef());

        tfprix.setEditable(true);
        tfqt.setEditable(true);
        tfref.setEditable(true);

        ComboBox<Double> RatingCombo = new ComboBox<>();
        

        Button btnValider = new Button("Edit Article");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tflibelle.getText().length() == 0) 
                        || (tfqt.getText().length() == 0) || (tfprix.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", "OK", null);
                } else {
                    try {
                        
                        r.setLibelle(tflibelle.getText());
                        //r.setImage_name(tfImagearticle.getText());
                        r.setStock(tfqt.getText());
                        r.setPrix((tfprix.getText()));
                        r.setRef((tfref.getText()));
                        r.setCategorie(r.getCategorie());
                        if (ServiceArticle.getInstance().editArticle(r , id)) {
                            Dialog.show("Success", "Article edited successfully", "OK", null);
                            
                            new ListArticlesForm(theme);
                        } else {
                            Dialog.show("Error", "Server error", "OK", null);
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("Alert", "Please fill all the fields", "OK", null);
                    }
                }
            }
        });

        setLayout(BoxLayout.y());
        add(tflibelle).add(tfqt).add(tfprix).add(tfref).add(RatingCombo).add(btnValider);

    }

}*/
public class editArticle extends Form {

   public editArticle(Resources theme, Article r) {
        setTitle("Edit My Article");

        TextField tflibelle = new TextField(r.getLibelle());

        int id = r.getId();
        System.out.println(id);
        TextField tfqt = new TextField(r.getStock());
        TextField tfprix = new TextField(r.getPrix());
        TextField tfref = new TextField(r.getRef());
        
        tfprix.setEditable(true);
        tfqt.setEditable(true);
        tfref.setEditable(true);

        ComboBox<String> categoryCombo = new ComboBox<>();
        // Populate the categoryCombo with categories retrieved from the server
        // You need to implement the logic to fetch the categories from your server
        // and populate the categoryCombo with the category IDs and names
        List<Categorie> categories = ServiceCategorie.getInstance().getAllCategories();

        // Populate the categoryCombo with category IDs and names
        for (Categorie category : categories) {
            int categoryId = category.getId();
            String categoryName = category.getName();
            categoryCombo.addItem(categoryId + " - " + categoryName);
        }

        Button btnValider = new Button("Edit Article");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tflibelle.getText().length() == 0)
                        || (tfqt.getText().length() == 0) || (tfprix.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", "OK", null);
                } else {
                    try {
                        r.setLibelle(tflibelle.getText());
                        String stock = tfqt.getText();
                        r.setStock(stock);
                        String prix = tfprix.getText();
                        r.setPrix(prix);
                        r.setRef(tfref.getText());

                        // Set the selected category ID from the categoryCombo
                        String selectedCategoryItem = categoryCombo.getSelectedItem();
                        int index = selectedCategoryItem.indexOf("-");
String categoryId = selectedCategoryItem.substring(0, index).trim();
r.setCategorie(categoryId);
                        if (ServiceArticle.getInstance().editArticle(r, id)) {
                            Dialog.show("Success", "Article edited successfully", "OK", null);
                            new ListArticlesForm(theme).show();
                        } else {
                            Dialog.show("Error", "Server error", "OK", null);
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("Alert", "Invalid stock or price value", "OK", null);
                    }
                }
            }
        });

        setLayout(BoxLayout.y());
        add(tflibelle).add(tfqt).add(tfprix).add(tfref).add(categoryCombo).add(btnValider);
    }
}

    



    


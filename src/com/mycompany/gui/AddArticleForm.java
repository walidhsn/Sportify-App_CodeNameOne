package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.entities.Categorie;
import com.mycompany.services.ServiceArticle;
import com.mycompany.services.ServiceCategorie;
import java.util.ArrayList;

import java.util.List;

public class AddArticleForm extends Form {
    Resources res;
    private Form current;
    public AddArticleForm(Resources theme) {
        setTitle("Add New Product");
        
        Label label = new Label("Nom:");
        TextField tflibelle = new TextField();
        Label labelqt = new Label("Quantite:");
        TextField tfqt = new TextField();
        Label labelpr = new Label("Prix:");
        TextField tfprix = new TextField();
        Label labelfr = new Label("Reference :");
        TextField tfref = new TextField();

        tfprix.setEditable(true);
        tfqt.setEditable(true);
        tfref.setEditable(true);
        
        Label labelcat = new Label("Categorie:");

        ComboBox<String> categoryCombo = new ComboBox<>();
        // Populate the categoryCombo with categories retrieved from the server
        List<Categorie> categories = ServiceCategorie.getInstance().getAllCategories();
        for (Categorie category : categories) {
            int categoryId = category.getId();
            String categoryName = category.getName();
            categoryCombo.addItem(categoryId + " - " + categoryName);
        }
        
             


        Button btnValider = new Button("Add Article");
        btnValider.addActionListener(evt -> {
            if (tflibelle.getText().length() == 0 || tfqt.getText().length() == 0 || tfprix.getText().length() == 0) {
                Dialog.show("Alert", "Please fill all the fields", "OK", null);
            } else {
                try {
                    Article newArticle = new Article();
                    newArticle.setLibelle(tflibelle.getText());
                    newArticle.setStock(tfqt.getText());
                    newArticle.setPrix(tfprix.getText());
                    newArticle.setRef(tfref.getText());
                    int selectedIndex = categoryCombo.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < categories.size()) {
                    Categorie selectedCategory = categories.get(selectedIndex);
                    // Set the ID of the selected category
                    newArticle.setCategorie(String.valueOf(selectedCategory.getId()));
                }
                    //newArticle.setCategorie(String.valueOf(categoryCombo.getSelectedItem().getId()));

                    if (ServiceArticle.getInstance().addArticle(newArticle)) {
                        Dialog.show("Success", "Article added successfully", "OK", null);
                        // Clear the input fields
                        tflibelle.clear();
                        tfqt.clear();
                        tfprix.clear();
                        tfref.clear();
                        // Redirect to ListArticlesForm
                        new ListArticlesForm(theme).show();
                    } else {
                        Dialog.show("Error", "Server error", "OK", null);
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("Alert", "Invalid stock or price value", "OK", null);
                }
            }
        });
        Button btnBack = new Button("Back");
        btnBack.addActionListener(e -> {
        new ListArticlesForm(res).show();
    });
        Button btnAddCat = new Button("Add categorie?");
        btnAddCat.addActionListener(e -> {
        new AddCatForm(current).show();
    });
         add(btnBack);
        setLayout(BoxLayout.y());
        add(label).add(tflibelle).add(labelqt).add(tfqt).add(labelpr).add(tfprix).add(labelfr).add(tfref).add(labelcat).add(categoryCombo).add(btnValider).add(btnAddCat);
    }
    
}

package com.mycompany.gui;

import com.codename1.charts.util.ColorUtil;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.services.ServiceArticle;
import com.mycompany.services.ServiceCardItem;
import com.mycompany.services.CardService;
import java.util.ArrayList;

/**
 *
 * @author louay
 */
public class ListArticlesForm2 extends Form {
    private Resources resources;
    Form current;
     public ListArticlesForm2(Resources res) {
         super(BoxLayout.y());
         this.getToolbar().addCommandToSideMenu("panier" , null , e -> {new PanierForm(ServiceCardItem.getInstance().getAllCardItems()).show(); });
         resources = res;
    setTitle("Article");
    setLayout(new BoxLayout(BoxLayout.Y_AXIS)); // use BoxLayout with Y_AXIS
    ArrayList<Article> articles = ServiceArticle.getInstance().getAllArticles();
    if (articles != null) {
        for (Article article : articles) {
            Container card = createArticleCard(article);
            addComponent(card);
        }
    }
    
    System.out.println("Number of articles retrieved from the database: " + articles.size());
     /*Button addButton = new Button("Add New One ?");
    addButton.addActionListener(e -> {
        new AddArticleForm(res).show();
    });
         add(addButton);
       */  

}
private Container createArticleCard(Article article) {
    Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    
    Label libelleLabel = new Label(article.getLibelle());
    libelleLabel.getUnselectedStyle().setFont(fnt);
    libelleLabel.getUnselectedStyle().setAlignment(Component.CENTER);

    Label stockLabel = new Label(String.valueOf(article.getStock()));
    stockLabel.getUnselectedStyle().setFont(fnt);
    stockLabel.getUnselectedStyle().setAlignment(Component.CENTER);

    Label prixLabel = new Label(String.valueOf(article.getPrix()));
    prixLabel.getUnselectedStyle().setFont(fnt);
    prixLabel.getUnselectedStyle().setAlignment(Component.CENTER);
    int id = article.getId();
    System.out.println("+++++++++"+id);
    Button editButton = new Button("Edit");
    editButton.addActionListener(e -> {
        new editArticle(resources, article).show();
    });
    //Button supButton = new Button("delete");
    /*supButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent evt) {
                if (ServiceArticle.getInstance().delete(id)) {
                            Dialog.show("Success", "Article edited successfully", "OK", null);
                            new ListArticlesForm2(resources).show();
                        } else {
                            Dialog.show("Error", "Server error", "OK", null);
                        }
            
            }});*/
            Button aerButton = new Button("add to card");
       aerButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent evt) {
                if (ServiceArticle.getInstance().addToCart(1,id)) {
                            Dialog.show("Success", "Article Added successfully", "OK", null);
                            new ListArticlesForm2(resources).show();
                        } else {
                            Dialog.show("Error", "Server error", "OK", null);
                        }
            
            }});
                 
    Container card = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    card.getStyle().setPadding(Component.TOP, 5);
    card.getStyle().setPadding(Component.BOTTOM, 5);
    card.getStyle().setPadding(Component.LEFT, 5);
    card.getStyle().setPadding(Component.RIGHT, 5);
    card.getStyle().setBorder(Border.createLineBorder(2, ColorUtil.rgb(66, 66, 66)));
    card.getStyle().setBgTransparency(255);
    card.getStyle().setBgColor(ColorUtil.WHITE);
    card.addComponent(libelleLabel);
    card.addComponent(stockLabel);
    card.addComponent(prixLabel);
    //card.addComponent(editButton);
    //card.add(supButton);
    card.add(aerButton);

    return card;
}
}
package com.mycompany.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.entities.Card;
import com.mycompany.gui.ListArticlesForm;
import com.mycompany.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.concurrent.CompletableFuture;

public class ServiceArticle {
    private ArrayList<Article> articles;
    private static ServiceArticle instance = null;
    private boolean resultOK;
    private ConnectionRequest req;
    Resources res;
    private ServiceArticle() {
        req = new ConnectionRequest();
    }

    public static ServiceArticle getInstance() {
        if (instance == null) {
            instance = new ServiceArticle();
        }
        return instance;
    }


   public boolean addArticle(Article article) {
    String url = Statics.BASE_URL + "/produitjson/addprodjson";
    req.setUrl(url);
    req.setPost(true); // Set to true for a POST request
    req.addArgument("libelle", article.getLibelle());
    req.addArgument("prix", String.valueOf(article.getPrix()));
    req.addArgument("stock", String.valueOf(article.getStock()));
    req.addArgument("ref", article.getRef());
    req.addArgument("categorie_id", String.valueOf(article.getCategorie())); // Assuming you have the category ID in the Article object

    req.addResponseListener(evt -> {
        if (req.getResponseCode() == 200) {
            String response = new String(req.getResponseData());
            // Handle the response if needed
        } else {
            // Handle the error if needed
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return req.getResponseCode() == 200;
}



    public ArrayList<Article> parseArticles(String jsonText) {
        try {
            articles = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> articlesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) articlesListJson.get("root");

            for (Map<String, Object> obj : list) {
                Article article = new Article();
                float id_article = Float.parseFloat(obj.get("id").toString());
               // article.setId_article((int) id_article);
                //article.setImage_name(obj.get("image_name").toString());
                article.setLibelle(obj.get("libelle").toString());
                article.setStock(obj.get("stock").toString());
                article.setPrix(obj.get("prix").toString());
                article.setRef(obj.get("ref").toString());
                article.setId((int) id_article);
                articles.add(article);
            }
        } catch (IOException ex) {
            Log.e(ex);
        }

        return articles;
    }
    public ArrayList<Article> getAllArticles() {
    String url = Statics.BASE_URL + "/produitjson/sh";
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(false);

    req.addResponseListener(evt -> {
        String jsonText = new String(req.getResponseData());
        articles = parseArticles(jsonText);
        req.removeResponseListener(evt2 -> {
            // listener removed
        });
    });
    NetworkManager.getInstance().addToQueueAndWait(req);

    return articles;
}

    
       
    public boolean editArticle(Article r, int id) {
    String url = "http://127.0.0.1:8000/produitjson/editprodjson/" + id
            + "?libelle=" + r.getLibelle()
            + "&prix=" + r.getPrix()
            + "&stock=" + r.getStock()
            + "&ref=" + r.getRef()
            + "&categorie_id=" + r.getCategorie();

    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200;
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
    ToastBar.showMessage("Article Modifié", FontImage.MATERIAL_ACCESS_TIME);

    return resultOK;
}

    public boolean delete(int id) {
    String url = Statics.BASE_URL + "/produitjson/" + id;
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setHttpMethod("DELETE");

    req.addResponseListener((ActionListener<NetworkEvent>) evt -> {
        if (req.getResponseCode() == 200) {
            // Deletion successful
            Display.getInstance().callSerially(() -> {
                ToastBar.showMessage("Article deleted", FontImage.MATERIAL_INFO);
                // Perform the redirection to another page here
                // Example: app.navigateToSomePage();
                new ListArticlesForm(res).show();
            });
        } else {
            // Deletion failed
            Display.getInstance().callSerially(() -> {
                ToastBar.showMessage("Failed to delete article", FontImage.MATERIAL_ERROR);
            });
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return req.getResponseCode() == 200;
}
    /*public void addToCart(int id) {
        String url = Statics.BASE_URL + "/add-to-cart/" + id;
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(evt -> {
            if (req.getResponseCode() == 200) {
                String jsonText = new String(req.getResponseData());
                Card cart = parseCart(jsonText);
                // Handle the retrieved cart object if needed
                Display.getInstance().callSerially(() -> {
                    ToastBar.showMessage("Product added to cart", FontImage.MATERIAL_INFO);
                    // Perform any additional actions here
                });
            } else {
                // Handle the error if needed
                Display.getInstance().callSerially(() -> {
                    ToastBar.showMessage("Failed to add product to cart", FontImage.MATERIAL_ERROR);
                });
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }*/
 public boolean addToCart(int id , int  pId) {
    String url = Statics.BASE_URL + "/produitjson/add-to-cart/" + id +"/"+pId;
    req.setUrl(url);
    req.setPost(false);
    
    // Boolean flag to indicate the result
    final boolean[] success = {false};

    req.addResponseListener(evt -> {
        if (req.getResponseCode() == 200) {
            String jsonText = new String(req.getResponseData());
            Card cart = parseCart(jsonText);
            // Handle the retrieved cart object if needed
            
            success[0] = true; // Set the flag to true indicating success
        } else {
            // Handle the error if needed
            
            success[0] = false; // Set the flag to false indicating failure
        }
        
        synchronized (success) {
            success.notify(); // Notify the waiting thread
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
    
    synchronized (success) {
        try {
            success.wait(); // Wait for the response to arrive
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    return success[0]; // Return the final result
}
    private Card parseCart(String jsonText) {
        Card cart = null;
        try {
            JSONParser parser = new JSONParser();
            Map<String, Object> cartJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            // Parse the JSON response and create a Cart object
            // You'll need to adapt this part based on the structure of your JSON response
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cart;
    }

}

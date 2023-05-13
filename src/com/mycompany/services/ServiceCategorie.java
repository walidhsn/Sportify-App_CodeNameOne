package com.mycompany.services;


import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.NetworkEvent;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Categorie;
import com.mycompany.utils.Statics;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceCategorie {
    private ArrayList<Categorie> categories;
    public static ServiceCategorie instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceCategorie() {
        req = new ConnectionRequest();
    }

    public static ServiceCategorie getInstance() {
        if (instance == null) {
            instance = new ServiceCategorie();
        }
        return instance;
    }
        public ArrayList<Categorie> parseCategories(String jsonText) {
    ArrayList<Categorie> categories = new ArrayList<>();
    try {
        JSONParser j = new JSONParser();

        Map<String, Object> categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) categoriesListJson.get("root");

        for (Map<String, Object> obj : list) {
            Categorie categorie = new Categorie();
            float idCategorie = Float.parseFloat(obj.get("id").toString());
            categorie.setId((int) idCategorie);
            categorie.setName(obj.get("nomCat").toString());

            categories.add(categorie);
        }
    } catch (IOException ex) {
        Log.e(ex);
    }

    return categories;
}
   public ArrayList<Categorie> getAllCategories() {
    String url = Statics.BASE_URL + "/categoriejson/sh";
    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
    req.setPost(false);

    req.addResponseListener(evt -> {
        String jsonText = new String(req.getResponseData());
        categories = parseCategories(jsonText);
        req.removeResponseListener(evt2 -> {
            // listener removed
        });
    });
    NetworkManager.getInstance().addToQueueAndWait(req);

    return categories;
}

    public boolean addCategory(Categorie c) {
        String url = Statics.BASE_URL + "/categoriejson/addcatjson";
        req.setUrl(url);
        req.setPost(true);
        req.addRequestHeader("Content-Type", "application/json");
        req.setRequestBody("{\"nom\":\"" + c.getName() + "\"}");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 201;
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }

    public boolean deleteCategory(Categorie c) {
        String url = Statics.BASE_URL + "/categoriejson/deletecatjson/" + c.getId();
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }
    
    public boolean updateCategory(Categorie c) {
    String url = Statics.BASE_URL + "/categoriejson/editcatjson/" + c.getId() 
            + "?nom=" + c.getName();
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200;
            req.removeResponseListener(this);
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    ToastBar.showMessage("Categorie Modifiée", FontImage.MATERIAL_ACCESS_TIME);
    return resultOK;
}


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.category;
import models.supplier;
import utils.DataSource;
import utils.Statics;

/**
 *
 * @author MOLKA
 */
public class Category_Service {
      private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<category> categorys;
     public Category_Service() {
        request = DataSource.getInstance().getRequest();
    }


       public ArrayList<category> parseCategorys(String jsonText) {
        
        try {
            categorys = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) 
            {   
                
            String name = obj.get("nom").toString(); 
             
            
            int id = (int)Float.parseFloat(obj.get("id").toString());  
            
            
            categorys.add(new category(id, name));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return categorys;
    }
   
     public ArrayList<category> getAllcategorys() {
        String url = Statics.BASE_URL + "category/categorys_mobile";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categorys = parseCategorys(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return categorys;
    }
        public void DeleteCategory(int id) {
     
        String url = Statics.BASE_URL + "category/delete_categorys_mobile?id=" + id;

        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

    }
           public boolean addcategory(category s) {
     
        String url = Statics.BASE_URL + "category/add_categorys_mobile?name=" + s.getName();

        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }
                public boolean updateCategory(category s) {
     
        String url = Statics.BASE_URL + "category/update_categorys_mobile?id=" + s.getId()+ "&name=" + s.getName();

        request.setUrl(url);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }
}

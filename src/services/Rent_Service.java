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
import models.rent;
import utils.DataSource;
import utils.Statics;

/**
 *
 * @author MOLKA
 */
public class Rent_Service {
    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<rent> rents;
     public Rent_Service() {
        request = DataSource.getInstance().getRequest();
    }


       public ArrayList<rent> parseRents(String jsonText) {
        
        try {
            rents = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) 
            {   
                
            String rentAt = obj.get("rentAt").toString(); 
             
            int id = (int)Float.parseFloat(obj.get("id").toString());  
            
            
            rents.add(new rent(id, rentAt));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return rents;
    }
   
     public ArrayList<rent> getAllRents() {
        String url = Statics.BASE_URL + "rent/rents_mobile";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                rents = parseRents(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return rents;
    }
        public void DeleteRent(int id) {
     
        String url = Statics.BASE_URL + "rent/delete_rents_mobile?id=" + id;

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
           public boolean addrent(int id_e, String date ) {
     
        String url = Statics.BASE_URL + "rent/add_rents_mobile?id_e=" + id_e+ "&date=" + date;

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

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
import models.supplier;
import utils.DataSource;
import utils.Statics;

/**
 *
 * @author MOLKA
 */
public class Supplier_Service {
        private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<supplier> Suppliers;
     public Supplier_Service() {
        request = DataSource.getInstance().getRequest();
    }


       public ArrayList<supplier> parseSuppliers(String jsonText) {
        
        try {
            Suppliers = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) 
            {   
                
            String name = obj.get("name").toString(); 
             
            String phone = obj.get("phone").toString();     
          
            String email = obj.get("email").toString();  
            
            String notes = obj.get("notes").toString();
        
            String adress = obj.get("adress").toString();
            
            int id = (int)Float.parseFloat(obj.get("id").toString());  
            
            
            Suppliers.add(new supplier(id, name, phone, email, notes, adress));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return Suppliers;
    }
   
     public ArrayList<supplier> getAllSuppliers() {
        String url = Statics.BASE_URL + "suppliers/suppliers_mobile";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Suppliers = parseSuppliers(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return Suppliers;
    }
        public void DeleteSupplier(int id) {
     
        String url = Statics.BASE_URL + "suppliers/delete_suppliers_mobile?id=" + id;

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
           public boolean addSupplier(supplier s) {
     
        String url = Statics.BASE_URL + "suppliers/add_suppliers_mobile?name=" + s.getName()+ "&phone=" + s.getPhone()+ "&email=" + s.getEmail()+ "&adress=" + s.getAdress()+ "&notes=" + s.getNotes();

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
                public boolean updateSupplier(supplier s) {
     
        String url = Statics.BASE_URL + "suppliers/update_suppliers_mobile?id=" + s.getId()+ "&name=" + s.getName()+ "&phone=" + s.getPhone()+ "&email=" + s.getEmail()+ "&adress=" + s.getAdress()+ "&notes=" + s.getNotes();

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

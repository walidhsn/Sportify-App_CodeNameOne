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
import java.util.Map.Entry;
import models.category;
import models.equipment;
import models.supplier;
import utils.DataSource;
import utils.Statics;

/**
 *
 * @author MOLKA
 */
public class Equipment_Service {

    private ConnectionRequest request;

    private boolean responseResult;
    public ArrayList<equipment> equipments;

    public Equipment_Service() {
        request = DataSource.getInstance().getRequest();
    }

    public ArrayList<equipment> parseEquipment(String jsonText) {

        try {
            equipments = new ArrayList<>();

            JSONParser jp = new JSONParser();
            Map<String, Object> tasksListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {

                String name = obj.get("name").toString();
                String type = obj.get("type").toString();
                String adress = obj.get("adress").toString();
                String image = obj.get("image").toString();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                int quantity = (int) Float.parseFloat(obj.get("quantity").toString());
                String Price = obj.get("Price").toString(); 
                supplier su = new supplier();
                Map<String, Object> map1 = ((Map<String, Object>) obj.get("suppliers"));
                for (Entry<String, Object> entry : map1.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.equals("id")) {
                        float idsu = Float.parseFloat(value.toString());
                        su.setId((int) idsu);
                    }
                    if (key.equals("name")) {
                        su.setName(value.toString());
                    }
                    if (key.equals("phone")) {
                        su.setPhone(value.toString());
                    }
                    if (key.equals("email")) {
                        su.setEmail(value.toString());
                    }
                    if (key.equals("notes")) {
                        su.setNotes(value.toString());
                    }
                    if (key.equals("adress")) {
                        su.setAdress(value.toString());
                    }
                }
                category ca = new category();
                  Map<String, Object> map2 = ((Map<String, Object>) obj.get("category"));
                for (Entry<String, Object> entry : map2.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key.equals("id")) {
                        float idcar = Float.parseFloat(value.toString());
                        ca.setId((int) idcar);
                    }
                    if (key.equals("nom")) {
                        ca.setName(value.toString());
                    }
                   
                }
                equipments.add(new equipment(id, name, adress, type, quantity, su, Price, ca, image));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return equipments;
    }

    public ArrayList<equipment> getAllequipments() {
        String url = Statics.BASE_URL + "equipment/equipments_mobile";

        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                equipments = parseEquipment(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return equipments;
    }

    
        public void DeleteEquipment(int id) {
     
        String url = Statics.BASE_URL + "equipment/delete_equipments_mobile?id=" + id;

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
           public boolean addEquipment(equipment eq) {
     
        String url = Statics.BASE_URL + "equipment/add_equipments_mobile?id_s=" + eq.getSupplier().getId()+ "&id_c=" + eq.getCategory().getId()+ "&name=" + eq.getName()+ "&adress=" + eq.getAdress()+ "&type=" + eq.getType()+ "&quantite=" + eq.getQuantity()+ "&price=" + eq.getPrice();

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
            public boolean updateEquipment(equipment eq) {
     
        String url = Statics.BASE_URL + "equipment/update_equipments_mobile?id_s=" + eq.getSupplier().getId()+ "&id_c=" + eq.getCategory().getId()+ "&name=" + eq.getName()+ "&adress=" + eq.getAdress()+ "&type=" + eq.getType()+ "&quantite=" + eq.getQuantity()+ "&price=" + eq.getPrice()+ "&id=" + eq.getId();

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Terrain;
import com.mycompany.utils.Statics;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author WALID
 */
public class ServiceTerrain {

    public static ServiceTerrain instance = null;

    private ConnectionRequest req;

    public static ServiceTerrain getInstance() {
        if (instance == null) {
            instance = new ServiceTerrain();
        }
        return instance;
    }

    public ServiceTerrain() {
        req = new ConnectionRequest();
    }

    public void ajouterTerrain(Terrain terrain, int id_user) {

        String url = Statics.BASE_URL + "/terrainApiAjouter/" + id_user + "?name=" + terrain.getName() + "&capacity=" + terrain.getCapacity() + "&sportType=" + terrain.getSportType() + "&rentPrice=" + terrain.getRentPrice() + "&disponibility=" + terrain.isDisponibility() + "&postalCode=" + terrain.getPostalCode() + "&roadName=" + terrain.getRoadName() + "&roadNumber=" + terrain.getRoadNumber() + "&city=" + terrain.getCity() + "&country=" + terrain.getCountry() + "&imageName=" + terrain.getImageName();
        req.setUrl(url);
        req.addResponseListener((a)
                -> {
            String str = new String(req.getResponseData());
            System.out.println("Data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        if (req.getResponseCode() == 200) {
            Dialog.show("Success", "The Terrain Has been added successfully", "OK", null);
        }
    }

    public void modifierTerrain(Terrain terrain, int id_terrain) {

        String url = Statics.BASE_URL + "/terrainApiModifier/" + id_terrain + "?name=" + terrain.getName() + "&capacity=" + terrain.getCapacity() + "&sportType=" + terrain.getSportType() + "&rentPrice=" + terrain.getRentPrice() + "&disponibility=" + terrain.isDisponibility() + "&postalCode=" + terrain.getPostalCode() + "&roadName=" + terrain.getRoadName() + "&roadNumber=" + terrain.getRoadNumber() + "&city=" + terrain.getCity() + "&country=" + terrain.getCountry() + "&imageName=" + terrain.getImageName();
        req.setUrl(url);
        req.addResponseListener((a)
                -> {
            String str = new String(req.getResponseData());
            System.out.println("Data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        if (req.getResponseCode() == 200) {
            Dialog.show("Success", "The Terrain Has been modified successfully", "OK", null);
        }
    }

    public void supprimerTerrain(int id_terrain) {

        String url = Statics.BASE_URL + "/terrainApiSupprimer/" + id_terrain;
        req.setUrl(url);
        req.addResponseListener((a)
                -> {
            String str = new String(req.getResponseData());
            System.out.println("Data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        if (req.getResponseCode() == 200) {
            Dialog.show("Success", "The Terrain Has been deleted successfully", "OK", null);
        } else {
            Dialog.show("Error", "it seems there's an error at deleting Terrain", "OK", null);
        }
    }

    public ArrayList<Terrain> afficherTerrains() {
        ArrayList<Terrain> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/terrainApiAfficher";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    String strData = new String(req.getResponseData(), "UTF-8");
                    Map<String, Object> mapTerrains = jsonp.parseJSON(new StringReader(strData));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapTerrains.get("root");
                    for (Map<String, Object> obj : listOfMaps) {
                        Terrain t = new Terrain();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String name = obj.get("name").toString();
                        double doubleValue = Double.parseDouble(obj.get("capacity").toString());
                        int capacity = (int) Math.round(doubleValue);
                        String sportType = obj.get("sportType").toString();
                        float rentPrice = Float.parseFloat(obj.get("rentPrice").toString());
                        boolean disponibility = Boolean.parseBoolean(obj.get("disponibility").toString());
                        doubleValue = Double.parseDouble(obj.get("postalCode").toString());
                        int postalCode = (int) Math.round(doubleValue);
                        String roadName = obj.get("roadName").toString();
                        doubleValue = Double.parseDouble(obj.get("roadNumber").toString());
                        int roadNumber = (int) Math.round(doubleValue);
                        String city = obj.get("city").toString();
                        String country = obj.get("country").toString();
                        String imageName = obj.get("imageName").toString();
                        String updatedAtStr = obj.get("updatedAt").toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date updatedAt = dateFormat.parse(updatedAtStr);
                        String imageUrl = obj.get("image_url").toString();

                        // setting the values :
                        t.setId((int) id);
                        t.setName(name);
                        t.setCapacity(capacity);
                        t.setSportType(sportType);
                        t.setRentPrice(rentPrice);
                        t.setDisponibility(disponibility);
                        t.setRentPrice(rentPrice);
                        t.setPostalCode(postalCode);
                        t.setRoadName(roadName);
                        t.setRoadNumber(roadNumber);
                        t.setCity(city);
                        t.setCountry(country);
                        t.setImageName(imageName);
                        t.setUpdatedAt(updatedAt);
                        t.setImageUrl(imageUrl);

                        result.add(t);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public ArrayList<Terrain> afficherTerrains_owner(int id_user) {
        ArrayList<Terrain> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/terrainApiAfficher/owner/" + id_user;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    String strData = new String(req.getResponseData(), "UTF-8");

                    Map<String, Object> mapTerrains = jsonp.parseJSON(new StringReader(strData));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapTerrains.get("root");
                    if (listOfMaps != null) {
                        for (Map<String, Object> obj : listOfMaps) {
                            Terrain t = new Terrain();
                            float id = Float.parseFloat(obj.get("id").toString());
                            String name = obj.get("name").toString();
                            double doubleValue = Double.parseDouble(obj.get("capacity").toString());
                            int capacity = (int) Math.round(doubleValue);
                            String sportType = obj.get("sportType").toString();
                            Double p = Double.parseDouble(obj.get("rentPrice").toString());
                            float rentPrice = p.floatValue();
                            boolean disponibility = Boolean.parseBoolean(obj.get("disponibility").toString());
                            doubleValue = Double.parseDouble(obj.get("postalCode").toString());
                            int postalCode = (int) Math.round(doubleValue);
                            String roadName = obj.get("roadName").toString();
                            doubleValue = Double.parseDouble(obj.get("roadNumber").toString());
                            int roadNumber = (int) Math.round(doubleValue);
                            String city = obj.get("city").toString();
                            String country = obj.get("country").toString();
                            String imageName = obj.get("imageName").toString();
                            String updatedAtStr = obj.get("updatedAt").toString();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date updatedAt = dateFormat.parse(updatedAtStr);
                            String imageUrl = obj.get("image_url").toString();

                            // setting the values :
                            t.setId((int) id);
                            t.setName(name);
                            t.setCapacity(capacity);
                            t.setSportType(sportType);
                            t.setRentPrice(rentPrice);
                            t.setDisponibility(disponibility);
                            t.setRentPrice(rentPrice);
                            t.setPostalCode(postalCode);
                            t.setRoadName(roadName);
                            t.setRoadNumber(roadNumber);
                            t.setCity(city);
                            t.setCountry(country);
                            t.setImageName(imageName);
                            t.setUpdatedAt(updatedAt);
                            t.setImageUrl(imageUrl);

                            result.add(t);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
}

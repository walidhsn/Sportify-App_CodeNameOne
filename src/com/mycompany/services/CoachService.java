package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Coach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoachService {

    private ArrayList<Coach> academies;
    private static CoachService instance = null;
    private boolean resultOK;
    private ConnectionRequest req;
    private String baseUrl = "http://127.0.0.1:8000/coachapi/";

    private CoachService() {
        req = new ConnectionRequest();
    }

    public static CoachService getInstance() {
        if (instance == null) {
            instance = new CoachService();
        }
        return instance;
    }
    
    public ArrayList<Coach> getAllCoaches() {
        String url = baseUrl;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonParser = new JSONParser();
                try {
                    Map<String, Object> response = jsonParser.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("root");
                    academies = new ArrayList<>();
                    for (Map<String, Object> obj : list) {
                        Coach a = new Coach();
                        float id = Float.parseFloat(obj.get("id").toString());
                        a.setId((int) id);
                        a.setName(obj.get("name").toString());
                        a.setEmail(obj.get("email").toString());
                        a.setPhone(obj.get("telephone").toString());
                        academies.add(a);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return academies;
    }
    
//    public boolean addCoach(Coach a) {
//        String url = baseUrl + "add";
//        req.setUrl(url);
//        req.setPost(false);
//        req.addArgument("name", a.getName());
//        req.addArgument("category", a.getCategory());
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }

    public boolean updateCoach(Coach a) {
        String url = baseUrl + "edit/" + a.getId()+ "?name=" + a.getName() + "&email=" + a.getEmail() + "&telephone=" + a.getPhone();
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("name", a.getName());
        req.addArgument("email", a.getEmail());
        req.addArgument("telephone", a.getPhone());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public void deleteCoach(int id) {
     
        String url = baseUrl + "delete?id=" + id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public boolean addCoach(Coach a) {
        String url = baseUrl + "add?name=" + a.getName()+ "&email=" + a.getEmail()+ "&telephone=" + a.getPhone();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Coach> searchAcademies(String searchText) {
        ArrayList<Coach> result = new ArrayList<>();

        for (Coach coach : academies) {
            if (coach.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                    coach.getEmail().toLowerCase().contains(searchText.toLowerCase())) {
                result.add(coach);
            }
        }

        return result;
    }

//           
//    public boolean updateEquipment(equipment eq) {
//     
//        String url = Statics.BASE_URL + "equipment/update_equipments_mobile?id_s=" + eq.getSupplier().getId()+ "&id_c=" + eq.getCategory().getId()+ "&name=" + eq.getName()+ "&adress=" + eq.getAdress()+ "&type=" + eq.getType()+ "&quantite=" + eq.getQuantity()+ "&price=" + eq.getPrice()+ "&id=" + eq.getId();
//
//        request.setUrl(url);
//        request.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
//                request.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(request);
//
//        return responseResult;
//    }
}

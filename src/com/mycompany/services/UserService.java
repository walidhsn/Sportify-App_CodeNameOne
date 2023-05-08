/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.mycompany.shared.SharedComponents;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.mycompany.entities.User;

/**
 *
 * @author moata
 */
public class UserService {
    public static UserService instance = null;

    private ConnectionRequest req;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public UserService() {
        req = new ConnectionRequest();
    }
    
    public void editUser(Integer id,TextField password,TextField email,TextField phone,TextField firstname,TextField lastname, TextField nomutilisateur)
    {
        String URL = SharedComponents.BASE_URL+"/api/editUser";
        req.setUrl(URL);
        req.addArgument("email", email.getText());
        req.addArgument("password",password.getText());
        req.addArgument("firstname",firstname.getText());
        req.addArgument("lastname",lastname.getText());
        req.addArgument("phone",phone.getText());
        req.addArgument("nomutilisateur",nomutilisateur.getText());
        req.addArgument("id", id.toString());
        
        req.addResponseListener((e) -> {
            try {
                JSONParser j = new JSONParser();
                String json = new String(req.getResponseData()) + "";
                Map<String,Object> result = j.parseJSON(new CharArrayReader(json.toCharArray()));
                if(result.containsKey("success")){
                    Dialog.show("Success", "User updated successfully!", "OK", null);
                }else
                    Dialog.show("Fail", "Failed to update user"+nomutilisateur.getText(), "OK", null);
            } catch (IOException ex) {
                    Dialog.show("Error",ex.getMessage(),"OK",null);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    
    
    public ArrayList<User> displayUsers() {
        
        ArrayList<User> result = new ArrayList<>();
        
        String url = SharedComponents.BASE_URL+"/api/users";
        req.setUrl(url);
        
        req.addResponseListener((NetworkEvent evt) -> {
            
            JSONParser jsonp ;
            jsonp = new JSONParser();
            
            try {
                Map<String,Object> mapUsers = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                List<Map<String,Object>> listOfUsers;
                listOfUsers =  (List<Map<String,Object>>) mapUsers.get("root");
                
                for (Map<String, Object> obj : listOfUsers){
                    User u = new User();
        
        float id = Float.parseFloat(obj.get("id").toString());
        boolean status = Boolean.parseBoolean(obj.get("status").toString());
        String email = obj.get("email").toString();
        String nomutilisateur = obj.get("nomutilisateur").toString();
        String phone = obj.get("phone").toString();
        String firstname = obj.get("firstname").toString();
        String lastname = obj.get("lastname").toString();
        
        u.setId((int)id);
        u.setStatus(status);
        u.setEmail(email);
        u.setNomutilisateur(nomutilisateur);
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setPhone(phone);
        
        result.add(u);
                    
                    
                }
            
            }catch(Exception ex) {
                
                ex.printStackTrace();
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);

        return result;   
    }
    
    public void blockUnblock(Integer id)
    {
        String URL = SharedComponents.BASE_URL+"/api/blockUnblock";
        req.setUrl(URL);
        req.addArgument("id",id.toString());
        req.addResponseListener((e) -> {
            try {
                JSONParser j = new JSONParser();
                String json = new String(req.getResponseData()) + "";
                Map<String,Object> result = j.parseJSON(new CharArrayReader(json.toCharArray()));
                if(result.containsKey("success")){
                    Dialog.show("Success", "User status changed successfully", "OK", null);
                }else
                    Dialog.show("Fail", "Failed to perform action on user", "OK", null);
            } catch (IOException ex) {
                    Dialog.show("Error",ex.getMessage(),"OK",null);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
}

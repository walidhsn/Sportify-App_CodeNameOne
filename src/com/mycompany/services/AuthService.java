/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.shared.SharedComponents;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author moata
 */
public class AuthService {

    public static AuthService instance = null;

    private ConnectionRequest req;
    private String access;

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public AuthService() {
        req = new ConnectionRequest();
    }

    public String signin(TextField login, TextField password, Resources rs) {
        this.access = "fail";
        User currentUser = new User();
        String url = SharedComponents.BASE_URL + "/api/login";
        req.addArgument("email", login.getText().toString());
        req.addArgument("password", password.getText().toString());
        req.setUrl(url);
        req.addResponseListener((e) -> {

            JSONParser j = new JSONParser();

            String json = new String(req.getResponseData()) + "";

//            System.out.println(json);
            try {

                if (json.contains("fail")) {
                    Map<String, Object> failMessage = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    Map<String, Object> message = (Map<String, Object>) failMessage.get("fail");
                    this.access = message.get("Error").toString();
//                    Dialog.show("Echec d'authentification", failMessage.get("fail").toString(), "OK", null);
                } else {

                    Map<String, Object> userObject = j.parseJSON(new CharArrayReader(json.toCharArray()));
                    

                    if (userObject.get("user") != null) {
                        Map<String, Object> user = (Map<String, Object>) userObject.get("user");
                        this.access = "success";
                        //Session 
                        double idDouble = (Double) user.get("id");
                        int id = (int) idDouble;
//                SessionManager.setId(id);              
//                SessionManager.setPassword(user.get("password").toString());
//                SessionManager.setUserName(user.get("username").toString());
//                SessionManager.setEmail(user.get("email").toString());
                        currentUser.setId(id);
                        currentUser.setEmail(user.get("email").toString());
                        currentUser.setFirstname(user.get("firstname").toString());
                        currentUser.setLastname(user.get("lastname").toString());
                        currentUser.setPhone(user.get("phone").toString());
                        currentUser.setStatus(user.get("status").toString() == "true");
                        currentUser.setNomutilisateur(user.get("nomutilisateur").toString());
                        ArrayList rolesList = (ArrayList) user.get("roles");
                        currentUser.setRoles(rolesList);
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        if (access == "success") {
            SharedComponents.user = currentUser;
        } else {
            System.out.println(access);
        }
        return access;
    }

}

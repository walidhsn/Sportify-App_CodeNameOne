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
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.List;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.shared.SharedComponents;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author moata
 */
public class RegisterService {
    public static RegisterService instance = null;
    
    private ConnectionRequest req;
    
    public static RegisterService getInstance(){
        if(instance == null)
            instance = new RegisterService();
        return instance;
    }
    
    public RegisterService(){
        req = new ConnectionRequest();
    }
    
    public void signUp(TextField firstname,TextField lastname,TextField phone,
            TextField email,TextField password,TextField confirmPassword,
            TextField nomutilisateur,ComboBox<String> roles,Resources res){
        String result;
        req.setUrl(SharedComponents.BASE_URL+"/api/register");
        req.addArgument("email", email.getText().toString());
        req.addArgument("password", password.getText().toString());
        req.addArgument("nomutilisateur", nomutilisateur.getText().toString());
        req.addArgument("roles", roles.getSelectedItem());
        req.addArgument("firstname", firstname.getText().toString());
        req.addArgument("lastname", lastname.getText().toString());
        req.addArgument("phone", phone.getText().toString());
        if(email.getText().equals(" ") && password.getText().equals(" ")
                && nomutilisateur.getText().equals(" ") && firstname.getText().equals(" ")
                && phone.getText().equals(" ") && lastname.getText().equals(" ")
                ){
            Dialog.show("Error","Please fill in all the fields");
        }
        req.addResponseListener((e)-> {
            byte[] data = (byte[])e.getMetaData();
            String responseData = new String(data);
            System.out.println("data : \n"+responseData);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
}

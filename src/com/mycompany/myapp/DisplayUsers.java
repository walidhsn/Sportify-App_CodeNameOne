/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.UserService;
import java.util.ArrayList;

/**
 *
 * @author moata
 */
public class DisplayUsers extends Form{
    public DisplayUsers(Resources res) {
        setTitle("Users");

        // Create a container to hold the list of products
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        // Retrieve the list of products from the server
        ArrayList<User> users = UserService.getInstance().displayUsers();

        // Loop through the list of products and add them to the container
        for (User user : users) {
            // Create a new container to hold the product details
            Container userContainer = new Container(new BorderLayout());

            // Create labels to display the product details
            Label emailLabel = new Label("Email: " + user.getEmail());
            Label usernameLabel = new Label("Username: " + user.getNomutilisateur());
            Label phoneLabel = new Label("Phone: " + user.getPhone());
            Label firstname = new Label("Firstname: " + user.getFirstname());
            Label lastname = new Label("Lastname: " + user.getLastname());
            String buttonText;
            if(user.isStatus()){
                buttonText = "Unblock";
            }else
                buttonText = "Block";
            Button blockUser = new Button(buttonText);

            // Add the labels to the produitContainer
            userContainer.addComponent(BorderLayout.NORTH, emailLabel);
            userContainer.addComponent(BorderLayout.WEST, usernameLabel);
            userContainer.addComponent(BorderLayout.CENTER, phoneLabel);
            userContainer.addComponent(BorderLayout.EAST, firstname);
            userContainer.addComponent(BorderLayout.EAST, lastname);
            userContainer.addComponent(BorderLayout.EAST, blockUser);
            blockUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Show the "Add Product" form
                    UserService.getInstance().blockUnblock(user.getId());
                    new DisplayUsers(res).show();
                }
            });
        

        }
    }
}

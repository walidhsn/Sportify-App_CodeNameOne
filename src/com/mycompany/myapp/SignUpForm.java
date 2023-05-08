/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.myapp;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.RegisterService;
import java.util.Vector;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends Form {

    public SignUpForm(Resources res) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_TOTAL_BELOW));
        setUIID("LoginForm");
                
        TextField firstname = new TextField("", "First name", 20, TextField.ANY);
        TextField lastname = new TextField("", "Last name", 20, TextField.ANY);
        TextField phone = new TextField("", "Phone", 20, TextField.ANY);
        TextField nomutilisateur = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        
           //Role 
        Vector<String> vectorRole;
        vectorRole = new Vector();
        
        vectorRole.add("ROLE_CLIENT");
        vectorRole.add("ROLE_OWNER");
        
        ComboBox<String>roles = new ComboBox<>(vectorRole);
        
        
        
        
        firstname.setSingleLineTextArea(false);
        lastname.setSingleLineTextArea(false);
        nomutilisateur.setSingleLineTextArea(false);
        phone.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        Button next = new Button("Sign up");
        next.setUIID("LoginButton");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> new LoginForm(res).show());
        signIn.setUIID("Sign in");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("         Please Sign Up", "WelcomeWhite"),
                new FloatingHint(firstname),
                new FloatingHint(lastname),
                new FloatingHint(nomutilisateur),
                new FloatingHint(phone),
                new FloatingHint(email),
                new FloatingHint(password),
                new FloatingHint(confirmPassword),
                roles
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((e) -> {
            try{
                RegisterService.getInstance().signUp(firstname,lastname,phone,
            email,password,confirmPassword,
            nomutilisateur,roles,res);
                Dialog.show("Success","account is saved","OK",null);
            new LoginForm(res).show();
            }catch(Exception ex){
                Dialog.show("Error",ex.getMessage());
                System.out.println(ex.getMessage());
            } 
        });
       
    }
    
}
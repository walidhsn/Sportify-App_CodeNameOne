/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp;

/**
 *
 * @author moata
 */
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import java.util.*;

public class DashboardForm extends Form {
    
    public DashboardForm() {
        super("Dashboard", BoxLayout.y());
        
        // create labels to display random values
        Label salesLabel = new Label("Sales: " + getRandomValue());
        Label expensesLabel = new Label("Expenses: " + getRandomValue());
        Label profitLabel = new Label("Profit: " + getRandomValue());
        
        // add labels to form
        add(salesLabel);
        add(expensesLabel);
        add(profitLabel);
        
        // create button to refresh values
        Button refreshButton = new Button("Refresh");
        refreshButton.addActionListener(e -> {
            // update labels with new random values
            salesLabel.setText("Sales: " + getRandomValue());
            expensesLabel.setText("Expenses: " + getRandomValue());
            profitLabel.setText("Profit: " + getRandomValue());
        });
        
        // add button to form
        add(refreshButton);
    }
    
    // generate a random value between 100 and 1000
    private int getRandomValue() {
        return new Random().nextInt(901) + 100;
    }
    
}

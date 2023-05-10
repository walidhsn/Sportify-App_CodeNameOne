/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

/**
 *
 * @author MOLKA
 */
public class Admin_InterfaceForm  extends Form {

    Resources theme = UIManager.initFirstTheme("/theme_1");

    public Admin_InterfaceForm(Form previous) {
     super("Admin",BoxLayout.y());
     this.getToolbar().addCommandToSideMenu("Category", null, e -> {
         Admin_CategoryForm w = new Admin_CategoryForm(previous);
        w.show();    
        });
     this.getToolbar().addCommandToSideMenu("Suppliers", null, e -> {
            Admin_SupplierForm w = new Admin_SupplierForm(previous);
        w.show();
        });
     this.getToolbar().addCommandToSideMenu("Equipment", null, e -> {
            Admin_EquipmentForm w = new Admin_EquipmentForm(previous);
        w.show();
        });
     this.getToolbar().addCommandToSideMenu("Rent", null, e -> {
            Admin_RentForm w = new Admin_RentForm(previous);
        w.show();
        });
    }
    
}

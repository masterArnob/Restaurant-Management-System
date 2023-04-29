/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management.system;

import javafx.scene.control.Alert;

/**
 *
 * @author ASUS
 */
public class alert {
    
    Alert alert;
    public void showAlertForErrror(String msg)
    {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public void successAlert(String msg)
    {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

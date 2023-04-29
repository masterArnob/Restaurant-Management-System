/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management.system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DeleteAccountController implements Initializable {

    @FXML
    private Button deleteAccount;
    @FXML
    private TextField pass;
    @FXML
    private AnchorPane delete_Page;
    @FXML
    private Button backtoSettings;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private Connection con;
    private Statement st;
    private ResultSet rs;

    private String p;

    alert a = new alert();
    Alert alert;
    
    @FXML
    public void deleteAccount() {

         String Pass = pass.getText();
        String sql = "SELECT password FROM admin WHERE email ='" + data.useremail + "'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                p = rs.getString(1);
                if (p.equals(Pass)) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you want to delete account?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        String sql2 = "DELETE FROM admin WHERE email = '" + data.useremail + "'";
                        st.executeUpdate(sql2);
                        a.successAlert("Successfully Deleted");
                        System.out.println("Password Matched");
                        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        delete_Page.getScene().getWindow().hide();

                    }

                } else {

                    a.showAlertForErrror("Password does not Match");
                    System.out.println("Password does not matched");
                }

            }
            System.out.println("Password is : " + p);

        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    

    @FXML
   public void backtoSettings() throws IOException
   {
       Parent root = FXMLLoader.load(getClass().getResource("AfterLogin.fxml"));

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    delete_Page.getScene().getWindow().hide();
   }
}

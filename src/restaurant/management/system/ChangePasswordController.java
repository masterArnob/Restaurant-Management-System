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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private TextField oldPass;
    @FXML
    private TextField newPass;
    @FXML
    private Label status;
    @FXML
    private Button submit;
    @FXML
    private AnchorPane changePassword_page;
    @FXML
    private Button gotoSettings;

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
    alert a = new alert();
    private String p;
    String Username = null;
    Alert alert;

    @FXML
    public void gotoSettings() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AfterLogin.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        changePassword_page.getScene().getWindow().hide();
    }

    @FXML
    public void submit() {
        String OldPass = oldPass.getText();
        String NewPass = newPass.getText();
        if (OldPass.isEmpty()
                || NewPass.isEmpty()) {
            a.showAlertForErrror("Pleasw fill all the information");
        } else if (OldPass.equals(NewPass)) {
            status.setText("*Old password and new password can't be same");
        } else {
            status.setText("");
            String sql = "SELECT password FROM admin WHERE email ='" + data.useremail + "'";
            try {
                con = database.connectDB();
                st = con.createStatement();
                rs = st.executeQuery(sql);

                if (rs.next()) {
                    p = rs.getString(1);
                    if (p.equals(OldPass)) {
                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Are you sure you want to change password?");
                        Optional<ButtonType> option = alert.showAndWait();

                        if (option.get().equals(ButtonType.OK)) {
                            String sql2 = "UPDATE admin SET password = '" + NewPass + "' WHERE email = '" + data.useremail + "'";
                            st.executeUpdate(sql2);
                            a.successAlert("Successfully Changed");

                            sendEmail s = new sendEmail();
                            String msg = "Password is changed successfully";
                            String email = data.useremail;
                            s.email(Username, email, msg);

                            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                            changePassword_page.getScene().getWindow().hide();

                        }

                    } else {

                        a.showAlertForErrror("Old password does not Match");
                        System.out.println("Password does not matched");
                    }

                }

            } catch (Exception e) {
                System.out.println("" + e);
            }

        }
    }

}

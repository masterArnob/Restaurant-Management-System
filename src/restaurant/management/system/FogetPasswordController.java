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
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FogetPasswordController implements Initializable {

    @FXML
    private AnchorPane forgePassword_Page;
    @FXML
    private TextField email;
    @FXML
    private Button sendOTP;
    @FXML
    private Label status;
    @FXML
    private Button backTOLOGIN;
    @FXML
    private AnchorPane otp_Page;
    @FXML
    private TextField otp;
    @FXML
    private Button submitOTP;
    @FXML
    private AnchorPane newPassword_page;
    @FXML
    private TextField password;
    @FXML
    private Button newPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private Statement st;
    private Connection con;
    private ResultSet rs;
    alert a = new alert();

    private int num;
    private String Email;

    @FXML
    private void sendOTP(ActionEvent event) {

        Random rand = new Random();
        num = (int) (Math.random() * 9000) + 1000;

        Email = email.getText();

        String username = null;

        String emailChecker = "^[A-Z0-9._%+-]+@[A-Z0-9._]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailChecker, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(Email);

        if (Email.isEmpty()) {
            a.showAlertForErrror("Email is empty");
        } else if (emailPattern.matcher(Email).matches()) {
            try {

                String sql = "SELECT * FROM admin WHERE email = '" + Email + "'";
                con = database.connectDB();
                st = con.createStatement();
                rs = st.executeQuery(sql);
                if (rs.next()) {

                    String Text = "Your OTP is " + num;
                    sendEmail s = new sendEmail();
                    s.email(username, Email, Text);
                    System.out.println("User exist");
                    System.out.println("OTP is sent successfully");

                    otp_Page.setVisible(true);
                    forgePassword_Page.setVisible(false);
                    newPassword_page.setVisible(false);

                } else {
                    a.showAlertForErrror("Email does not exist");
                }

            } catch (Exception e) {
                System.out.println("" + e);
            }
        } else {
            status.setText("Email is not valid");
        }

    }

    @FXML
    public void submitOTP() {
        int Otp = Integer.parseInt(otp.getText());
        if (Otp == 0) {
            a.showAlertForErrror("Please enter your OTP");
        } else if (Otp == num) {
            otp_Page.setVisible(false);
            forgePassword_Page.setVisible(false);
            newPassword_page.setVisible(true);

            a.successAlert("OTP Matched");
        } else {
            a.showAlertForErrror("OTP does not match");
        }
    }

    @FXML
    private void backTOLOGIN(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        otp_Page.getScene().getWindow().hide();
    }

    @FXML
    private void newPass(ActionEvent event) {
        String username = null;
        String pass = password.getText();
        String sql = "UPDATE admin SET password = '" + pass + "' WHERE email = '" + Email + "'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            st.executeUpdate(sql);
            String Text = "Password is Password is successfully reset";
            a.successAlert("Password is successfully changed");
            sendEmail s = new sendEmail();
            s.email(username, Email, Text);
            System.out.println("Password is changed");
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            newPassword_page.getScene().getWindow().hide();
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

}

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
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LoginController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button Login;
    @FXML
    private AnchorPane Login_page;

    /**
     * Initializes the controller class.
     */
    Alert alert;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    @FXML
    private Label status2;
    private CheckBox show_password;

    alert a = new alert();

    @FXML
    public void Login() throws IOException {
        String Email = email.getText();
        String Password = password.getText();

        String emailCheck = "^[A-Z0-9._%+-]+@[A-Z0-9._]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailCheck, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(Email);

        if (Email.isEmpty()
                || Password.isEmpty()) {
            a.showAlertForErrror("please fill all the required information");
            status2.setText("");
        } else if (emailPattern.matcher(Email).matches()) {
            status2.setText("");
            String sql = "SELECT * FROM admin WHERE email = '" + Email
                    + "' and password = '" + Password + "'";
            try {
                con = database.connectDB();
                st = con.createStatement();
                rs = st.executeQuery(sql);

                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Welcome");
                    alert.showAndWait();

                    data.useremail = Email;

                    Parent root = FXMLLoader.load(getClass().getResource("AfterLogin.fxml"));

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    Login_page.getScene().getWindow().hide();
                } else {

                    a.showAlertForErrror("Emal is not registered");

                }

            } catch (Exception e) {
                System.out.println("" + e);
            }

        } else {
            status2.setText("*Email is not valid");
        }

    }

    @FXML
    public void gotoSignup() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Login_page.getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void fogetPassword(ActionEvent event) throws IOException
    {
         Parent root = FXMLLoader.load(getClass().getResource("fogetPassword.fxml"));

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    Login_page.getScene().getWindow().hide();
    }

}

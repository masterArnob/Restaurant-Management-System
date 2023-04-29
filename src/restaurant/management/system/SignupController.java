/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management.system;

import java.io.File;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class SignupController implements Initializable {

    @FXML
    private AnchorPane Signup_page;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Label status;
    @FXML
    private Button signUP;
    @FXML
    private Button back;
    @FXML
    private ImageView userImage;
    @FXML
    private Button insertUserImage;
    @FXML
    private Label userImagePath;
    @FXML
    private AnchorPane left_main2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    alert a = new alert();
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public void clear() {
        username.setText("");
        email.setText("");
        password.setText("");
        status.setText("");
    }

    @FXML
    public void back() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Signup_page.getScene().getWindow().hide();
    }

    @FXML
    private void insertUserImage(ActionEvent event) {
        FileChooser open = new FileChooser();

        Stage stage = (Stage) left_main2.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if (file != null) {

            String path = file.getAbsolutePath();

            path = path.replace("\\", "\\\\");

            userImagePath.setText(path);

            Image image = new Image(file.toURI().toString());

            userImage.setImage(image);

        } else {

            System.out.println("NO DATA EXIST!");

        }
    }
    
    @FXML
    public void signUP() throws IOException {

        String Username = username.getText();
        String Email = email.getText();
        String Password = password.getText();

        String Picture = userImagePath.getText();
        String emailChecker = "^[A-Z0-9._%+-]+@[A-Z0-9._]+\\.[A-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailChecker, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(Email);

        if (Username.isEmpty()
                || Email.isEmpty()
                || Password.isEmpty()) {
            a.showAlertForErrror("please fill all the required information");
        } else if (emailPattern.matcher(Email).matches()) {

            status.setText("");
            String sql = "INSERT INTO admin(username, email, password, pic) "
                    + "VALUES('" + Username + "','" + Email + "','" + Password +"','"+Picture+"')";

            try {
                con = database.connectDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                a.successAlert("Successfully Sign up");

                String Message = "Dear " + Username + ",\n\nI would like to extend a warm welcome to Arnob's project on behalf of Testbot2. My name is TestBot2, and I am here to assist you in any way I can.\n\nThank you\n\nBest regards,\nTestBot2";
                sendEmail s = new sendEmail();
                s.email(Username, Email, Message);

                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Signup_page.getScene().getWindow().hide();

            } catch (Exception e) {
                a.showAlertForErrror("Email already exist");

            }

        } else {

            status.setText("*Email is not valid");
        }

    }

}

   

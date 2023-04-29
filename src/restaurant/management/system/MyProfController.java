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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MyProfController implements Initializable {

    @FXML
    private Label myProf_username;
    @FXML
    private Label myProf_email;
    @FXML
    private Label myProf_password;
    @FXML
    private Button backtoSettings;
    @FXML
    private AnchorPane myProf_page;
    @FXML
    private Label myProf_reg;

    /**
     * Initializes the controller class.
     */
    
    
    private String userImage;
    @FXML
    private ImageView uerImage2;

    public void showUserImage() {
        String sql = "SELECT pic FROM admin WHERE email = '" + data.useremail + "'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                userImage = rs.getString(1);
            }
            System.out.println("" + userImage);

            String picture = "file:" + userImage;

            picture = picture.replace("\\", "\\\\");

            Image image = new Image(picture);

            uerImage2.setImage(image);

            uerImage2.setFitHeight(image.getHeight());

        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showDetails();
        showUserImage();
    }

    private Connection con;
    private Statement st;
    private ResultSet rs;

    private String name;
    private String email;
    private String pass;
    private String reg;

    public void showDetails() {
        String sql = "SELECT username, password, reg_date FROM admin WHERE email = '" + data.useremail + "'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                name = rs.getString(1);
                pass = rs.getString(2);
                reg  =  rs.getString(3);
            }
            System.out.println("name :" + name);
            System.out.println("Pass : " + pass);
            System.out.println("Reg : " +reg);

            myProf_username.setText(name);
            myProf_email.setText(data.useremail);
            myProf_password.setText(pass);
            myProf_reg.setText(reg);
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    @FXML
    private void backtoSettings(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AfterLogin.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        myProf_page.getScene().getWindow().hide();
    }

}

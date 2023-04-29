package restaurant.management.system;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AfterLoginController implements Initializable {

    @FXML
    private Button dashboard_btn;
    @FXML
    private Button order_btn;
    @FXML
    private Button products_btn;
    @FXML
    private AnchorPane dashboard_page;
    @FXML
    private AnchorPane product_page;
    @FXML
    private AnchorPane order_page;

    @FXML
    private TextField productID;
    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;
    @FXML
    private ComboBox<String> productStatus;

    private String[] typesOfFood = {"Food", "Drinks"};
    @FXML
    private ComboBox<String> productType;
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private TableView<productList> product_table;
    @FXML
    private TableColumn<productList, String> product_col_ID;
    @FXML
    private TableColumn<productList, String> product_col_Name;
    @FXML
    private TableColumn<productList, String> product_col_Type;
    @FXML
    private TableColumn<productList, String> product_col_Price;
    @FXML
    private TableColumn<productList, String> product_col_Status;
    @FXML
    private TextField PSearch;
    @FXML
    private ImageView productImage;
    @FXML
    private Label productImagePath;
    @FXML
    private Button insertProductImage;
    private AnchorPane left_main1;
    @FXML
    private Label greetings;
    @FXML
    private ComboBox<String> order_product_ID;
    @FXML
    private ComboBox<String> order_product_Name;
    @FXML
    private TextField order_product_Quantity;
    @FXML
    private Label order_product_Price;
    @FXML
    private Button AddToCart;
    @FXML
    private TableView<cartList> order_cart_table;
    @FXML
    private TableColumn<cartList, String> order_col_productID;
    @FXML
    private TableColumn<cartList, String> order_col_productName;
    @FXML
    private TableColumn<cartList, String> order_col_productType;
    @FXML
    private TableColumn<cartList, String> order_col_productQuantity;
    @FXML
    private TableColumn<cartList, String> order_col_productPrice;
    @FXML
    private Label cartTotal;
    @FXML
    private Button pay;
    @FXML
    private TextField order_amount;
    @FXML
    private Label change;
    @FXML
    private Button removeFromCart;
    @FXML
    private Label numofp;
    @FXML
    private Label no;
    @FXML
    private Label tIncome;
    @FXML
    private AreaChart<?, ?> dashboardChart;
    @FXML
    private AnchorPane afterLogin_page;
    @FXML
    private Button logout;
    @FXML
    private Label user;
    @FXML
    private AnchorPane settings_page;
    @FXML
    private Button settings_btn;
    @FXML
    private Label name;
    @FXML
    private AnchorPane myProfile_page;
    @FXML
    private Label useEmail;
    @FXML
    private Label useName;
    @FXML
    private Label usePassword;
    @FXML
    private Button myProfile_btn;
    @FXML
    private Button deleteAccount_btn;
    @FXML
    private Button about_btn;
    @FXML
    private Button changePassword_btn;
    @FXML
    private ImageView uerImage2;
    @FXML
    private Button productListDownload;
    @FXML
    private AnchorPane left_main7;

    public void type() {
        List<String> foodType = new ArrayList<>();
        for (String data : typesOfFood) {
            foodType.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(foodType);
        productType.setItems(listData);
    }

    private String[] statusOfFood = {"Available", "Sold-Out"};

    public void status() {
        List<String> foodStatus = new ArrayList<>();
        for (String data : statusOfFood) {
            foodStatus.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(foodStatus);
        productStatus.setItems(listData);
    }

    private Connection con;
    private Statement st;
    private ResultSet rs;

    alert a = new alert();

    @FXML
    public void add() {

        String product_id = productID.getText();
        String product_name = productName.getText();
        String Type = productType.getSelectionModel().getSelectedItem().toString();
        int price = Integer.parseInt(productPrice.getText());
        String status = productStatus.getSelectionModel().getSelectedItem().toString();
        String Picture = productImagePath.getText();
        if (product_id.isEmpty()
                || product_name.isEmpty()
                || Type.isEmpty()
                || status.isEmpty()
                || Picture.isEmpty()) {
            a.showAlertForErrror("Please fill all the required information");
        } else {
            String sql = "INSERT INTO product(product_id, product_name, type, price, status, picture)"
                    + " VALUES('" + product_id + "','" + product_name + "','" + Type + "'," + price + ",'" + status + "','" + Picture + "')";

            try {
                con = database.connectDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                System.out.println("Added");
                a.successAlert("Successfully added");
                clear();
                showProductList();
            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

    }

    @FXML
    public void productSearch() {
        FilteredList<productList> filter = new FilteredList<>(Listp, e -> true);
        PSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
            filter.setPredicate(predicateProductList -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateProductList.getProduct_id().toString().contains(searchKey)) {
                    return true;
                } else if (predicateProductList.getProduct_name().toString().contains(searchKey)) {
                    return true;
                } else if (predicateProductList.getType().toString().contains(searchKey)) {
                    return true;
                } else if (predicateProductList.getStatus().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<productList> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(product_table.comparatorProperty());

        product_table.setItems(sortList);
    }

    Alert alert;

    @FXML
    public void update() {
        String product_id = productID.getText();
        String product_name = productName.getText();
        String Type = productType.getSelectionModel().getSelectedItem().toString();
        int price = Integer.parseInt(productPrice.getText());
        String status = productStatus.getSelectionModel().getSelectedItem().toString();
        String Picture = productImagePath.getText();

        Picture = Picture.replace("\\", "\\\\");

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to update Product ID : " + product_id);
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            String sql = "UPDATE product SET product_name = '" + product_name + "', type = '" + Type + "', price = " + price + ", status = '" + status + "', picture = '" + Picture + "' WHERE product_id = '" + product_id + "'";

            try {
                con = database.connectDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                System.out.println("Updated");
                a.successAlert("Successfully updated");
                showProductList();
            } catch (Exception e) {
                System.out.println("" + e);
            }

        } else {
            a.successAlert("Cancelled");
        }

    }

    @FXML
    public void delete() {
        String product_id = productID.getText();
        String sql = "DELETE FROM product WHERE product_id = '" + product_id + "'";

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to delete Product ID : " + product_id);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {
            try {
                con = database.connectDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                alert = new Alert(Alert.AlertType.INFORMATION);
                a.successAlert("Succssfully Deleted");
                showProductList();
                clear();
            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

    }

    private ObservableList<productList> pList() {
        ObservableList listData = FXCollections.observableArrayList();

        String sql = "select * from product";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            productList p;

            while (rs.next()) {
                p = new productList(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getString("type"),
                        rs.getInt("price"),
                        rs.getString("status"),
                        rs.getString("picture")
                );
                listData.add(p);
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return listData;
    }

    private ObservableList<productList> Listp;

    public void showProductList() {
        Listp = pList();
        product_col_ID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        product_col_Name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        product_col_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        product_col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        product_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));

        product_table.setItems(Listp);
    }

    @FXML
    public void clear() {
        productID.setText("");
        productName.setText("");
        productType.getSelectionModel().clearSelection();
        productPrice.setText("");
        productStatus.getSelectionModel().clearSelection();
        productImagePath.setText("");
        productImage.setImage(null);
    }

    @FXML
    public void productSelect() {

        productList p = product_table.getSelectionModel().getSelectedItem();
        int num = product_table.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }

        productID.setText(p.getProduct_id());
        productName.setText(p.getProduct_name());
        productType.setValue(p.getType());
        productPrice.setText(String.valueOf(p.getPrice()));
        productStatus.setValue(p.getStatus());

        String picture = "file:" + p.getPicture();

        Image image = new Image(picture);

        productImage.setImage(image);

        String path = p.getPicture();

        productImagePath.setText(path);
        productImage.setImage(image);

        productImage.setPreserveRatio(true);
        productImage.setFitWidth(180); // set the maximum width to 100
        productImage.setFitHeight(200);

    }

    @FXML
    private void insertProductImage(ActionEvent event) {
        FileChooser open = new FileChooser();

        Stage stage = (Stage) left_main7.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if (file != null) {

            String path = file.getAbsolutePath();

            path = path.replace("\\", "\\\\");

            productImagePath.setText(path);

            Image image = new Image(file.toURI().toString());

            productImage.setImage(image);
            productImage.setFitWidth(180); // set the maximum width to 100
            productImage.setFitHeight(200);
        } else {

            System.out.println("NO DATA EXIST!");

        }
    }

    public void Grettings() {
        LocalTime time = LocalTime.now();
        int hour = time.getHour();

        if (hour >= 5 && hour < 12) {
            greetings.setText("Good Morning");
        } else if (hour >= 12 && hour < 18) {
            greetings.setText("Good Afternoon");
        } else if (hour >= 18 && hour < 24) {
            greetings.setText("Good Evening");
        } else {
            greetings.setText("Good Night");
        }
    }

    @FXML
    public void orderProductID() {
        String sql = "SELECT product_id FROM product WHERE status = 'Available'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            ObservableList listData = FXCollections.observableArrayList();
            while (rs.next()) {
                listData.add(rs.getString("product_id"));
            }
            order_product_ID.setItems(listData);

        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    @FXML
    public void orderProductName() {

        String sql = "SELECT product_name FROM product WHERE product_id = '" + order_product_ID.getSelectionModel().getSelectedItem() + "'";

        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            ObservableList listData = FXCollections.observableArrayList();
            while (rs.next()) {
                listData.add(rs.getString("product_name"));
            }
            order_product_Name.setItems(listData);
            orderType();
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    private int qty;
    private int result;
    private int totalPrice;
    private String orderType;

    @FXML
    public void QantityAndShowPrice() {
        String sql = "SELECT price FROM product WHERE product_id = '" + order_product_ID.getSelectionModel().getSelectedItem() + "' and product_name = '" + order_product_Name.getSelectionModel().getSelectedItem() + "'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                totalPrice = rs.getInt("price");
            }
            qty = Integer.parseInt(order_product_Quantity.getText());
            result = (qty * totalPrice);
            order_product_Price.setText("$" + result);
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public void orderType() {
        String sql = "SELECT type FROM product WHERE product_id = '" + order_product_ID.getSelectionModel().getSelectedItem() + "'";

        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                orderType = rs.getString("type");
            }
            System.out.println("Order Type is  " + orderType);
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    public ObservableList<cartList> cartData() {
        String sql = "SELECT * FROM customerorder";
        ObservableList listData = FXCollections.observableArrayList();
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            cartList cList;

            while (rs.next()) {
                cList = new cartList(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getString("type"),
                        rs.getInt("quantity"),
                        rs.getInt("price")
                );
                listData.add(cList);
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }
        return listData;
    }

    private ObservableList<cartList> dataCart;

    public void showCartData() {
        dataCart = cartData();

        order_col_productID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        order_col_productName.setCellValueFactory(new PropertyValueFactory<>("product_name"));
        order_col_productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        order_col_productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        order_col_productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        order_cart_table.setItems(dataCart);

    }

    public void customerRecipt() {
        total();
        String sql2 = "SELECT * FROM customerorder";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = database.connectDB();

            JasperDesign jdesign = JRXmlLoader.load("E:\\Java\\Projects\\Restaurant Management System\\src\\restaurant\\management\\system\\recipt.jrxml");

            JRDesignQuery updateQuery = new JRDesignQuery();

            updateQuery.setText(sql2);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, con);

            JasperViewer.viewReport(jprint, false);

        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    private int count;

    @FXML
    public void pay() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("PAYMENT?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {

            count++;
            System.out.println("Number of Orders : " + count);
            customerRecipt();

            String sql = "DELETE FROM customerorder";

            try {
                con = database.connectDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                showCartData();
                orderClear();

            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

    }

    public void orderClear() {
        order_product_ID.getSelectionModel().clearSelection();
        order_product_Name.getSelectionModel().clearSelection();
        order_product_Quantity.setText("");
        order_product_Price.setText("");
        cartTotal.setText("");
        change.setText("");
        order_amount.setText("");
    }

    private int amount1;
    private int amount2;

    @FXML
    public void amount() {
        amount1 = Integer.parseInt(order_amount.getText());
        if (amount1 < dt) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Amount is low");
            alert.showAndWait();
        } else if (amount1 >= dt) {
            amount2 = (amount1 - dt);
            change.setText("$" + amount2);

            System.out.println(amount2);
        }
    }

    private int dt;

    public void total() {
        String sql = "select sum(price) from customerorder";

        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                dt = rs.getInt("sum(price)");
            }
            System.out.println("Total : " + dt);
            cartTotal.setText("$" + dt);

        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    @FXML
    public void AddToCart() {

        String PID = order_product_ID.getSelectionModel().getSelectedItem().toString();
        String PName = order_product_Name.getSelectionModel().getSelectedItem().toString();
        String PType = orderType;
        int PQty = qty;
        int PPrice = result;
        int tt = dt;

        System.out.println("Cart total is " + tt);
        System.out.println("PID is" + PID);
        String sql = "INSERT INTO customerorder (product_id, product_name, type, quantity, price, total) "
                + "VALUES('" + PID + "','" + PName + "','" + PType + "'," + PQty + "," + PPrice + "," + tt + ")";

        String sql2 = "INSERT INTO orderhistory (product_id, product_name, type, quantity, price) "
                + "VALUES('" + PID + "','" + PName + "','" + PType + "'," + PQty + "," + PPrice + ")";

        try {
            con = database.connectDB();
            st = con.createStatement();
            st.executeUpdate(sql);
            st.executeUpdate(sql2);
            System.out.println("customerOrder is added");
            System.out.println("orderHistory is added");
            showCartData();
            total();

        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    @FXML
    public void CartTableSelect() {

        cartList c = order_cart_table.getSelectionModel().getSelectedItem();
        int num = order_cart_table.getSelectionModel().getSelectedIndex();

        //int num = order_cart_table.getSelectionModel().getFocusedIndex();
        if ((num - 1) < -1) {
            return;
        }

        order_product_ID.setValue(c.getProduct_id());
        order_product_Name.setValue(c.getProduct_name());
        order_product_Price.setText(String.valueOf(c.getPrice()));

    }

    @FXML
    public void removeFromCart() {

        String PID = order_product_ID.getSelectionModel().getSelectedItem().toString();
        String PName = order_product_Name.getSelectionModel().getSelectedItem().toString();

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Remove from Cart?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {
            String sql = "DELETE FROM customerorder WHERE product_id = '" + PID + "' and product_name = '" + PName + "'";

            try {
                con = database.connectDB();
                st = con.createStatement();
                st.executeUpdate(sql);
                System.out.println("Deleted from cart");
                showCartData();
                total();
            } catch (Exception e) {
                System.out.println("" + e);
            }
        }

    }

    private int np;

    public void numberofProducts() {

        String sql = "SELECT count(*) FROM product WHERE status='Available'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                np = rs.getInt("count(*)");
            }
            System.out.println("Number of Prducts : " + np);
            numofp.setText("" + np);
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    public void numberofOrders() {
        no.setText("" + count);
    }

    private int ti;

    public void TodaysIncome() {
        String sql = "select sum(price) from orderhistory";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                ti = rs.getInt("sum(price)");
            }
            System.out.println("Total Income : " + ti);
            tIncome.setText("$ " + ti);
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    public void dashboardChart() {
        dashboardChart.getData().clear();

        String sql = "SELECT date, SUM(price) FROM orderhistory GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 8";
        XYChart.Series chart = new XYChart.Series();

        try {

            con = database.connectDB();
            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                chart.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));

            }

            dashboardChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String uName;

    public void displayUsername() {
        String sql = "SELECT username FROM admin WHERE email = '" + data.useremail + "'";
        try {
            con = database.connectDB();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                uName = rs.getString("username");
            }
            user.setText("" + uName);
            name.setText("" + uName);
            useName.setText("" + uName);
            useEmail.setText("" + data.useremail);

        } catch (Exception e) {
            System.out.println("" + e);
        }

    }

    @FXML
    public void logout() throws IOException {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Logout?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get().equals(ButtonType.OK)) {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            afterLogin_page.getScene().getWindow().hide();
        }

    }

    public void ButtonStyles() {
        dashboard_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        dashboard_btn.setOnMouseEntered(e -> dashboard_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        dashboard_btn.setOnMouseExited(e -> dashboard_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        products_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        products_btn.setOnMouseEntered(e -> products_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        products_btn.setOnMouseExited(e -> products_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        order_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        order_btn.setOnMouseEntered(e -> order_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        order_btn.setOnMouseExited(e -> order_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        add.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        add.setOnMouseEntered(e -> add.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        add.setOnMouseExited(e -> add.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        update.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        update.setOnMouseEntered(e -> update.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        update.setOnMouseExited(e -> update.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        delete.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        delete.setOnMouseEntered(e -> delete.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        delete.setOnMouseExited(e -> delete.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        clear.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        clear.setOnMouseEntered(e -> clear.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        clear.setOnMouseExited(e -> clear.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        insertProductImage.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        insertProductImage.setOnMouseEntered(e -> insertProductImage.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        insertProductImage.setOnMouseExited(e -> insertProductImage.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        AddToCart.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        AddToCart.setOnMouseEntered(e -> AddToCart.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        AddToCart.setOnMouseExited(e -> AddToCart.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        pay.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        pay.setOnMouseEntered(e -> pay.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        pay.setOnMouseExited(e -> pay.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        removeFromCart.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        removeFromCart.setOnMouseEntered(e -> removeFromCart.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        removeFromCart.setOnMouseExited(e -> removeFromCart.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        logout.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        logout.setOnMouseEntered(e -> logout.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        logout.setOnMouseExited(e -> logout.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        settings_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        settings_btn.setOnMouseEntered(e -> settings_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        settings_btn.setOnMouseExited(e -> settings_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        myProfile_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        myProfile_btn.setOnMouseEntered(e -> myProfile_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        myProfile_btn.setOnMouseExited(e -> myProfile_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        changePassword_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        changePassword_btn.setOnMouseEntered(e -> changePassword_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        changePassword_btn.setOnMouseExited(e -> changePassword_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        about_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        about_btn.setOnMouseEntered(e -> about_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        about_btn.setOnMouseExited(e -> about_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        deleteAccount_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        deleteAccount_btn.setOnMouseEntered(e -> deleteAccount_btn.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        deleteAccount_btn.setOnMouseExited(e -> deleteAccount_btn.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

        productListDownload.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;");
        productListDownload.setOnMouseEntered(e -> productListDownload.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        productListDownload.setOnMouseExited(e -> productListDownload.setStyle("-fx-background-color: #1c1c1c; -fx-text-fill: white;"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();
        type();
        status();
        showProductList();
        ButtonStyles();
        Grettings();

        orderProductID();
        orderProductName();

        showCartData();
        numberofProducts();
        numberofOrders();
        TodaysIncome();
        dashboardChart();

        showUserImage();

    }

    @FXML
    private void switchButtons(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_page.setVisible(true);
            product_page.setVisible(false);
            order_page.setVisible(false);
            settings_page.setVisible(false);
            numberofProducts();
            numberofOrders();
            TodaysIncome();
            dashboardChart();
        } else if (event.getSource() == products_btn) {
            dashboard_page.setVisible(false);
            product_page.setVisible(true);
            order_page.setVisible(false);
            settings_page.setVisible(false);
            showProductList();
        } else if (event.getSource() == order_btn) {
            dashboard_page.setVisible(false);
            product_page.setVisible(false);
            order_page.setVisible(true);
            settings_page.setVisible(false);
            orderProductID();
        } else if (event.getSource() == settings_btn) {
            dashboard_page.setVisible(false);
            product_page.setVisible(false);
            order_page.setVisible(false);
            settings_page.setVisible(true);
            showUserImage();
        }

    }

    @FXML
    private void myProfile_btn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("myProf.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        settings_page.getScene().getWindow().hide();
    }

    @FXML
    private void deleteAccount_btn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("deleteAccount.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        settings_page.getScene().getWindow().hide();
    }

    @FXML
    private void changePassword_btn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        settings_page.getScene().getWindow().hide();
    }

    @FXML
    private void about_btn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        settings_page.getScene().getWindow().hide();
    }

    private String userImage;

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

    @FXML
    public void productListDownload() {
        try {

            con = database.connectDB();

            JasperDesign jdesign = JRXmlLoader.load("E:\\Java\\Projects\\Restaurant Management System\\src\\restaurant\\management\\system\\productDownload.jrxml");

            String sql = "select * from product";
            JRDesignQuery updateQuery = new JRDesignQuery();

            updateQuery.setText(sql);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, con);

            JasperViewer.viewReport(jprint, false);

        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

}

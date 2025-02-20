package controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXButton;
import config.AppModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.User;
import service.custom.CustomerService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    private static User currentUser;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        setUserButton();
    }

    private void setUserButton() {
        if (currentUser.getRole().equals("Admin")) {
            btnUserName.setText("Admin - " + currentUser.getUserName());
        } else {
            btnUserName.setText(currentUser.getUserName());
        }
    }

    @FXML
    public Label lbl;

    @FXML
    public JFXButton btnUserName;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnCustomerManagement;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnOrderManagement;

    @FXML
    private JFXButton btnProductmanagement;

    @FXML
    private AnchorPane paneLoadFXML;

    @FXML
    private AnchorPane paneNavigation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/dahboard-view.fxml"));
            Injector injector = Guice.createInjector(new AppModule());
            fxmlLoader.setControllerFactory(injector::getInstance);
            AnchorPane anchorPane = fxmlLoader.load();
            paneLoadFXML.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCustomerManagementOnAction(ActionEvent event) {
        try {
            Injector injector = Guice.createInjector(new AppModule());
            FXMLLoader loader =new FXMLLoader(this.getClass().getResource("/view/customer-management-view.fxml"));
            loader.setControllerFactory(injector::getInstance);
            this.paneLoadFXML.getChildren().clear();
            this.paneLoadFXML.getChildren().add(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        try {
            Injector injector = Guice.createInjector(new AppModule());
            FXMLLoader loader =new FXMLLoader(this.getClass().getResource("/view/dahboard-view.fxml"));
            loader.setControllerFactory(injector::getInstance);
            this.paneLoadFXML.getChildren().clear();
            this.paneLoadFXML.getChildren().add(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnLogOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("You will be redirected to the login page.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage currentStage = (Stage) lbl.getScene().getWindow();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login-signup-view.fxml"));
                    Scene loginScene = new Scene(loader.load());

                    Stage stage = new Stage();
                    stage.setScene(loginScene);
                    stage.setTitle("Login");
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    currentStage.close();
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    void btnOrderManagementOnAction(ActionEvent event) {
        try {
            Injector injector = Guice.createInjector(new AppModule());
            FXMLLoader loader =new FXMLLoader(this.getClass().getResource("/view/order-history-view.fxml"));
            loader.setControllerFactory(injector::getInstance);
            this.paneLoadFXML.getChildren().clear();
            this.paneLoadFXML.getChildren().add(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnProcustManagementOnAction(ActionEvent event) {
        try {
            Injector injector = Guice.createInjector(new AppModule());
            FXMLLoader loader =new FXMLLoader(this.getClass().getResource("/view/product-management-view.fxml"));
            loader.setControllerFactory(injector::getInstance);
            this.paneLoadFXML.getChildren().clear();
            this.paneLoadFXML.getChildren().add(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnEmployeeManagementOnAction(ActionEvent actionEvent) {
        try {
            Injector injector = Guice.createInjector(new AppModule());
            FXMLLoader loader =new FXMLLoader(this.getClass().getResource("/view/employee-management-view.fxml"));
            loader.setControllerFactory(injector::getInstance);
            this.paneLoadFXML.getChildren().clear();
            this.paneLoadFXML.getChildren().add(loader.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReportsOnAction(ActionEvent actionEvent) {
        try {
            Injector injector = Guice.createInjector(new AppModule());
            FXMLLoader loader =new FXMLLoader(this.getClass().getResource("/view/reports-view.fxml"));
            loader.setControllerFactory(injector::getInstance);
            this.paneLoadFXML.getChildren().clear();
            this.paneLoadFXML.getChildren().add(loader.load());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSupplierManagementOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/supplier-management-view.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            paneLoadFXML.getChildren().add(anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddNewUserOnAction(ActionEvent actionEvent) {

    }
}

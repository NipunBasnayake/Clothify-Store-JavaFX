package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.User;

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
        btnUserName.setText(currentUser.getUserName());
    }

    @FXML
    public JFXButton btnUserName;

    @FXML
    public JFXButton btnCreateUserAccount;

    @FXML
    public Label lbl;

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
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/dahboard-view.fxml"));
            paneLoadFXML.getChildren().clear();
            DashboardViewController dashboardViewController = new DashboardViewController();
            dashboardViewController.setCurrentUser(currentUser);
            paneLoadFXML.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCustomerManagementOnAction(ActionEvent event) {
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/customer-management-view.fxml"));
            paneLoadFXML.getChildren().clear();
            paneLoadFXML.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/dahboard-view.fxml"));
            paneLoadFXML.getChildren().clear();
            paneLoadFXML.getChildren().add(pane);
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
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/order-history-view.fxml"));
            paneLoadFXML.getChildren().clear();
            paneLoadFXML.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnProcustManagementOnAction(ActionEvent event) {
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/product-management-view.fxml"));
            paneLoadFXML.getChildren().clear();
            paneLoadFXML.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnEmployeeManagementOnAction(ActionEvent actionEvent) {
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/employee-management-view.fxml"));
            paneLoadFXML.getChildren().clear();
            paneLoadFXML.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReportsOnAction(ActionEvent actionEvent) {
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/reports-view.fxml"));
            paneLoadFXML.getChildren().clear();
            paneLoadFXML.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSupplierManagementOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = new FXMLLoader().load(getClass().getResource("/view/supplier-management-view.fxml"));
            paneLoadFXML.getChildren().clear();
            paneLoadFXML.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCreateUserAccountOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/create-user-account-view.fxml"))));
            stage.setTitle("Create User Account");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

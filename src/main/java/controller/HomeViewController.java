package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

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

    }

    @FXML
    void btnOrderManagementOnAction(ActionEvent event) {
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/order-management-view.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane pane = null;
        try {
            pane = new FXMLLoader().load(getClass().getResource("/view/dahboard-view.fxml"));
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
}

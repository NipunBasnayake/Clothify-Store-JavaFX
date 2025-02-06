package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerViewController {

    @FXML
    private TableColumn colEmploteeDeleteAction;

    @FXML
    private TableColumn colEmploteeEmail;

    @FXML
    private TableColumn colEmploteeName;

    @FXML
    private TableColumn colEmploteeRole;

    @FXML
    private TableColumn colEmploteeUpdateAction;

    @FXML
    private TableColumn colEmployeeId;

    @FXML
    private TableView tblCustomerDetails;

    @FXML
    private TextField txtSearchCustomer;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-customer-view.fxml"))));
            stage.setTitle("Add Product");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchCustomerOnAction(ActionEvent event) {

    }


    public void btnSaveCustomerOnAction(ActionEvent actionEvent) {
    }
}

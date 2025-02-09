package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import service.custom.impl.CustomerControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colMobileNumber.setCellValueFactory(new PropertyValueFactory<>("customerMobile"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

        populateTable();
    }

    @FXML
    private TableColumn colCustomerAddress;

    @FXML
    private TableColumn colCustomerDeleteAction;

    @FXML
    private TableColumn colCustomerId;

    @FXML
    private TableColumn colCustomerName;

    @FXML
    private TableColumn colCustomerUpdateAction;

    @FXML
    private TableColumn colMobileNumber;

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

    private void populateTable() {
        List<Customer> customers = CustomerControllerImpl.getInstance().getCustomers();
        ObservableList<Customer> observableCustomers = FXCollections.observableList(customers);
        tblCustomerDetails.setItems(observableCustomers);
    }

}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        colCustomerUpdateAction.setCellFactory(param -> new TableCell<Customer, Void>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setStyle("-fx-background-color: #495057; -fx-text-fill: white;");
                updateButton.setOnMouseEntered(e -> updateButton.setStyle("-fx-background-color: #363b3e; -fx-text-fill: white;"));
                updateButton.setOnMouseExited(e -> updateButton.setStyle("-fx-background-color: #495057; -fx-text-fill: white;"));

                updateButton.setOnAction(event -> {
                    Customer customer = getTableRow().getItem();
                    if (customer != null) {
                        openUpdateCustomerView(customer);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        colCustomerDeleteAction.setCellFactory(param -> new TableCell<Customer, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;");
                deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #4c0000; -fx-text-fill: white;"));
                deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;"));

                deleteButton.setOnAction(event -> {
                    Customer customer = getTableRow().getItem();
                    if (customer != null) {
                        deleteCustomer(customer);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        populateTable();
    }

    @FXML
    private TableColumn<Customer, String> colCustomerAddress;

    @FXML
    private TableColumn<Customer, Void> colCustomerDeleteAction;

    @FXML
    private TableColumn<Customer, Integer> colCustomerId;

    @FXML
    private TableColumn<Customer, String> colCustomerName;

    @FXML
    private TableColumn<Customer, Void> colCustomerUpdateAction;

    @FXML
    private TableColumn<Customer, String> colMobileNumber;

    @FXML
    private TableView<Customer> tblCustomerDetails;

    @FXML
    private TextField txtSearchCustomer;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-customer-view.fxml"))));
            stage.setTitle("Add Customer");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchCustomerOnAction(ActionEvent event) {
        String searchText = txtSearchCustomer.getText().trim().toLowerCase();
        if (!searchText.isEmpty()) {
            ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
            for (Customer customer : CustomerControllerImpl.getInstance().getCustomers()) {
                if (customer.getCustomerName().toLowerCase().contains(searchText) ||
                        String.valueOf(customer.getCustomerId()).contains(searchText) ||
                        customer.getCustomerMobile().contains(searchText)) {
                    filteredCustomers.add(customer);
                }
            }
            tblCustomerDetails.setItems(filteredCustomers);
        } else {
            populateTable();
        }
    }


    private void populateTable() {
        List<Customer> customers = CustomerControllerImpl.getInstance().getCustomers();
        ObservableList<Customer> observableCustomers = FXCollections.observableList(customers);
        tblCustomerDetails.setItems(observableCustomers);
    }

    private void openUpdateCustomerView(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update-customer-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            UpdateCustomerViewController controller = loader.getController();
            controller.setCustomer(customer);
            stage.setTitle("Update Customer");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCustomer(Customer customer) {
        boolean isDeleted = CustomerControllerImpl.getInstance().deleteCustomer(customer.getCustomerId());
        if (isDeleted) {
            tblCustomerDetails.getItems().remove(customer);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Customer Deleted");
            alert.setContentText("Customer has been successfully deleted.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Deletion Failed");
            alert.setContentText("Failed to delete the customer.");
            alert.showAndWait();
        }
    }
}

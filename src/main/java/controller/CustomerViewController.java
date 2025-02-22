package controller;

import javafx.application.Platform;
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
import dto.Customer;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerViewController implements Initializable {

    CustomerService customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMERS);
    List<Customer> customerList = new ArrayList<>();

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

            stage.setOnHidden(e -> Platform.runLater(() -> populateTable()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchCustomerOnAction(ActionEvent event) {
        String searchText = txtSearchCustomer.getText().trim().toLowerCase();
        if (!searchText.isEmpty()) {
            ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
            for (Customer customer : customerService.getCustomers()) {
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
        customerList.clear();
        customerList.addAll(customerService.getCustomers());
        tblCustomerDetails.setItems(FXCollections.observableList(customerList));
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

            stage.setOnHidden(e -> Platform.runLater(() -> populateTable()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCustomer(Customer customer) {

        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete Customer");
        deleteAlert.setHeaderText("Do you want to delete customer: " + customer.getCustomerName() + "?");
        deleteAlert.setContentText("Click 'Delete' to confirm, or 'Cancel' to abort.");

        Optional<ButtonType> result = deleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete the customer: " + customer.getCustomerName() + "?");
            confirmationAlert.setContentText("This action cannot be undone.");

            Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();

            if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {

                boolean isDeleted = customerService.deleteCustomer(customer.getCustomerId());

                if (isDeleted) {
                    Platform.runLater(() -> populateTable());
                }
            }
        }
    }

}

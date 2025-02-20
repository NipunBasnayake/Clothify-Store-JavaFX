package controller;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import dto.Customer;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.ServiceType;

public class UpdateCustomerViewController {

    @Inject
    CustomerService service;

    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        txtUpdateCustomerName.setText(customer.getCustomerName());
        txtUpdateCustomerAddress.setText(customer.getCustomerAddress());
        txtUpdateCustomerMobileNumber.setText(customer.getCustomerMobile());
    }

    @FXML
    private TextField txtUpdateCustomerAddress;

    @FXML
    private TextField txtUpdateCustomerMobileNumber;

    @FXML
    private TextField txtUpdateCustomerName;

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {
        boolean isUpdatedCustomer = service.updateCustomer(new Customer(
                customer.getCustomerId(),
                txtUpdateCustomerName.getText(),
                txtUpdateCustomerMobileNumber.getText(),
                txtUpdateCustomerAddress.getText()
        ));
        if (isUpdatedCustomer) {
            Stage stage = (Stage) txtUpdateCustomerAddress.getScene().getWindow();
            stage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Not Updated");
            alert.setHeaderText("Customer Not Updated");
            alert.show();
        }
    }
}

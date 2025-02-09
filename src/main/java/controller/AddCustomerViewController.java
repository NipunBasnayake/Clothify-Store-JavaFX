package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Customer;
import service.custom.impl.CustomerControllerImpl;

public class AddCustomerViewController {

    @FXML
    private TextField txtAddCustomerAddress;

    @FXML
    private TextField txtAddCustomerMobileNumber;

    @FXML
    private TextField txtAddCustomerName;

    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) {
        boolean isCustomerAdded = CustomerControllerImpl.getInstance().addCustomer(new Customer(
                1,
                txtAddCustomerName.getText(),
                txtAddCustomerMobileNumber.getText(),
                txtAddCustomerAddress.getText()
        ));
        if (isCustomerAdded) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Added");
            alert.setHeaderText("Customer Successfully Added");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Not Added");
            alert.setHeaderText("Customer Not Added");
            alert.show();
        }
    }

}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import dto.Customer;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.CustomerService;
import util.ServiceType;

public class AddCustomerViewController {

    CustomerService service = ServiceFactory.getInstance().getService(ServiceType.CUSTOMERS);

    @FXML
    private TextField txtAddCustomerAddress;

    @FXML
    private TextField txtAddCustomerMobileNumber;

    @FXML
    private TextField txtAddCustomerName;

    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) {
        boolean isCustomerAdded = service.addCustomer(new Customer(
                1,
                txtAddCustomerName.getText(),
                txtAddCustomerMobileNumber.getText(),
                txtAddCustomerAddress.getText()
        ));
        if (isCustomerAdded) {
            Stage stage = (Stage) txtAddCustomerAddress.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Not Added");
            alert.setHeaderText("Customer Not Added");
            alert.show();
        }
    }

}

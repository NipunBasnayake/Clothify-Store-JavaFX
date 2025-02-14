package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Employee;
import service.ServiceFactory;
import service.custom.EmployeeService;
import service.custom.impl.EmployeeServiceImpl;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeViewController implements Initializable {

    EmployeeService employeeService = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);

    @FXML
    private ComboBox cmbAddEmployeeRole;

    @FXML
    private TextField txtAddEmployeeEmail;

    @FXML
    private TextField txtAddEmployeeName;

    @FXML
    void btnSaveEmployeeOnAction(ActionEvent event) {
        boolean isEmployeeAdded = employeeService.addEmployee(new Employee(
                1,
                txtAddEmployeeName.getText(),
                txtAddEmployeeEmail.getText(),
                cmbAddEmployeeRole.getSelectionModel().getSelectedItem().toString()
        ));
        Alert alert;
        if (isEmployeeAdded) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Added");
            alert.setHeaderText("Employee Added");
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Employee Not Added");
            alert.setHeaderText("Employee Not Added");
        }
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbAddEmployeeRole.getItems().add("Sales Assistant");
        cmbAddEmployeeRole.getItems().add("Cashier");
    }
}

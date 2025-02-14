package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import dto.Employee;
import service.ServiceFactory;
import service.custom.EmployeeService;
import util.ServiceType;

public class UpdateEmployeeViewController {

    EmployeeService employeeService = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);

    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;

        cmbUpdateEmployeeRole.getItems().add("Sales Assistant");
        cmbUpdateEmployeeRole.getItems().add("Cashier");

        txtUpdateEmployeeName.setText(employee.getEmployeeName());
        txtUpdateEmployeeEmail.setText(employee.getEmployeeEmail());
        cmbUpdateEmployeeRole.setValue(employee.getEmployeeRole());
    }

    @FXML
    private ComboBox<String> cmbUpdateEmployeeRole;

    @FXML
    private TextField txtUpdateEmployeeEmail;

    @FXML
    private TextField txtUpdateEmployeeName;

    @FXML
    void btnSaveEmployeeOnAction(ActionEvent event) {
        if (employee != null) {
            employee.setEmployeeName(txtUpdateEmployeeName.getText());
            employee.setEmployeeEmail(txtUpdateEmployeeEmail.getText());
            employee.setEmployeeRole(cmbUpdateEmployeeRole.getValue());

            boolean isEmployeeUpdated = employeeService.updateEmployee(employee);

            Alert alert = new Alert(isEmployeeUpdated ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Update Employee");
            alert.setHeaderText(isEmployeeUpdated ? "Employee Updated Successfully" : "Employee Update Failed");
            alert.show();
        }
    }
}

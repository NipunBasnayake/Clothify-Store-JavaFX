package controller;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import dto.Employee;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.EmployeeService;
import util.ServiceType;

public class UpdateEmployeeViewController {

    @Inject
    EmployeeService employeeService;

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

            System.out.println(employeeService);
            boolean isEmployeeUpdated = employeeService.updateEmployee(employee);

            if (isEmployeeUpdated) {
                Stage stage = (Stage) txtUpdateEmployeeEmail.getScene().getWindow();
                stage.close();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Error");
                alert.setHeaderText("Employee Not Updated");
                alert.show();
            }
        }
    }
}

package controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import config.AppModule;
import dto.Customer;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.Employee;
import service.ServiceFactory;
import service.custom.EmployeeService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.addAll;

public class EmployeeViewController implements Initializable {

    @Inject
    EmployeeService employeeService;

    List<Employee> employeeList = new ArrayList<>();

    @FXML
    public AnchorPane paneProductManagement;
    @FXML
    private TableColumn<Employee, Integer> colEmployeeId;
    @FXML
    private TableColumn<Employee, String> colEmployeeName, colEmployeeEmail, colEmployeeRole;
    @FXML
    private TableColumn<Employee, Void> colEmployeeUpdateAction, colEmployeeDeleteAction;
    @FXML
    private TableView<Employee> tblEmployeeDetails;
    @FXML
    private TextField txtSearchEmployee;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeEmail.setCellValueFactory(new PropertyValueFactory<>("employeeEmail"));
        colEmployeeRole.setCellValueFactory(new PropertyValueFactory<>("employeeRole"));

        colEmployeeUpdateAction.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setStyle("-fx-background-color: #495057; -fx-text-fill: white;");
                updateButton.setOnMouseEntered(e -> updateButton.setStyle("-fx-background-color: #363b3e; -fx-text-fill: white;"));
                updateButton.setOnMouseExited(e -> updateButton.setStyle("-fx-background-color: #495057; -fx-text-fill: white;"));

                updateButton.setOnAction(event -> {
                    Employee employee = getTableRow() != null ? getTableRow().getItem() : null;
                    if (employee != null) {
                        updateEmployee(employee);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : updateButton);
            }
        });

        colEmployeeDeleteAction.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;");
                deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #4c0000; -fx-text-fill: white;"));
                deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;"));

                deleteButton.setOnAction(event -> {
                    Employee employee = getTableRow() != null ? getTableRow().getItem() : null;
                    if (employee != null) {
                        deleteEmployee(employee);
                    }
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        populateTable();
    }

    @FXML
    void btnSearchEmployeeOnAction(ActionEvent event) {
        if (txtSearchEmployee == null || tblEmployeeDetails == null) {
            return;
        }

        String searchText = txtSearchEmployee.getText();
        if (searchText == null) searchText = "";
        List<Employee> employees = employeeService.getEmployees();
        ObservableList<Employee> filteredList = FXCollections.observableArrayList();

        for (Employee emp : employees) {
            if (emp != null && emp.getEmployeeName() != null && emp.getEmployeeEmail() != null &&
                    (emp.getEmployeeName().toLowerCase().contains(searchText.toLowerCase()) ||
                            emp.getEmployeeEmail().toLowerCase().contains(searchText.toLowerCase()))) {
                filteredList.add(emp);
            }
        }
        tblEmployeeDetails.setItems(filteredList);
    }

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Injector injector = Guice.createInjector(new AppModule());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/add-employee-view.fxml"));

            fxmlLoader.setControllerFactory(injector::getInstance);
            stage.setScene(new Scene(fxmlLoader.load()));

            stage.setTitle("Add Employee");
            stage.setResizable(false);
            stage.show();

            stage.setOnHidden(e -> Platform.runLater(() -> populateTable()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateEmployee(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update-employee-view.fxml"));
            Stage stage = new Stage();
            Injector injector = Guice.createInjector(new AppModule());
            loader.setControllerFactory(injector::getInstance);
            stage.setScene(new Scene(loader.load()));

            UpdateEmployeeViewController controller = loader.getController();
            controller.setEmployee(employee);

            stage.setTitle("Update Customer");
            stage.setResizable(false);
            stage.show();

            stage.setOnHidden(e -> Platform.runLater(() -> populateTable()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEmployee(Employee employee) {
        if (employee == null) return;

        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete Employee");
        deleteAlert.setHeaderText("Do you want to delete employee: " + employee.getEmployeeName() + "?");
        deleteAlert.setContentText("Click 'Delete' to confirm, or 'Cancel' to abort.");

        Optional<ButtonType> result = deleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete the employee: " + employee.getEmployeeName() + "?");
            confirmationAlert.setContentText("This action cannot be undone.");

            Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();

            if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                boolean isEmployeeDeleted = employeeService.deleteEmployee(employee.getEmployeeId());

                if (isEmployeeDeleted) {
                    Platform.runLater(() -> populateTable());
                }
            }
        }
    }

    private void populateTable() {
        employeeList.clear();
        employeeList.addAll(employeeService.getEmployees());
        tblEmployeeDetails.setItems(FXCollections.observableList(employeeList));
    }
}

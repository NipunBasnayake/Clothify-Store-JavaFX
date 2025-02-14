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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dto.Employee;
import service.ServiceFactory;
import service.custom.EmployeeService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeViewController implements Initializable {

    EmployeeService employeeService = ServiceFactory.getInstance().getService(ServiceType.EMPLOYEE);

    private static final Logger LOGGER = Logger.getLogger(EmployeeViewController.class.getName());
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
                        openUpdateEmployeeView(employee);
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
    void btnAddEmployeeOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add-employee-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add Employee");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load add-employee-view.fxml", e);
        }
    }

    @FXML
    void btnSearchEmployeeOnAction(ActionEvent event) {
        if (txtSearchEmployee == null || tblEmployeeDetails == null) {
            LOGGER.log(Level.WARNING, "Search field or table is null.");
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

    private void populateTable() {
        List<Employee> employees = employeeService.getEmployees();
        tblEmployeeDetails.setItems(FXCollections.observableList(employees));
    }

    private void openUpdateEmployeeView(Employee employee) {
        if (employee == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update-employee-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Update Employee");
            stage.setResizable(false);
            UpdateEmployeeViewController controller = loader.getController();

            if (controller != null) {
                controller.setEmployee(employee);
            } else {
                LOGGER.log(Level.SEVERE, "Failed to retrieve controller from update-employee-view.fxml");
            }

            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to open update employee view", e);
        }
    }

    private void deleteEmployee(Employee employee) {
        if (employee == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this employee?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isEmployeeDeleted = employeeService.deleteEmployee(employee.getEmployeeId());
            if (isEmployeeDeleted) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Delete Confirmation");
                alert1.setHeaderText("Delete Confirmation");
                alert1.show();
                populateTable();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Delete Error");
                alert1.setHeaderText("Delete Error");
                alert1.show();
            }
        }
    }

}

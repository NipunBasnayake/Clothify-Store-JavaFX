package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeViewController{

    @FXML
    private TableColumn colEmploteeDeleteAction;

    @FXML
    private TableColumn colEmploteeEmail;

    @FXML
    private TableColumn colEmploteeName;

    @FXML
    private TableColumn colEmploteeRole;

    @FXML
    private TableColumn colEmploteeUpdateAction;

    @FXML
    private TableColumn colEmployeeId;

    @FXML
    private AnchorPane paneProductManagement;

    @FXML
    private TableView tblEmployeeDetails;

    @FXML
    private TextField txtSearchEmployee;

    @FXML
    private ComboBox cmbAddEmployeeRole;

    @FXML
    private TextField txtAddEmployeeEmail;

    @FXML
    private TextField txtAddEmployeeName;

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-employee-view.fxml"))));
            stage.setTitle("Add Employee");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchEmployeeOnAction(ActionEvent event) {

    }


    public void btnSaveEmployeeOnAction(ActionEvent actionEvent) {

    }
}

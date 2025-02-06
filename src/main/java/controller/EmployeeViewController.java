package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    }

    @FXML
    void btnSearchEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveProductOnAction(ActionEvent event) {

    }

}

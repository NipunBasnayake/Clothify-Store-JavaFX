package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Supplier;
import service.custom.impl.SupplierControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplierCompany.setCellValueFactory(new PropertyValueFactory<>("supplierCompany"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        colSypplyItem.setCellValueFactory(new PropertyValueFactory<>("supplyItem"));

        populateTable();
    }

    @FXML
    private TableColumn<?, ?> colSupplierCompany;

    @FXML
    private TableColumn<?, ?> colSupplierDeleteAction;

    @FXML
    private TableColumn<?, ?> colSupplierEmail;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colSupplierUpdateAction;

    @FXML
    private TableColumn<?, ?> colSypplyItem;

    @FXML
    private TableView<?> tblSupplier;

    @FXML
    private TextField txtSearchSupplier;

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-Supplier-view.fxml"))));
            stage.setTitle("Add Supplier");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchSupplierOnAction(ActionEvent event) {

    }

    private void populateTable() {
        List<Supplier> supplierList = SupplierControllerImpl.getInstance().getSuppliers();
        System.out.println(supplierList.toString());
        if (supplierList == null) {
            supplierList = FXCollections.observableArrayList();
        }
        ObservableList supplierObservableList = FXCollections.observableList(supplierList);
        tblSupplier.setItems(supplierObservableList);
    }

}

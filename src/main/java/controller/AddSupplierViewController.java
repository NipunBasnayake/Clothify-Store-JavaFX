package controller;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import dto.Supplier;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.SupplierService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierViewController implements Initializable {

    @Inject
    SupplierService supplierService;

    @FXML
    private ComboBox cmbSupplyItem;

    @FXML
    private TextField txtSupplierCompany;

    @FXML
    private TextField txtSupplierEmail;

    @FXML
    private TextField txtSupplierName;

    @FXML
    void btnSaveSupplierOnAction(ActionEvent event) {

        boolean isSupplierAdded = supplierService.addSupplier(new Supplier(
                1,
                txtSupplierName.getText(),
                txtSupplierCompany.getText(),
                txtSupplierEmail.getText(),
                cmbSupplyItem.getSelectionModel().getSelectedItem().toString()
        ));
        Alert alert;
        if (isSupplierAdded) {
            Stage stage = (Stage) txtSupplierCompany.getScene().getWindow();
            stage.close();
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Supplier Not Added");
            alert.setHeaderText("Supplier Not Added");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbSupplyItem.getItems().add("Gents");
        cmbSupplyItem.getItems().add("Ladies");
        cmbSupplyItem.getItems().add("Kids");
        cmbSupplyItem.getItems().add("Accessories");
        cmbSupplyItem.getItems().add("Footwear");
    }
}

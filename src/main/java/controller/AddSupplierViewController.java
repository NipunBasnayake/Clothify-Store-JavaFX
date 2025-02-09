package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Supplier;
import service.custom.impl.SupplierControllerImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierViewController implements Initializable {

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

        boolean isSupplierAdded = SupplierControllerImpl.getInstance().addSupplier(new Supplier(
                1,
                txtSupplierName.getText(),
                txtSupplierCompany.getText(),
                txtSupplierEmail.getText(),
                cmbSupplyItem.getSelectionModel().getSelectedItem().toString()
        ));
        Alert alert;
        if (isSupplierAdded) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Supplier Added");
            alert.setHeaderText("Supplier Added");
        }else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Supplier Not Added");
            alert.setHeaderText("Supplier Not Added");
        }
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbSupplyItem.getItems().add("Shirts");
        cmbSupplyItem.getItems().add("Sarees");
        cmbSupplyItem.getItems().add("Jeans");
    }
}

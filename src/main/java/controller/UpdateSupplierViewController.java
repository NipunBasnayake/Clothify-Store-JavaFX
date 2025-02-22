package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dto.Supplier;
import service.ServiceFactory;
import service.custom.SupplierService;
import util.ServiceType;

public class UpdateSupplierViewController {

    SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);

    private Supplier supplier;

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;

        cmbSupplyItem.getItems().add("Gents");
        cmbSupplyItem.getItems().add("Ladies");
        cmbSupplyItem.getItems().add("Kids");
        cmbSupplyItem.getItems().add("Accessories");
        cmbSupplyItem.getItems().add("Footwear");

        txtSupplierName.setText(supplier.getSupplierName());
        txtSupplierEmail.setText(supplier.getSupplierEmail());
        txtSupplierCompany.setText(supplier.getSupplierCompany());
        cmbSupplyItem.setValue(supplier.getSupplyItem());
    }

    @FXML
    private ComboBox<String> cmbSupplyItem;

    @FXML
    private TextField txtSupplierCompany;

    @FXML
    private TextField txtSupplierEmail;

    @FXML
    private TextField txtSupplierName;

    @FXML
    void btnSaveSupplierOnAction(ActionEvent event) {
        if (supplier == null) {
            showAlert(Alert.AlertType.ERROR, "Update Supplier", "No supplier selected.");
            return;
        }

        supplier.setSupplierName(txtSupplierName.getText());
        supplier.setSupplierEmail(txtSupplierEmail.getText());
        supplier.setSupplierCompany(txtSupplierCompany.getText());
        supplier.setSupplyItem(cmbSupplyItem.getValue());

        boolean isSupplierUpdated = supplierService.updateSupplier(supplier);

        if (isSupplierUpdated) {
            Stage stage = (Stage) txtSupplierCompany.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText("Supplier Updated Error");
            alert.show();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}

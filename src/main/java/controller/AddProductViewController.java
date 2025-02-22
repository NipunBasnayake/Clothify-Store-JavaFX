package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dto.Product;
import dto.Supplier;
import service.ServiceFactory;
import service.custom.ProductService;
import service.custom.SupplierService;
import util.ServiceType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductViewController implements Initializable {

    ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
    SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProductCategoryComboBox();
        loadProductSizeComboBox();
        loadProductSupplierComboBox();
    }

    @FXML
    private ComboBox cmbAddProcutSupplierId;

    @FXML
    private ComboBox cmbAddProductCategory;

    @FXML
    private ComboBox cmbAddProductSize;

    @FXML
    private TextField txtAddProductImagePath;

    @FXML
    private TextField txtAddProductName;

    @FXML
    private TextField txtAddProductPrice;

    @FXML
    private TextField txtAddProductQuantityOnHand;

    @FXML
    void btnAddProductImageOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Product Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File initialDirectory = new File("src\\main\\resources\\images\\products");
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            txtAddProductImagePath.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-Supplier-view.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Add Supplier");
        stage.show();
    }

    @FXML
    void btnSaveProductOnAction(ActionEvent event) {
        if (txtAddProductImagePath.getText().trim().isEmpty() ||
                txtAddProductName.getText().trim().isEmpty() ||
                txtAddProductPrice.getText().trim().isEmpty() ||
                txtAddProductQuantityOnHand.getText().trim().isEmpty() ||
                cmbAddProductCategory.getValue() == null ||
                cmbAddProductSize.getValue() == null ||
                cmbAddProcutSupplierId.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fields Can't be Empty");
            alert.show();
            return;
        }
        boolean isProductAdded = service.addProduct(new Product(
                1,
                txtAddProductName.getText(),
                cmbAddProductCategory.getSelectionModel().getSelectedItem().toString(),
                cmbAddProductSize.getSelectionModel().getSelectedItem().toString(),
                Double.parseDouble(txtAddProductPrice.getText()),
                Integer.parseInt(txtAddProductQuantityOnHand.getText()),
                txtAddProductImagePath.getText(),
                Integer.parseInt(cmbAddProcutSupplierId.getSelectionModel().getSelectedItem().toString().split(" - ")[0])
        ));

        if (isProductAdded) {
            Stage stage = (Stage) txtAddProductImagePath.getScene().getWindow();
            stage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Adding Failed");
            alert.setHeaderText("Product Adding Failed");
            alert.show();
        }
    }

    private void loadProductSupplierComboBox() {
        List<Supplier> supplierList = supplierService.getSuppliers();
        ObservableList<String> supplierObservableList = FXCollections.observableArrayList();
        for (Supplier supplier : supplierList) {
            supplierObservableList.add(supplier.getSupplierId() + " - " + supplier.getSupplierName() + " - " + supplier.getSupplierCompany());
        }
        cmbAddProcutSupplierId.setItems(supplierObservableList);
    }

    private void loadProductCategoryComboBox() {
        List<String> productCategories = List.of("Gents", "Ladies", "Kids", "Accessories", "Footwear", "Other");
        cmbAddProductCategory.setItems(FXCollections.observableArrayList(productCategories));
    }

    private void loadProductSizeComboBox() {
        List<String> productSizes = List.of("XXS", "XS", "Small", "Medium", "Large", "XL", "XXL", "XXXL");
        cmbAddProductSize.setItems(FXCollections.observableArrayList(productSizes));
    }


}

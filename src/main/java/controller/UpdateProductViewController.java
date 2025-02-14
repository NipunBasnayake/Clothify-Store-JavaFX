package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import model.Supplier;
import service.ServiceFactory;
import service.custom.ProductService;
import service.custom.SupplierService;
import service.custom.impl.ProductServiceImpl;
import service.custom.impl.SupplierServiceImpl;
import util.ServiceType;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class UpdateProductViewController {
    ProductService productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
    SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        loadProductDetails();
        loadProductCategoryComboBox();
        loadProductSizeComboBox();
        loadProductSupplierComboBox();
    }

    private void loadProductDetails() {
        if (product != null) {
            txtUpdateProductName.setText(product.getProductName());
            cmbUpdateProductCategory.setValue(product.getProductCategory());
            cmbUpdateProductSize.setValue(product.getProductSize());
            txtUpdateProductPrice.setText(product.getProductPrice().toString());
            txtUpdateProductQuantityOnHand.setText(product.getProductQuantity().toString());
            txtUpdateProductImagePath.setText(product.getProductImage());
            cmbUpdateProcutSupplierId.setValue(product.getSupplierID());
        }
    }

    @FXML
    private ComboBox cmbUpdateProcutSupplierId;

    @FXML
    private ComboBox cmbUpdateProductCategory;

    @FXML
    private ComboBox cmbUpdateProductSize;

    @FXML
    private TextField txtUpdateProductImagePath;

    @FXML
    private TextField txtUpdateProductName;

    @FXML
    private TextField txtUpdateProductPrice;

    @FXML
    private TextField txtUpdateProductQuantityOnHand;

    @FXML
    void btnAddProductImageOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Product Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            txtUpdateProductImagePath.setText(selectedFile.getAbsolutePath());
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
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnUpdateProductOnAction(ActionEvent event) {
        if (txtUpdateProductImagePath.getText().trim().isEmpty() ||
                txtUpdateProductName.getText().trim().isEmpty() ||
                txtUpdateProductPrice.getText().trim().isEmpty() ||
                txtUpdateProductQuantityOnHand.getText().trim().isEmpty() ||
                cmbUpdateProductCategory.getValue() == null ||
                cmbUpdateProductSize.getValue() == null ||
                cmbUpdateProcutSupplierId.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fields Can't be Empty");
            alert.setContentText("Please Fill All Fields");
            alert.show();
            return;
        }

        try {
            int supplierID = Integer.parseInt(cmbUpdateProcutSupplierId.getSelectionModel().getSelectedItem().toString().split(" - ")[0]);

            Product updatedProduct = new Product(
                    product.getProductID(),
                    txtUpdateProductName.getText(),
                    cmbUpdateProductCategory.getSelectionModel().getSelectedItem().toString(),
                    cmbUpdateProductSize.getSelectionModel().getSelectedItem().toString(),
                    Double.parseDouble(txtUpdateProductPrice.getText()),
                    Integer.parseInt(txtUpdateProductQuantityOnHand.getText()),
                    txtUpdateProductImagePath.getText(),
                    supplierID
            );

            boolean isProductUpdated = productService.updateProduct(updatedProduct);

            Alert alert = new Alert(isProductUpdated ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle(isProductUpdated ? "Successfully Updated" : "Update Error");
            alert.setHeaderText(isProductUpdated ? "Product Updated" : "Product Not Updated");
            alert.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Error in Supplier ID");
            alert.setContentText("Please select a valid supplier.");
            alert.show();
        }
    }

    private void loadProductSupplierComboBox() {
        List<Supplier> supplierList = supplierService.getSuppliers();
        ObservableList<String> supplierObservableList = FXCollections.observableArrayList();
        for (Supplier supplier : supplierList) {
            supplierObservableList.add(supplier.getSupplierId() + " - " + supplier.getSupplierName() + " - " + supplier.getSupplierCompany());
        }
        cmbUpdateProcutSupplierId.setItems(supplierObservableList);
    }

    private void loadProductCategoryComboBox() {
        List<String> productCategories = List.of("Gents", "Ladies", "Kids", "Accessories", "Footwear", "Other");
        cmbUpdateProductCategory.setItems(FXCollections.observableArrayList(productCategories));
    }

    private void loadProductSizeComboBox() {
        List<String> productSizes = List.of("XXS", "XS", "Small", "Medium", "Large", "XL", "XXL", "XXXL");
        cmbUpdateProductSize.setItems(FXCollections.observableArrayList(productSizes));
    }


}

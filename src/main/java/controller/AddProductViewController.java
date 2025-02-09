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
import model.Product;
import model.Supplier;
import service.custom.impl.ProductControllerImpl;
import service.custom.impl.SupplierControllerImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddProductViewController implements Initializable {
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
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            txtAddProductImagePath.setText(filePath);
        } else {
            System.out.println("No file selected.");
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
        boolean isProductAdded = ProductControllerImpl.getInstance().addProduct(new Product(
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Adding Success");
            alert.setHeaderText("Product Successfully Added");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Adding Failed");
            alert.setHeaderText("Product Adding Failed");
            alert.show();
        }
    }

    private void loadProductSupplierComboBox() {
        List<Supplier> supplierList = SupplierControllerImpl.getInstance().getSuppliers();
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

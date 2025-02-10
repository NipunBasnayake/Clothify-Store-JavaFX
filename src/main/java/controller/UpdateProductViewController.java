package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import model.Product;


public class UpdateProductViewController{
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        loadProductDetails();
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

    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateProductOnAction(ActionEvent event) {

    }

}

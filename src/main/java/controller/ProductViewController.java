package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductViewController {

    @FXML
    private AnchorPane paneProductManagement;

    @FXML
    private ListView procustsListView;

    @FXML
    private TextField txtSearchProduct;

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
    void btnAddProductOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-product-view.fxml"))));
            stage.setTitle("Add Product");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchProductOnAction(ActionEvent event) {

    }

    @FXML
    void btnSortAccessoriesOnAction(ActionEvent event) {

    }

    @FXML
    void btnSortAllProductsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSortFootwareOnAction(ActionEvent event) {

    }

    @FXML
    void btnSortGentsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSortKidsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSortLadiesOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddProductImageOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveProductOnAction(ActionEvent event) {

    }

}

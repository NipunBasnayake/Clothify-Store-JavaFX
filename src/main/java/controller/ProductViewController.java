package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.custom.impl.ProductControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colProdutId.setCellValueFactory(new PropertyValueFactory("productID"));
        colProductName.setCellValueFactory(new PropertyValueFactory("productName"));
        colProductCategory.setCellValueFactory(new PropertyValueFactory("productCategory"));
        ColProductSize.setCellValueFactory(new PropertyValueFactory("productSize"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory("productPrice"));
        colProductQty.setCellValueFactory(new PropertyValueFactory("productQuantity"));
        colProductImage.setCellValueFactory(new PropertyValueFactory("productImage"));
        colProsuctSupplier.setCellValueFactory(new PropertyValueFactory("supplierID"));

        populateTable();
    }

    @FXML
    private TableColumn ColProductSize;

    @FXML
    private TableColumn colProductCategory;

    @FXML
    private TableColumn colProductImage;

    @FXML
    private TableColumn colProductName;

    @FXML
    private TableColumn colProductPrice;

    @FXML
    private TableColumn colProductQty;

    @FXML
    private TableColumn colProdutId;

    @FXML
    private TableColumn colProsuctSupplier;

    @FXML
    private AnchorPane paneProductManagement;

    @FXML
    private ListView<?> procustsListView;

    @FXML
    private TableView<?> tblProducts;

    @FXML
    private TextField txtSearchProduct;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-product-view.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Add Product");
        stage.show();
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

    private void populateTable() {
        ProductControllerImpl controller = new ProductControllerImpl();
        ObservableList products = FXCollections.observableArrayList();
        products.addAll(controller.getProducts());
        tblProducts.setItems(products);
    }

}

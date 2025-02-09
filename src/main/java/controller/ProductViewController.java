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
import model.Product;
import service.custom.impl.ProductControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {
    List<Product> productList;
    List<Product> sortedList = new ArrayList<>();

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

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
        if (!txtSearchProduct.getText().isEmpty()) {
            sortedList.clear();
            for (Product product : productList) {
                if (txtSearchProduct.getText().toLowerCase().equals(product.getProductName().toLowerCase())) {
                    sortedList.add(product);
                }
            }
        }
        ObservableList sorterObserverbleList = FXCollections.observableArrayList(sortedList);
        tblProducts.setItems(sorterObserverbleList);
    }

    @FXML
    void btnSortAccessoriesOnAction(ActionEvent event) {
        sortTable("Accessories");
    }

    @FXML
    void btnSortAllProductsOnAction(ActionEvent event) {
        populateTable();
    }

    @FXML
    void btnSortFootwareOnAction(ActionEvent event) {
        sortTable("Footware");
    }

    @FXML
    void btnSortGentsOnAction(ActionEvent event) {
        sortTable("Gents");
    }

    @FXML
    void btnSortKidsOnAction(ActionEvent event) {
        sortTable("Kids");
    }

    @FXML
    void btnSortLadiesOnAction(ActionEvent event) {
        sortTable("Ladies");
    }

    private void sortTable(String category) {
        sortedList.clear();
        for (Product product : productList) {
            if (product.getProductCategory().equals(category)) {
                sortedList.add(product);
            }
        }
        ObservableList sorterObserverbleList = FXCollections.observableArrayList(sortedList);
        tblProducts.setItems(sorterObserverbleList);
    }

    private void populateTable() {
        productList= ProductControllerImpl.getInstance().getProducts();
        ObservableList products = FXCollections.observableArrayList(productList);
        tblProducts.setItems(products);
    }

}

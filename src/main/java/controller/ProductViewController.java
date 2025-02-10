package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lombok.Setter;
import model.Product;
import service.custom.impl.ProductControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Setter
public class ProductViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProductPanes();
    }

    private List<Product> productList;
    private List<Product> sortedList = new ArrayList<>();

    private Product product;


    @FXML
    private FlowPane flowPaneProductsManagement;

    @FXML
    private AnchorPane paneProductManagement;

    @FXML
    private TextField txtSearchProduct;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-product-view.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add Product");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchProductOnAction(ActionEvent event) {
        String searchText = txtSearchProduct.getText().trim().toLowerCase();
        if (!searchText.isEmpty()) {
            sortedList.clear();
            for (Product product : productList) {
                if (product.getProductName().toLowerCase().contains(searchText)) {
                    sortedList.add(product);
                }
            }
        }
        ObservableList<Product> sortedObservableList = FXCollections.observableArrayList(sortedList);
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

    private void sortTable(String category) {
        sortedList.clear();
        for (Product product : productList) {
            if (product.getProductCategory().equals(category)) {
                sortedList.add(product);
            }
        }
        ObservableList<Product> sortedObservableList = FXCollections.observableArrayList(sortedList);
    }

    private void loadProductPanes() {
        flowPaneProductsManagement.getChildren().clear();
        flowPaneProductsManagement.setHgap(15);
        flowPaneProductsManagement.setVgap(15);
        flowPaneProductsManagement.setPrefWrapLength(950);

        productList = ProductControllerImpl.getInstance().getProducts();

        for (Product product : productList) {
            VBox productCard = createProductCard(product);
            flowPaneProductsManagement.getChildren().add(productCard);
        }
    }

    private VBox createProductCard(Product product) {
        VBox productCard = new VBox(7);
        productCard.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-width: 2; -fx-border-color: #ccc; -fx-background-radius: 10; -fx-border-radius: 10");
        productCard.setPrefWidth(210);
        productCard.setPrefHeight(320);

        ImageView productImage = new ImageView(new Image("file:" + product.getProductImage()));
        productImage.setFitWidth(185);
        productImage.setFitHeight(130);

        Rectangle clip = new Rectangle(185, 130);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        productImage.setClip(clip);

        Label lblProductId = new Label("ID: " + product.getProductID());
        Label lblProductName = new Label(product.getProductName());
        Label lblCategory = new Label("Category: " + product.getProductCategory());
        Label lblPrice = new Label("Price: Rs. " + product.getProductPrice());
        Label lblQuantity = new Label("Stock: " + product.getProductQuantity());

        Button btnUpdate = new Button("Update");
        btnUpdate.setOnAction(e -> openUpdateProductView(product));

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> deleteProduct(product));

        HBox buttonBox = new HBox(5, btnUpdate, btnDelete);
        productCard.getChildren().addAll(productImage, lblProductId, lblProductName, lblCategory, lblPrice, lblQuantity, buttonBox);

        return productCard;
    }

    private void openUpdateProductView(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update-product-view.fxml"));
            Parent root = loader.load();

            UpdateProductViewController controller = loader.getController();
            controller.setProduct(product);

            Stage stage = new Stage();
            stage.setTitle("Update Product");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(Product product) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + product.getProductName() + "?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = ProductControllerImpl.getInstance().deleteProduct(String.valueOf(product.getProductID()));
            if (isDeleted) {
                loadProductPanes();
            }
        }
    }

}

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
        VBox productCard = new VBox(5);
        productCard.setStyle("-fx-padding: 12; -fx-background-color: #ffffff; "
                + "-fx-border-width: 2; -fx-border-color: #ccc; "
                + "-fx-background-radius: 10; -fx-border-radius: 10;");
        productCard.setPrefWidth(177);
        productCard.setPrefHeight(300);
        productCard.setAlignment(Pos.CENTER);

        ImageView productImage = new ImageView(new Image("file:" + product.getProductImage()));
        productImage.setFitWidth(177);
        productImage.setFitHeight(130);

        Rectangle clip = new Rectangle(177, 130);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        productImage.setClip(clip);

        Label lblProductId = new Label("ID: " + product.getProductID());
        lblProductId.setStyle("-fx-font-size: 12; -fx-text-fill: #666;");

        Label lblProductName = new Label(product.getProductName());
        lblProductName.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        Label lblCategory = new Label("Category: " + product.getProductCategory());
        Label lblQuantity = new Label("Stock: " + product.getProductQuantity());

        Label lblPrice = new Label("LKR " + product.getProductPrice());
        lblPrice.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #333;");

        VBox textContainer = new VBox(3, lblProductId, lblProductName, lblCategory, lblQuantity);
        textContainer.setAlignment(Pos.CENTER);

        Button btnUpdate = new Button("Update");
        btnUpdate.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnUpdate.setOnAction(e -> updateProduct(product));

        Button btnDelete = new Button("Delete");
        btnDelete.setStyle("-fx-background-color: #E53935; -fx-text-fill: white;");
        btnDelete.setOnAction(e -> deleteProduct(product));

        HBox buttonBox = new HBox(10, btnUpdate, btnDelete);
        buttonBox.setAlignment(Pos.CENTER);

        productCard.getChildren().addAll(productImage, textContainer, lblPrice, buttonBox);

        return productCard;
    }

    private void updateProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update-product-view.fxml"));
            Parent root = loader.load();

            UpdateProductViewController controller = loader.getController();
            controller.setProduct(product);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Product");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteProduct(Product product) {
        System.out.println("Deleting Product: " + product.getProductName());
    }


}

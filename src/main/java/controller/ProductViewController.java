package controller;

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
import service.ServiceFactory;
import service.custom.ProductService;
import service.custom.impl.ProductServiceImpl;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Setter
public class ProductViewController implements Initializable {
    ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);

    private List<Product> productList;

    @FXML
    private FlowPane flowPaneProductsManagement;

    @FXML
    private AnchorPane paneProductManagement;

    @FXML
    private TextField txtSearchProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productList = service.getProducts();
        loadProductPanes(productList);
    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-product-view.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add Product");
            stage.show();
            stage.setOnHidden(e -> loadProductPanes(service.getProducts()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchProductOnAction(ActionEvent event) {
        String searchText = txtSearchProduct.getText().trim().toLowerCase();
        List<Product> filteredList = new ArrayList<>();

        if (!searchText.isEmpty()) {
            for (Product product : productList) {
                if (product.getProductName().toLowerCase().contains(searchText)) {
                    filteredList.add(product);
                }
            }
        } else {
            filteredList = productList;
        }
        loadProductPanes(filteredList);
    }

    @FXML
    void btnSortAllProductsOnAction(ActionEvent event) {
        loadProductPanes(productList);
    }

    @FXML
    void btnSortGentsOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Gents"));
    }

    @FXML
    void btnSortLadiesOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Ladies"));
    }

    @FXML
    void btnSortKidsOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Kids"));
    }

    @FXML
    void btnSortAccessoriesOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Accessories"));
    }

    @FXML
    void btnSortFootwareOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Footwear"));
    }

    private List<Product> sortProductsByCategory(String category) {
        List<Product> sortedList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getProductCategory().equals(category)) {
                sortedList.add(product);
            }
        }
        return sortedList;
    }

    private void loadProductPanes(List<Product> products) {
        flowPaneProductsManagement.getChildren().clear();
        flowPaneProductsManagement.setHgap(15);
        flowPaneProductsManagement.setVgap(15);
        flowPaneProductsManagement.setPrefWrapLength(950);

        for (Product product : products) {
            VBox productCard = createProductCard(product);
            flowPaneProductsManagement.getChildren().add(productCard);
        }
    }

    private VBox createProductCard(Product product) {
        VBox productCard = new VBox(5);
        productCard.setStyle("-fx-padding: 12; -fx-background-color: #ffffff; "
                + "-fx-border-width: 2; -fx-border-color: #ccc; "
                + "-fx-background-radius: 10; -fx-border-radius: 10;");
        productCard.setPrefWidth(182);
        productCard.setPrefHeight(300);
        productCard.setAlignment(Pos.CENTER);

        ImageView productImage = new ImageView(new Image("file:" + product.getProductImage()));
        productImage.setFitWidth(182);
        productImage.setFitHeight(130);

        Rectangle clip = new Rectangle(182, 130);
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
        btnUpdate.setStyle("-fx-background-color: #495057; -fx-text-fill: white;");
        btnUpdate.setOnMouseEntered(e -> btnUpdate.setStyle("-fx-background-color: #363b3e; -fx-text-fill: white;"));
        btnUpdate.setOnMouseExited(e -> btnUpdate.setStyle("-fx-background-color: #495057; -fx-text-fill: white;"));
        btnUpdate.setOnAction(e -> updateProduct(product));

        Button btnDelete = new Button("Delete");
        btnDelete.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;");
        btnDelete.setOnMouseEntered(e -> btnDelete.setStyle("-fx-background-color: #4c0000; -fx-text-fill: white;"));
        btnDelete.setOnMouseExited(e -> btnDelete.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;"));
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
            stage.setOnHidden(e -> loadProductPanes(service.getProducts()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(Product product) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Product");
        confirmationAlert.setHeaderText("Are you sure you want to delete this product?");
        confirmationAlert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isProductDeleted = service.deleteProduct(product.getProductID());

            Alert alert = new Alert(isProductDeleted ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle(isProductDeleted ? "Product Deleted" : "Delete Product Error");
            alert.setHeaderText(isProductDeleted ? "Product successfully deleted." : "Product not deleted.");
            alert.show();
            loadProductPanes(service.getProducts());
        }
    }
}

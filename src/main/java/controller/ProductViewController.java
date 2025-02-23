package controller;

import com.jfoenix.controls.JFXRadioButton;
import dto.Product;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.ProductService;
import util.ServiceType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {

    private final ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
    private List<Product> productList;

    @FXML
    private FlowPane flowPaneProductsManagement;

    @FXML
    private AnchorPane paneProductManagement;

    @FXML
    private TextField txtSearchProduct;

    @FXML
    public TableView<Product> tableProducts;

    @FXML
    public AnchorPane paneListView;

    @FXML
    public ScrollPane paneCardView;

    @FXML
    public JFXRadioButton radioListView;

    @FXML
    public JFXRadioButton radioCardView;

    @FXML
    public TableColumn<Product, String> colId;

    @FXML
    public TableColumn<Product, String> colName;

    @FXML
    public TableColumn<Product, String> colImage;

    @FXML
    public TableColumn<Product, String> colCategory;

    @FXML
    public TableColumn<Product, String> colSize;

    @FXML
    public TableColumn<Product, Double> colPrice;

    @FXML
    public TableColumn<Product, Integer> colQty;

    @FXML
    public TableColumn<Product, String> colSupplierId;

    @FXML
    public TableColumn<Product, Void> colUpdateAction;

    @FXML
    public TableColumn<Product, Void> colDeleteAction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup viewToggleGroup = new ToggleGroup();
        radioListView.setToggleGroup(viewToggleGroup);
        radioCardView.setToggleGroup(viewToggleGroup);

        colId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("productSize"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("productImage"));
        colImage.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);

                if (empty || imageUrl == null || imageUrl.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image(imageUrl);
                        imageView.setImage(image);
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(60);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        System.err.println("Failed to load image: " + imageUrl);
                        setGraphic(null);
                    }
                }
            }
        });

        colUpdateAction.setCellFactory(column -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setStyle("-fx-background-color: #495057; -fx-text-fill: white;");
                updateButton.setOnMouseEntered(e -> updateButton.setStyle("-fx-background-color: #363b3e; -fx-text-fill: white;"));
                updateButton.setOnMouseExited(e -> updateButton.setStyle("-fx-background-color: #495057; -fx-text-fill: white;"));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                    updateButton.setOnAction(event -> {
                        Product product = getTableView().getItems().get(getIndex());
                        updateProduct(product);
                    });
                }
            }
        });

        colDeleteAction.setCellFactory(column -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;");
                deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #4c0000; -fx-text-fill: white;"));
                deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #6e0000; -fx-text-fill: white;"));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);

                    deleteButton.setOnAction(event -> {
                        Product product = getTableView().getItems().get(getIndex());
                        deleteProduct(product);
                    });
                }
            }
        });

        productList = service.getProducts();
        loadProductPanes(productList);
        populateTable(productList);

        radioCardView.setSelected(true);
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

            stage.setOnHidden(e -> Platform.runLater(() -> {
                        productList.clear();
                        productList.addAll(service.getProducts());
                        loadProductPanes(productList);
                        populateTable(productList);
                    })
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(Product product) {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete Product");
        deleteAlert.setHeaderText("Do you want to delete product: " + product.getProductName() + "?");
        deleteAlert.setContentText("Click 'Ok' to confirm, or 'Cancel' to abort.");

        Optional<ButtonType> result = deleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete the product: " + product.getProductName() + "?");
            confirmationAlert.setContentText("This action cannot be undone.");

            Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();

            if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {

                boolean isProductDeleted = service.deleteProduct(product.getProductID());

                if (isProductDeleted) {
                    Platform.runLater(() -> {
                        productList.clear();
                        productList.addAll(service.getProducts());
                        loadProductPanes(productList);
                        populateTable(productList);
                    });
                }
            }
        }
    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-product-view.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add Product");
            stage.show();

            stage.setOnHidden(e -> Platform.runLater(() -> {
                        productList.clear();
                        productList.addAll(service.getProducts());
                        loadProductPanes(productList);
                        populateTable(productList);
                    })
            );
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
        populateTable(filteredList);
    }

    @FXML
    void btnSortAllProductsOnAction(ActionEvent event) {
        loadProductPanes(productList);
        populateTable(productList);
    }

    @FXML
    void btnSortGentsOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Gents"));
        populateTable(sortProductsByCategory("Gents"));
    }

    @FXML
    void btnSortLadiesOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Ladies"));
        populateTable(sortProductsByCategory("Ladies"));
    }

    @FXML
    void btnSortKidsOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Kids"));
        populateTable(sortProductsByCategory("Kids"));
    }

    @FXML
    void btnSortAccessoriesOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Accessories"));
        populateTable(sortProductsByCategory("Accessories"));
    }

    @FXML
    void btnSortFootwareOnAction(ActionEvent event) {
        loadProductPanes(sortProductsByCategory("Footwear"));
        populateTable(sortProductsByCategory("Footwear"));
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
        if (products == null) {
            return;
        }
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
        productCard.setStyle("-fx-padding: 12; -fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(97,97,97,0.2), 15, 0, 0, 0);");
        productCard.setPrefWidth(182);
        productCard.setPrefHeight(300);
        productCard.setAlignment(Pos.CENTER);

        ImageView productImage = new ImageView();
        File imageFile = new File(product.getProductImage());
        if (imageFile.exists()) {
            productImage.setImage(new Image(imageFile.toURI().toString()));
        } else {
            System.out.println("Image file not found: " + product.getProductImage());
        }

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

    private void populateTable(List<Product> products) {
        tableProducts.getItems().clear();
        tableProducts.getItems().addAll(products);
    }

    @FXML
    void radioListViewOnAction(ActionEvent actionEvent) {
        paneListView.toFront();
    }

    @FXML
    void radioCardViewOnAction(ActionEvent actionEvent) {
        paneCardView.toFront();
    }
}
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Customer;
import model.Product;
import service.custom.impl.CustomerControllerImpl;
import service.custom.impl.ProductControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {
    private List<Product> productList;
    private List<Product> cartList = new ArrayList<>();

    @FXML
    public Label txtOrderId;

    @FXML
    public Label txtTotalAmount;

    @FXML
    public FlowPane flowPaneCart;

    @FXML
    private FlowPane flowPaneProducts;

    @FXML
    private ComboBox<String> cmbSelectCustomer;

    @FXML
    private TextField txtSearchProductText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomersComboBox();
        productList = ProductControllerImpl.getInstance().getProducts();
        loadProductPanes(productList);
    }

    @FXML
    void btnPayBillOnAction(ActionEvent event) {}

    @FXML
    void btnAllProductsOnAction(ActionEvent actionEvent) {
        loadProductPanes(productList);
    }

    @FXML
    void btnGentsOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Gents"));
    }

    @FXML
    void btnLadiesOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Ladies"));
    }

    @FXML
    void btnKidsOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Kids"));
    }

    @FXML
    void btnAccessoriesOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Accessories"));
    }

    @FXML
    void btnFootwearOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Footwear"));
    }

    @FXML
    void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-customer-view.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add Customer");

            stage.setOnHidden(e -> loadCustomersComboBox());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchProductOnAction(ActionEvent actionEvent) {
        String searchText = txtSearchProductText.getText().trim().toLowerCase();
        List<Product> filteredList = new ArrayList<>();

        if (!searchText.isEmpty()) {
            for (Product product : productList) {
                if (product.getProductName().toLowerCase().contains(searchText)) {
                    filteredList.add(product);
                }
            }
            loadProductPanes(filteredList);
        } else {
            loadProductPanes(productList);
        }
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

    private void loadCustomersComboBox() {
        List<Customer> customerList = CustomerControllerImpl.getInstance().getCustomers();
        ObservableList<String> customerObservableList = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            customerObservableList.add(customer.getCustomerId() + " - " + customer.getCustomerName() + " - " + customer.getCustomerMobile());
        }
        cmbSelectCustomer.setItems(customerObservableList);
    }

    private void loadProductPanes(List<Product> products) {
        flowPaneProducts.getChildren().clear();
        flowPaneProducts.setHgap(15);
        flowPaneProducts.setVgap(15);
        flowPaneProducts.setPrefWrapLength(950);

        for (Product product : products) {
            VBox productCard = createProductCard(product);
            flowPaneProducts.getChildren().add(productCard);
        }
    }

    private VBox createProductCard(Product product) {
        VBox productCard = new VBox(5);
        productCard.setStyle("-fx-padding: 12; -fx-background-color: #ffffff; -fx-border-width: 2; -fx-border-color: #ccc; -fx-background-radius: 10; -fx-border-radius: 10;");
        productCard.setPrefWidth(177);
        productCard.setPrefHeight(265);
        productCard.setAlignment(Pos.CENTER);

        ImageView productImage = new ImageView(new Image("file:" + product.getProductImage()));
        productImage.setFitWidth(177);
        productImage.setFitHeight(130);

        Rectangle clip = new Rectangle(177, 130);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        productImage.setClip(clip);

        Label lblProductName = new Label(product.getProductName());
        lblProductName.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        Label lblCategory = new Label("Category: " + product.getProductCategory());
        Label lblQuantity = new Label("Stock: " + product.getProductQuantity());

        Label lblPrice = new Label("LKR " + product.getProductPrice());
        lblPrice.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #333;");

        VBox textContainer = new VBox(3, lblProductName, lblCategory, lblQuantity);
        textContainer.setAlignment(Pos.CENTER);

        setProductCardClickAction(productCard, product);

        productCard.getChildren().addAll(productImage, textContainer, lblPrice);

        return productCard;
    }

    private void setProductCardClickAction(VBox productCard, Product product) {
        productCard.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Quantity");
            dialog.setHeaderText("Enter the quantity for " + product.getProductName());
            dialog.setContentText("Quantity:");

            dialog.showAndWait().ifPresent(input -> {
                try {
                    int quantity = Integer.parseInt(input);

                    boolean found = false;
                    for (Product listProduct : cartList) {
                        if (product.getProductName().equals(listProduct.getProductName())) {
                            listProduct.setProductQuantity(listProduct.getProductQuantity() + quantity);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        cartList.add(new Product(product.getProductID(),
                                product.getProductName(),
                                product.getProductCategory(),
                                product.getProductSize(),
                                product.getProductPrice(),
                                quantity,
                                product.getProductImage(),
                                product.getSupplierID()));
                    }

                    loadCartPane();
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, "Invalid quantity! Please enter a valid number.").show();
                }
            });
        });
    }


    private void loadCartPane() {
        flowPaneCart.getChildren().clear();
        double totalAmount = 0;

        VBox cartContainer = new VBox(10);
        cartContainer.setPadding(new Insets(10, 0, 10, 0));

        for (Product product : cartList) {
            HBox cartItem = new HBox(10);
            cartItem.setStyle("-fx-padding: 10; -fx-background-color: #f8f9fa; "
                    + "-fx-border-width: 1; -fx-border-color: #ccc; "
                    + "-fx-background-radius: 5; -fx-border-radius: 5;");
            cartItem.setAlignment(Pos.CENTER_LEFT);
            cartItem.setPrefWidth(450);
            cartItem.setPadding(new Insets(5, 10, 5, 10));

            Label lblProductName = new Label(product.getProductName());
            lblProductName.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            lblProductName.setPrefWidth(130);

            Label lblPrice = new Label("LKR " + product.getProductPrice());
            lblPrice.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
            lblPrice.setPrefWidth(80);

            TextField txtQuantity = new TextField(String.valueOf(product.getProductQuantity()));
            txtQuantity.setPrefWidth(70);
            txtQuantity.setAlignment(Pos.CENTER);
            txtQuantity.setStyle("-fx-border-radius: 5; -fx-background-radius: 5;");

            Label lblTotal = new Label("LKR " + (product.getProductPrice() * product.getProductQuantity()));
            lblTotal.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            lblTotal.setPrefWidth(100);

            txtQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    int newQuantity = Integer.parseInt(newValue.trim());
                    if (newQuantity > 0) {
                        product.setProductQuantity(newQuantity);
                        lblTotal.setText("LKR " + (product.getProductPrice() * newQuantity));
                        updateTotalAmount();
                    } else {
                        txtQuantity.setText(oldValue);
                    }
                } catch (NumberFormatException e) {
                    txtQuantity.setText(oldValue);
                }
            });

            Image closeImage = new Image(getClass().getResourceAsStream("/images/closeIcon.png"));
            ImageView closeImageView = new ImageView(closeImage);
            closeImageView.setFitWidth(16);
            closeImageView.setFitHeight(16);

            Button btnRemove = new Button();
            btnRemove.setGraphic(closeImageView);
            btnRemove.setStyle("-fx-background-color: transparent; -fx-padding: 6;");
            btnRemove.setTooltip(new Tooltip("Remove Item"));
            btnRemove.setOnAction(event -> {
                cartList.remove(product);
                loadCartPane();
            });

            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            cartItem.getChildren().addAll(lblProductName, lblPrice, txtQuantity, lblTotal, spacer, btnRemove);
            cartContainer.getChildren().add(cartItem);

            totalAmount += product.getProductPrice() * product.getProductQuantity();
        }

        txtTotalAmount.setText("Total: LKR " + totalAmount);
        flowPaneCart.getChildren().setAll(cartContainer);
    }

    private void updateTotalAmount() {
        double newTotal = cartList.stream()
                .mapToDouble(p -> p.getProductPrice() * p.getProductQuantity())
                .sum();
        txtTotalAmount.setText("Total: LKR " + newTotal);
    }




}

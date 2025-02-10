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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {
    private List<Product> productList;
    private List<Product> sortedList = new ArrayList<>();

    @FXML
    private FlowPane flowPaneProducts;

    @FXML
    public TableColumn colUnitPrice, colQty, colTotal, colRemoveAction;

    @FXML
    private ComboBox<String> cmbSelectCustomer;

    @FXML
    private TextField txtSearchProductText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomersComboBox();
        loadProductPanes();
    }

    @FXML
    void btnPayBillOnAction(ActionEvent event) {
        // Implement payment logic
    }

    public void btnAllProductsOnAction(ActionEvent actionEvent) {
//        populateTable();
    }

    public void btnGentsOnAction(ActionEvent actionEvent) {
        sortTable("Gents");
    }

    public void btnLadiesOnAction(ActionEvent actionEvent) {
        sortTable("Ladies");
    }

    public void btnKidsOnAction(ActionEvent actionEvent) {
        sortTable("Kids");
    }

    public void btnAccessoriesOnAction(ActionEvent actionEvent) {
        sortTable("Accessories");
    }

    public void btnFootwearOnAction(ActionEvent actionEvent) {
        sortTable("Footwear");
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-customer-view.fxml"))));
            stage.setTitle("Add Customer");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSearchProductOnAction(ActionEvent actionEvent) {
//        String searchText = txtSearchProductText.getText().trim().toLowerCase();
//        if (!searchText.isEmpty()) {
//            sortedList.clear();
//            for (Product product : productList) {
//                if (product.getProductName().toLowerCase().contains(searchText)) {
//                    sortedList.add(product);
//                }
//            }
//            tblProducts.setItems(FXCollections.observableArrayList(sortedList));
//        }
    }

    private void sortTable(String category) {
        sortedList.clear();
        for (Product product : productList) {
            if (product.getProductCategory().equals(category)) {
                sortedList.add(product);
            }
        }
//        tblProducts.setItems(FXCollections.observableArrayList(sortedList));
    }

    private void loadCustomersComboBox() {
        List<Customer> customerList = CustomerControllerImpl.getInstance().getCustomers();
        ObservableList<String> customerObservableList = FXCollections.observableArrayList();
        for (Customer customer : customerList) {
            customerObservableList.add(customer.getCustomerId() + " - " + customer.getCustomerName() + " - " + customer.getCustomerMobile());
        }
        cmbSelectCustomer.setItems(customerObservableList);
    }

    private void loadProductPanes() {
        flowPaneProducts.getChildren().clear();
        flowPaneProducts.setHgap(15);
        flowPaneProducts.setVgap(15);
        flowPaneProducts.setPrefWrapLength(950);

        productList = ProductControllerImpl.getInstance().getProducts();

        for (Product product : productList) {
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
            System.out.println("Clicked on: " + product.getProductName());
        });
    }






}

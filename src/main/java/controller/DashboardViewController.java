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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        productCard.setStyle("-fx-padding: 10; -fx-background-color: #ffffff; -fx-border-width: 2; -fx-border-color: #ccc; -fx-background-radius: 10; -fx-border-radius: 10");
        productCard.setPrefWidth(220);
        productCard.setPrefHeight(320);

        ImageView productImage = new ImageView(new Image("file:" + product.getProductImage()));
        productImage.setFitWidth(180);
        productImage.setFitHeight(130);

        Label lblProductId = new Label("ID: " + product.getProductID());
        Label lblProductName = new Label(product.getProductName());
        Label lblCategory = new Label("Category: " + product.getProductCategory());
        Label lblPrice = new Label("Price: Rs. " + product.getProductPrice());
        Label lblQuantity = new Label("Stock: " + product.getProductQuantity());

        Button btnUpdate = new Button("Update");
        btnUpdate.setOnAction(e -> openUpdateProductView(product));

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> deleteProduct(product));

        HBox buttonBox = new HBox(10, btnUpdate, btnDelete);
        productCard.getChildren().addAll(productImage, lblProductId, lblProductName, lblCategory, lblPrice, lblQuantity, buttonBox);

        return productCard;
    }

    private void openUpdateProductView(Product product) {
        System.out.println("Opening Update Product View for: " + product.getProductName());

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Are you sure you want to delete " + product.getProductName() + "?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean isDeleted = ProductControllerImpl.getInstance().deleteProduct(String.valueOf(product.getProductID()));
            if (isDeleted) {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Delete Product");
                alert2.setHeaderText("Deleted Product");
                alert2.show();
                loadProductPanes();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Product Not Delete");
                alert2.setHeaderText("Product Not Delete");
                alert2.show();
            }
        }
    }
}

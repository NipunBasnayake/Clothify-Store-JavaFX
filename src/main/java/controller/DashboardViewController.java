package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {
    private List<Product> productList;
    private List<Product> sortedList = new ArrayList<>();

    @FXML
    private FlowPane flowPaneProducts;

    @FXML
    public TableColumn colUnitPrice;

    @FXML
    public TableColumn colQty;

    @FXML
    public TableColumn colTotal;

    @FXML
    public TableColumn colRemoveAction;

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
            Button btnDelete = new Button("Delete");
            HBox buttonBox = new HBox(10, btnUpdate, btnDelete);

            productCard.getChildren().addAll(productImage, lblProductId, lblProductName, lblCategory, lblPrice, lblQuantity, buttonBox);
            flowPaneProducts.getChildren().add(productCard);
        }
    }

}

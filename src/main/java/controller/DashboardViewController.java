package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Product;
import service.custom.impl.ProductControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {
    public TextField txtSearchText;
    List<Product> productList;
    List<Product> sortedList = new ArrayList<>();

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
    private JFXButton btnPayBill;

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
    private TableColumn colQty;

    @FXML
    private TableColumn colRemoveAction;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private AnchorPane paneDashboard;

    @FXML
    private AnchorPane panePlaceOrder;

    @FXML
    private AnchorPane paneProductLoad;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private TableView<?> tblProducts;

    @FXML
    private Label txtTotalAmount;

    @FXML
    void btnPayBillOnAction(ActionEvent event) {

    }

    public void btnAllProductsOnAction(ActionEvent actionEvent) {
        ProductControllerImpl controller = new ProductControllerImpl();
        productList = controller.getProducts();
        ObservableList products = FXCollections.observableArrayList(productList);
        tblProducts.setItems(products);
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

    public void btnFootwareOnAction(ActionEvent actionEvent) {
        sortTable("Footware");
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-customer-view.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Add Customer");
        stage.show();
    }

    public void btnSearchProductOnAction(ActionEvent actionEvent) {
        if (!txtSearchText.getText().isEmpty()) {
            sortedList.clear();
            for (Product product : productList) {
                if (txtSearchText.getText().toLowerCase().equals(product.getProductName().toLowerCase())) {
                    sortedList.add(product);
                }
            }
        }
        ObservableList sorterObserverbleList = FXCollections.observableArrayList(sortedList);
        tblProducts.setItems(sorterObserverbleList);
    }

    private void populateTable() {
        ProductControllerImpl controller = new ProductControllerImpl();
        productList = controller.getProducts();
        ObservableList products = FXCollections.observableArrayList(productList);
        tblProducts.setItems(products);
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
}

package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.custom.impl.DashboardControllerImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {
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
    void btnAddCustomerOnAction(MouseEvent event) {

    }

    @FXML
    void btnPayBillOnAction(ActionEvent event) {

    }

    private void populateTable() {
        DashboardControllerImpl controller = new DashboardControllerImpl();
        ObservableList products = FXCollections.observableArrayList();
        products.addAll(controller.getProducts());
        tblProducts.setItems(products);
    }

}

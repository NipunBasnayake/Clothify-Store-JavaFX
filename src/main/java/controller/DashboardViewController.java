package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DashboardViewController {

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

}

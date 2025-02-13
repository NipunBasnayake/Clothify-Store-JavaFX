package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import service.custom.impl.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryViewController implements Initializable {
    @FXML
    public TableColumn colOrderId;
    @FXML
    public TableColumn colOrderDate;
    @FXML
    public TableColumn colProductName;
    @FXML
    public TableColumn colUnitPrice;
    @FXML
    public TableColumn colQuantity;
    @FXML
    public TableColumn colTotalAmount;
    @FXML
    public TableColumn colPaymentType;
    @FXML
    public TableColumn colCustomerName;
    @FXML
    public TableColumn colEmployeeName;
    @FXML
    private TableView<OrderHistory> tblOrderHistory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        populateTable();
    }

    private void populateTable() {
        List<Order> orders = OrderControllerImpl.getInstance().getOrders();
        List<OrderProduct> orderProducts = OrderProductControllerImpl.getInstance().getOrderProducts();

        ObservableList<OrderHistory> orderHistoryItems = FXCollections.observableArrayList();

        for (Order order : orders) {
            Customer customer = CustomerControllerImpl.getInstance().getCustomerById(order.getCustomerId());
            Employee employee = EmployeeControllerImpl.getInstance().getEmployeeById(order.getEmployeeId());

            for (OrderProduct op : orderProducts) {
                Product product = ProductControllerImpl.getInstance().getProductById(op.getProductId());

                orderHistoryItems.add(new OrderHistory(
                        order.getOrderId(),
                        order.getOrderDate(),
                        product.getProductName(),
                        product.getProductPrice(),
                        op.getQuantity(),
                        product.getProductPrice() * op.getQuantity(),
                        order.getPaymentMethod(),
                        customer.getCustomerName(),
                        employee.getEmployeeName()
                ));
            }
        }
        tblOrderHistory.setItems(orderHistoryItems);
    }
}
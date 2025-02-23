package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.*;
import service.ServiceFactory;
import service.custom.*;
import util.ServiceType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryViewController implements Initializable {

    private final OrderService orderService = ServiceFactory.getInstance().getService(ServiceType.ORDERS);
    private final OrderDetailService orderDetailService = ServiceFactory.getInstance().getService(ServiceType.ORDERPRODUCT);
    private final ProductService productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
    private final CustomerService customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMERS);
    private final LoginSignupService loginSignupService = ServiceFactory.getInstance().getService(ServiceType.USER);

    private ObservableList<OrderHistory> orderHistoryItems = FXCollections.observableArrayList();

    @FXML
    public TableColumn<OrderHistory, Integer> colOrderId;
    @FXML
    public TableColumn<OrderHistory, String> colOrderDate;
    @FXML
    public TableColumn<OrderHistory, String> colProductName;
    @FXML
    public TableColumn<OrderHistory, Double> colUnitPrice;
    @FXML
    public TableColumn<OrderHistory, Integer> colQuantity;
    @FXML
    public TableColumn<OrderHistory, Double> colTotalAmount;
    @FXML
    public TableColumn<OrderHistory, String> colPaymentType;
    @FXML
    public TableColumn<OrderHistory, String> colCustomerName;
    @FXML
    public TableColumn<OrderHistory, String> colEmployeeName;
    @FXML
    public TextField txtSearchOrder;
    @FXML
    private TableView<OrderHistory> tblOrderHistory;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("userName"));

        populateTable();
    }

    private void populateTable() {
        try {
            List<Order> orders = orderService.getOrders();
            List<OrderDetails> orderDetails = orderDetailService.getOrderProducts();

            for (Order order : orders) {
                for (OrderDetails orderDetail : orderDetails) {
                    if (orderDetail.getOrderId() == order.getOrderId()) {
                        Customer customer = customerService.getCustomerById(order.getCustomerId());
                        Product product = productService.getProductById(orderDetail.getProductId());
                        User user = loginSignupService.getUserById(order.getUserId());

                        OrderHistory orderHistory = new OrderHistory(
                                order.getOrderId(),
                                order.getOrderDate(),
                                product != null ? product.getProductName() : "null",
                                product != null ? product.getProductPrice() : 0.0,
                                orderDetail.getQuantity(),
                                (product != null ? product.getProductPrice() * orderDetail.getQuantity() : 0.0),
                                order.getPaymentMethod() != null ? order.getPaymentMethod() : "null",
                                customer != null ? customer.getCustomerName() : "null",
                                user != null ? user.getUserName() : "null"
                        );
                        orderHistoryItems.add(orderHistory);
                    }
                }
            }

            tblOrderHistory.setItems(orderHistoryItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnSearchOrderHistory(ActionEvent actionEvent) {
        String searchText = txtSearchOrder.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            tblOrderHistory.setItems(orderHistoryItems);
            return;
        }
        ObservableList<OrderHistory> filteredList = orderHistoryItems.filtered(order -> matchesSearch(order, searchText));
        tblOrderHistory.setItems(filteredList);
    }

    private boolean matchesSearch(OrderHistory order, String searchText) {
        return String.valueOf(order.getOrderId()).contains(searchText) ||
                (order.getOrderDate() != null && order.getOrderDate().toString().toLowerCase().contains(searchText)) ||
                (order.getProductName() != null && order.getProductName().toLowerCase().contains(searchText)) ||
                (order.getCustomerName() != null && order.getCustomerName().toLowerCase().contains(searchText)) ||
                (order.getUserName() != null && order.getUserName().toLowerCase().contains(searchText)) ||
                (order.getPaymentMethod() != null && order.getPaymentMethod().toLowerCase().contains(searchText));
    }
}
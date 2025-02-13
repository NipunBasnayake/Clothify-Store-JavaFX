package service.custom;

import model.Order;

import java.util.List;

public interface OrderServices {
    int getLastOrderId();
    List<Order> getOrders();
    Order getOrder(int orderId);
}

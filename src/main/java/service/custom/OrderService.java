package service.custom;

import model.Order;
import service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {
    int getLastOrderId();
    List<Order> getOrders();
    Order getOrder(int orderId);
}

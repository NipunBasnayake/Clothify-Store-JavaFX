package dao.Custom;

import dao.SuperDao;
import model.Order;

import java.util.List;

public interface OrderDao extends SuperDao {
    int getLastOrderId();
    List<Order> getOrders();
    Order getOrder(int orderId);
}

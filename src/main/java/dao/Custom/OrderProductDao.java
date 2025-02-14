package dao.Custom;

import dao.SuperDao;
import model.OrderProduct;

import java.util.List;

public interface OrderProductDao extends SuperDao {
    List<OrderProduct> getOrderProducts();
}

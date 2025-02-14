package dao.Custom;

import dao.SuperDao;
import dto.OrderProduct;

import java.util.List;

public interface OrderProductDao extends SuperDao {
    List<OrderProduct> getOrderProducts();
}

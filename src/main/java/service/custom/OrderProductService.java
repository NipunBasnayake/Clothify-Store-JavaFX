package service.custom;

import model.OrderProduct;
import service.SuperService;

import java.util.List;

public interface OrderProductService extends SuperService {
    List<OrderProduct> getOrderProducts();
}

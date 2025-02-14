package service.custom;

import dto.OrderProduct;
import service.SuperService;

import java.util.List;

public interface OrderProductService extends SuperService {
    List<OrderProduct> getOrderProducts();
}

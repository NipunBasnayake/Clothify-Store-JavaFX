package service.custom;

import dto.OrderDetails;
import service.SuperService;

import java.util.List;

public interface OrderDetailService extends SuperService {
    List<OrderDetails> getOrderProducts();
}

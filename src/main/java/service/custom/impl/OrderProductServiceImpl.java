package service.custom.impl;

import dao.Custom.OrderDetailsDao;
import dao.DaoFactory;
import dto.OrderDetails;
import service.custom.OrderProductService;
import util.DaoType;

import java.util.List;

public class OrderProductServiceImpl implements OrderProductService {

    private static OrderProductServiceImpl orderProductControllerImpl;

    public static OrderProductServiceImpl getInstance() {
        if (orderProductControllerImpl == null) {
            orderProductControllerImpl = new OrderProductServiceImpl();
        }
        return orderProductControllerImpl;
    }

    OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDERPRODUCT);

    @Override
    public List<OrderDetails> getOrderProducts() {
//        List<OrderProductEntity> orderProductEntities = orderDetailsDao.getAll();
//        List<OrderProduct> orderProducts = new ArrayList<>();
//        orderProductEntities.forEach(orderProductEntity -> {
//            orderProducts.add(new ModelMapper().map(orderProductEntity, OrderProduct.class));
//        });
//        return orderProducts;
        return null;
    }

}

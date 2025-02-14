package service.custom.impl;

import dao.Custom.OrderProductDao;
import dao.DaoFactory;
import dto.OrderProduct;
import entity.OrderProductEntity;
import org.modelmapper.ModelMapper;
import service.custom.OrderProductService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class OrderProductServiceImpl implements OrderProductService {

    private static OrderProductServiceImpl orderProductControllerImpl;

    public static OrderProductServiceImpl getInstance() {
        if (orderProductControllerImpl == null) {
            orderProductControllerImpl = new OrderProductServiceImpl();
        }
        return orderProductControllerImpl;
    }

    OrderProductDao orderProductDao = DaoFactory.getInstance().getDao(DaoType.ORDERPRODUCT);

    @Override
    public List<OrderProduct> getOrderProducts() {
        List<OrderProductEntity> orderProductEntities = orderProductDao.getAll();
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProductEntities.forEach(orderProductEntity -> {
            orderProducts.add(new ModelMapper().map(orderProductEntity, OrderProduct.class));
        });
        return orderProducts;
    }

}

package service.custom.impl;

import dao.Custom.OrderDao;
import dao.DaoFactory;
import dto.Order;
import entity.OrderEntity;
import org.modelmapper.ModelMapper;
import service.custom.OrderService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl orderControllerImpl;

    public static OrderServiceImpl getInstance() {
        if (orderControllerImpl == null) {
            orderControllerImpl = new OrderServiceImpl();
        }
        return orderControllerImpl;
    }

    OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDERS);

    @Override
    public int getLastOrderId() {
        return orderDao.getLastOrderId();
    }

    @Override
    public List<Order> getOrders() {
        List<OrderEntity> orderEntities = orderDao.getAll();
        List<Order> orders = new ArrayList<>();
        orderEntities.forEach(orderEntity -> {
            orders.add(new ModelMapper().map(orderEntity, Order.class));
        });
        return orders;
    }

    @Override
    public Order getOrder(int orderId) {
        OrderEntity orderEntity = orderDao.search(String.valueOf(orderId));
        return new ModelMapper().map(orderEntity,Order.class);
    }
}

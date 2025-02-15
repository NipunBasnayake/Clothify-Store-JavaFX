package service.custom.impl;

import dao.Custom.OrderDao;
import dao.Custom.OrderDetailsDao;
import dao.Custom.ProductDao;
import dao.DaoFactory;
import dto.Order;
import entity.OrderEntity;
import entity.ProductEntity;
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
    OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDERPRODUCT);
    ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

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
        return new ModelMapper().map(orderEntity, Order.class);
    }

    @Override
    public boolean addOrder(Order order) {
        System.out.println(order.toString());

        List<ProductEntity> productEntities = new ArrayList<>();
        order.getOrderDetailsList().forEach(product -> {
            productEntities.add(new ModelMapper().map(product, ProductEntity.class));
        });
        OrderEntity orderEntity = new OrderEntity(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getPaymentMethod(),
                order.getEmployeeId(),
                order.getCustomerId(),
                productEntities
        );
        return orderDao.save(orderEntity);
    }





}

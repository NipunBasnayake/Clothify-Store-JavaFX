package service.custom.impl;

import dao.Custom.OrderDao;
import dao.Custom.OrderDetailsDao;
import dao.Custom.ProductDao;
import dao.DaoFactory;
import dto.Order;
import dto.OrderDetails;
import dto.Product;
import entity.OrderDetailEntity;
import entity.OrderEntity;
import entity.ProductEntity;
import org.modelmapper.ModelMapper;
import service.custom.OrderService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl orderServiceImpl;
    private final OrderDao orderDao;
    private final OrderDetailsDao orderDetailsDao;
    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    private OrderServiceImpl() {
        orderDao = DaoFactory.getInstance().getDao(DaoType.ORDERS);
        orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDERPRODUCT);
        productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
        modelMapper = new ModelMapper();
    }

    public static OrderServiceImpl getInstance() {
        if (orderServiceImpl == null) {
            orderServiceImpl = new OrderServiceImpl();
        }
        return orderServiceImpl;
    }

    @Override
    public int getLastOrderId() {
        try {
            return orderDao.getLastOrderId();
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicate failure
        }
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            List<OrderEntity> orderEntities = orderDao.getAll();
            for (OrderEntity entity : orderEntities) {
                orders.add(modelMapper.map(entity, Order.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getOrder(int orderId) {
        try {
            OrderEntity orderEntity = orderDao.search(String.valueOf(orderId));
            if (orderEntity != null) {
                return modelMapper.map(orderEntity, Order.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addOrder(Order order) {
        try {
            List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
            for(OrderDetails orderDetails : order.getOrderDetailsList()){
                orderDetailEntities.add(modelMapper.map(orderDetails, OrderDetailEntity.class));
            }

            OrderEntity orderEntity = new OrderEntity(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getTotalPrice(),
                    order.getPaymentMethod(),
                    order.getUserId(),
                    order.getCustomerId(),
                    orderDetailEntities
            );
//            System.out.println(orderEntity.toString());

            boolean isOrderSaved = orderDao.save(orderEntity);
            if (isOrderSaved) {
                System.out.println("Order Added Successfully - 3 Databases Executed");
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
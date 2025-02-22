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
            return -1;
        }
    }

    @Override
    public List<Order> getOrders() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (OrderDetailEntity entity : orderDetailsDao.getAll()) {
            orderDetailsList.add(modelMapper.map(entity, OrderDetails.class));
        }

        List<Order> orders = new ArrayList<>();
        for (OrderEntity entity : orderDao.getAll()) {
            Order order = modelMapper.map(entity, Order.class);

            List<OrderDetails> matchedDetails = new ArrayList<>();
            for (OrderDetails details : orderDetailsList) {
                if (details.getOrderId() == order.getOrderId()) {
                    matchedDetails.add(details);
                }
            }
            order.setOrderDetailsList(matchedDetails);
            orders.add(order);
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

            boolean isOrderSaved = orderDao.save(orderEntity);
            if (isOrderSaved) {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
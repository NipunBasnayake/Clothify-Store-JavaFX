package service.custom.impl;

import dao.Custom.OrderDao;
import dao.DaoFactory;
import db.DBConnection;
import model.Order;
import service.custom.OrderService;
import util.DaoType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        return orderDao.getOrders();
    }

    @Override
    public Order getOrder(int orderId) {
        return orderDao.getOrder(orderId);
    }


}

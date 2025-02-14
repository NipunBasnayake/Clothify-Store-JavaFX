package dao.Custom.impl;

import dao.Custom.OrderProductDao;
import db.DBConnection;
import dto.OrderProduct;
import entity.OrderProductEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDaoImpl implements OrderProductDao {
    private static OrderProductDaoImpl orderProductDaoImpl;

    public static OrderProductDaoImpl getInstance() {
        if (orderProductDaoImpl == null) {
            orderProductDaoImpl = new OrderProductDaoImpl();
        }
        return orderProductDaoImpl;
    }

    @Override
    public boolean save(OrderProductEntity entity) {
        return false;
    }

    @Override
    public OrderProductEntity search(Integer integer) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public boolean update(OrderProductEntity entity) {
        return false;
    }

    @Override
    public List<OrderProductEntity> getAll() {
        String query = "select OrderProductID, OrderID, ProductID, Quantity from orderproduct";
        List<OrderProductEntity> orderProducts = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                orderProducts.add(new OrderProductEntity(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4)
                ));
            }
        } catch (SQLException e) {
            return null;
        }
        return orderProducts;
    }
}

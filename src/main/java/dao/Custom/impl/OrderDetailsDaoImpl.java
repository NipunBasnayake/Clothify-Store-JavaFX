package dao.Custom.impl;

import dao.Custom.OrderDetailsDao;
import db.DBConnection;
import entity.OrderDetailEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    private static OrderDetailsDaoImpl orderDetailsDaoImpl;

    public static OrderDetailsDaoImpl getInstance() {
        if (orderDetailsDaoImpl == null) {
            orderDetailsDaoImpl = new OrderDetailsDaoImpl();
        }
        return orderDetailsDaoImpl;
    }

    public List<OrderDetailEntity> getAll() {
        String query = "SELECT orderDetailsId, orderId, productId, quantity FROM orderdetails";
        List<OrderDetailEntity> orderProducts = new ArrayList<>();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                orderProducts.add(new OrderDetailEntity(
                        resultSet.getInt("orderDetailsId"),
                        resultSet.getInt("orderId"),
                        resultSet.getInt("productId"),
                        resultSet.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching order details", e);
        }
        return orderProducts;
    }

    @Override
    public boolean save(List<OrderDetailEntity> orderProductEntities) {
        String query = "INSERT INTO orderdetails (orderId, productId, quantity) VALUES (?, ?, ?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            for (OrderDetailEntity entity : orderProductEntities) {
                statement.setInt(1, entity.getOrderId());
                statement.setInt(2, entity.getProductId());
                statement.setInt(3, entity.getQuantity());

                if (statement.executeUpdate() <= 0) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving order details", e);
        }
    }
}

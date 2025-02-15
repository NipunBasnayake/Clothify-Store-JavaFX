package dao.Custom.impl;

import dao.Custom.OrderDetailsDao;
import db.DBConnection;
import entity.OrderProductEntity;

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

    @Override
    public boolean save(List<OrderProductEntity> orderProductEntities) {
        System.out.println("Start Save OrderProduct");
        String query = "INSERT INTO orderProduct (OrderID, ProductID, Quantity) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (OrderProductEntity entity : orderProductEntities) {
                statement.setInt(1, entity.getOrderId());
                statement.setInt(2, entity.getProductId());
                statement.setInt(3, entity.getQuantity());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected <= 0) {
                    System.err.println("Failed to add order product for OrderID: " + entity.getOrderId() + ", ProductID: " + entity.getProductId());
                    return false;
                }
            }
            System.out.println("All order products added successfully");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}

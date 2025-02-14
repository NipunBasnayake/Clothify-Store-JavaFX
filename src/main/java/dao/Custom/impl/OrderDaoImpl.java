package dao.Custom.impl;

import dao.Custom.OrderDao;
import db.DBConnection;
import dto.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl orderDaoImpl;

    public static OrderDaoImpl getInstance() {
        if (orderDaoImpl == null) {
            orderDaoImpl = new OrderDaoImpl();
        }
        return orderDaoImpl;
    }

    @Override
    public int getLastOrderId() {
        String query = "SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1";
        int lastOrderId = -1;

        try (Statement stmt = DBConnection.getInstance().getConnection().createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            if (resultSet.next()) {
                lastOrderId = resultSet.getInt("OrderID");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching last OrderID", e);
        }
        return lastOrderId;
    }

    @Override
    public List<Order> getOrders() {
        String query = "SELECT OrderID, OrderDate, TotalCost, PaymentType, EmployeeID, customerId FROM orders;";
        List<Order> orders = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6)
                ));
            }
        } catch (SQLException e) {
            return null;
        }
        return orders;
    }

    @Override
    public Order getOrder(int orderId) {
        String query = "SELECT * FROM orders WHERE OrderID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Order(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6)
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

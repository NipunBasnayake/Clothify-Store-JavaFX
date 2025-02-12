package service.custom.impl;

import db.DBConnection;
import service.custom.OrderServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderControllerImpl implements OrderServices {
    private static OrderControllerImpl orderControllerImpl;

    public static OrderControllerImpl getInstance() {
        if (orderControllerImpl == null) {
            orderControllerImpl = new OrderControllerImpl();
        }
        return orderControllerImpl;
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

}

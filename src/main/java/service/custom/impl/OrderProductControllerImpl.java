package service.custom.impl;

import db.DBConnection;
import model.OrderProduct;
import service.custom.OrderProductServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductControllerImpl implements OrderProductServices {

    private static OrderProductControllerImpl orderProductControllerImpl;

    public static OrderProductControllerImpl getInstance() {
        if (orderProductControllerImpl == null) {
            orderProductControllerImpl = new OrderProductControllerImpl();
        }
        return orderProductControllerImpl;
    }

    @Override
    public List<OrderProduct> getOrderProducts() {
        String query = "select OrderProductID, OrderID, ProductID, Quantity from orderproduct";
        List<OrderProduct> orderProducts = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                orderProducts.add(new OrderProduct(
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

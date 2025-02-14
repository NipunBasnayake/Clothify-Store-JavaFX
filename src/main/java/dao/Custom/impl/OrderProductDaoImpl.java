package dao.Custom.impl;

import dao.Custom.OrderProductDao;
import db.DBConnection;
import dto.OrderProduct;

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

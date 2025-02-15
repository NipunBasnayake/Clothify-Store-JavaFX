package dao.Custom.impl;

import dao.Custom.OrderDao;
import db.DBConnection;
import entity.OrderEntity;
import entity.OrderProductEntity;
import entity.ProductEntity;

import java.sql.*;
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
        String query = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
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
    public boolean save(OrderEntity entity) {
        System.out.println(entity.toString());
        Connection connection = null;
        try {
            String query = "INSERT INTO orders (orderDate, totalAmount, paymentMethod, employeeId, customerId) VALUES (?, ?, ?, ?, ?)";
            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setDate(1, entity.getOrderDate());
                statement.setDouble(2, entity.getTotalPrice());
                statement.setString(3, entity.getPaymentMethod());
                statement.setInt(4, entity.getEmployeeId());
                statement.setInt(5, entity.getCustomerId());
                boolean isOrderSaved = statement.executeUpdate() > 0;

                List<OrderProductEntity> orderProductEntities = new ArrayList<>();
                List<ProductEntity> productEntities = new ArrayList<>();

                for (ProductEntity productEntity : entity.getProductEntities()) {
                    orderProductEntities.add(new OrderProductEntity(1, entity.getOrderId(), productEntity.getProductID(), productEntity.getProductQuantity()));
                    productEntities.add(productEntity);
                }

                if (isOrderSaved) {
                    OrderDetailsDaoImpl orderDetailsDaoImpl = new OrderDetailsDaoImpl();
                    boolean isOrderProductSaved = orderDetailsDaoImpl.save(orderProductEntities);
                    if (isOrderProductSaved) {
                        ProductDaoImpl productDaoImpl = new ProductDaoImpl();
                        productDaoImpl.updateQuantity(productEntities);
                        if (isOrderProductSaved) {
                            connection.commit();
                            return true;
                        }
                    }
                }
                connection.rollback();
                return false;
            } catch (SQLException e) {
                return false;
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public OrderEntity search(String id) {
        String query = "SELECT * FROM orders WHERE orderId = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new OrderEntity(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        List.of()
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public boolean update(OrderEntity entity) {
        return false;
    }

    @Override
    public List<OrderEntity> getAll() {
        String query = "SELECT orderId, orderDate, totalAmount, paymentMethod, employeeId, customerId FROM orders;";
        List<OrderEntity> orders = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                orders.add(new OrderEntity(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        List.of()
                ));
            }
        } catch (SQLException e) {
            return null;
        }
        return orders;
    }
}

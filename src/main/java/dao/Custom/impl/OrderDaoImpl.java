package dao.Custom.impl;

import com.google.inject.Inject;
import dao.Custom.OrderDao;
import dao.Custom.OrderDetailsDao;
import dao.Custom.ProductDao;
import db.DBConnection;
import entity.OrderEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Inject
    OrderDetailsDao orderDetailsDao;

    @Inject
    ProductDao productDao;

    @Override
    public int getLastOrderId() {
        String query = "SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1";
        try (
                Statement stmt = DBConnection.getInstance().getConnection().createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("orderId");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching last OrderID", e);
        }
        return -1;
    }

    @Override
    public boolean save(OrderEntity entity) {
        String query = "INSERT INTO orders (orderDate, totalAmount, paymentMethod, employeeId, customerId) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        try{
            PreparedStatement statement = null;

            try {
                connection = DBConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                statement = connection.prepareStatement(query);
                statement.setDate(1, entity.getOrderDate());
                statement.setDouble(2, entity.getTotalPrice());
                statement.setString(3, entity.getPaymentMethod());
                statement.setInt(4, entity.getUserId());
                statement.setObject(5, entity.getCustomerId() != 0 ? entity.getCustomerId() : null);
                boolean isOrderSaved = statement.executeUpdate() > 0;

                if (isOrderSaved) {
                    boolean isSavedToOrderDetails = orderDetailsDao.save(entity.getOrderDetailEntityList());

                    if (isSavedToOrderDetails) {
                        boolean isProductTableUpdated = productDao.updateQuantity(entity.getOrderDetailEntityList());

                        if (isProductTableUpdated) {
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
        }
        finally {
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
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToOrderEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching order", e);
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM orders WHERE orderId = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order", e);
        }
    }

    @Override
    public boolean update(OrderEntity entity) {
        String query = "UPDATE orders SET orderDate = ?, totalAmount = ?, paymentMethod = ?, employeeId = ?, customerId = ? WHERE orderId = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setDate(1, entity.getOrderDate());
            statement.setDouble(2, entity.getTotalPrice());
            statement.setString(3, entity.getPaymentMethod());
            statement.setInt(4, entity.getUserId());
            statement.setInt(5, entity.getCustomerId());
            statement.setInt(6, entity.getOrderId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order", e);
        }
    }

    @Override
    public List<OrderEntity> getAll() {
        String query = "SELECT orderId, orderDate, totalAmount, paymentMethod, employeeId, customerId FROM orders";
        List<OrderEntity> orders = new ArrayList<>();

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                orders.add(mapResultSetToOrderEntity(resultSet));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }


    private OrderEntity mapResultSetToOrderEntity(ResultSet resultSet) throws SQLException {
        return new OrderEntity(
                resultSet.getInt("orderId"),
                resultSet.getDate("orderDate"),
                resultSet.getDouble("totalAmount"),
                resultSet.getString("paymentMethod"),
                resultSet.getInt("employeeId"),
                resultSet.getInt("customerId"),
                List.of()
        );
    }
}
package dao.Custom.impl;

import dao.Custom.ProductDao;
import db.DBConnection;
import dto.OrderDetails;
import dto.Product;
import entity.OrderDetailEntity;
import entity.ProductEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private static ProductDaoImpl productDaoImpl;

    public static ProductDaoImpl getInstance() {
        if (productDaoImpl == null) {
            productDaoImpl = new ProductDaoImpl();
        }
        return productDaoImpl;
    }

    @Override
    public boolean save(ProductEntity entity) {
        String query = "INSERT INTO product (productName, category, size, price, quantity, imageUrl, supplierId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, entity.getProductName());
            statement.setString(2, entity.getProductCategory());
            statement.setString(3, entity.getProductSize());
            statement.setDouble(4, entity.getProductPrice());
            statement.setInt(5, entity.getProductQuantity());
            statement.setString(6, entity.getProductImage());
            statement.setInt(7, entity.getSupplierID());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public ProductEntity search(Integer id) {
        try {
            String query = "SELECT * FROM product WHERE productId = ?";
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ProductEntity product = new ProductEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getInt(8)
                );
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM product WHERE productId = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(ProductEntity entity) {
        String query = "UPDATE product SET productName = ?, category = ?, size = ?, price = ?, quantity = ?, imageUrl = ?, supplierId = ? WHERE productId = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, entity.getProductName());
            statement.setString(2, entity.getProductCategory());
            statement.setString(3, entity.getProductSize());
            statement.setDouble(4, entity.getProductPrice());
            statement.setInt(5, entity.getProductQuantity());
            statement.setString(6, entity.getProductImage());
            statement.setInt(7, entity.getSupplierID());
            statement.setInt(8, entity.getProductID());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<ProductEntity> getAll() {
        String query = "SELECT * FROM product";
        List<ProductEntity> products = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                ProductEntity product = new ProductEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getInt(8)
                );
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public boolean updateQuantity(List<OrderDetailEntity> entities) {
        for (OrderDetailEntity entity : entities) {
            boolean isUpdate = minusQuantity(entity);
            if (!isUpdate) {
                return false;
            }
        }
        return true;
    }

    public boolean minusQuantity(OrderDetailEntity entity) {
        String query = "UPDATE product SET quantity = quantity-? WHERE productId = ?";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, entity.getQuantity());
            statement.setInt(2, entity.getProductId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

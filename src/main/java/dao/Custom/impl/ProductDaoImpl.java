package dao.Custom.impl;

import dao.Custom.ProductDao;
import db.DBConnection;
import dto.Product;
import entity.ProductEntity;

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
        String query = "INSERT INTO product (ProductName, Category, Size, Price, Quantity, Image, SupplierID) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
        for (ProductEntity product : getAll()) {
            if (product.getProductID() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "DELETE FROM product WHERE ProductID = ?";
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
        String query = "UPDATE product SET ProductName = ?, Category = ?, Size = ?, Price = ?, Quantity = ?, Image = ?, SupplierID = ? WHERE ProductID = ?";
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
}

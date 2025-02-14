package dao.Custom.impl;

import dao.Custom.ProductDao;
import db.DBConnection;
import dto.Product;

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
    public List<Product> getProducts() {
        String query = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                Product product = new Product(
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
    public boolean addProduct(Product product) {
        String query = "INSERT INTO product (ProductName, Category, Size, Price, Quantity, Image, SupplierID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductCategory());
            statement.setString(3, product.getProductSize());
            statement.setDouble(4, product.getProductPrice());
            statement.setInt(5, product.getProductQuantity());
            statement.setString(6, product.getProductImage());
            statement.setInt(7, product.getSupplierID());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        String query = "UPDATE product SET ProductName = ?, Category = ?, Size = ?, Price = ?, Quantity = ?, Image = ?, SupplierID = ? WHERE ProductID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductCategory());
            statement.setString(3, product.getProductSize());
            statement.setDouble(4, product.getProductPrice());
            statement.setInt(5, product.getProductQuantity());
            statement.setString(6, product.getProductImage());
            statement.setInt(7, product.getSupplierID());
            statement.setInt(8, product.getProductID());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        String query = "DELETE FROM product WHERE ProductID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, productId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Product getProductById(Integer productId) {
        for (Product product : getProducts()) {
            if (product.getProductID() == productId) {
                return product;
            }
        }
        return null;
    }
}

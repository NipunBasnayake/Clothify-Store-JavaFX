package service.custom.impl;

import db.DBConnection;
import model.Product;
import service.custom.DashboardServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardControllerImpl implements DashboardServices {
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
}

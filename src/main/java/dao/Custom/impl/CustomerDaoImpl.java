package dao.Custom.impl;

import dao.Custom.CustomerDao;
import db.DBConnection;
import dto.Customer;
import entity.CustomerEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private static CustomerDaoImpl customerDaoImpl;

    public static CustomerDaoImpl getInstance() {
        if (customerDaoImpl == null) {
            customerDaoImpl = new CustomerDaoImpl();
        }
        return customerDaoImpl;
    }

    @Override
    public boolean save(CustomerEntity entity) {
        String query = "INSERT INTO customers (name, MobileNumber, Address) VALUES (?,?,?)";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, entity.getCustomerName());
            statement.setString(2, entity.getCustomerMobile());
            statement.setString(3, entity.getCustomerAddress());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public CustomerEntity search(String id) {
        String query = "SELECT id, name, MobileNumber, Address FROM customers WHERE id = ?";
        CustomerEntity customer = null;

        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customer = new CustomerEntity(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    );
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return customer;
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM customers WHERE id = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(CustomerEntity entity) {
        String query = "UPDATE customers SET name = ?, MobileNumber = ?, Address = ? WHERE id = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, entity.getCustomerName());
            statement.setString(2, entity.getCustomerMobile());
            statement.setString(3, entity.getCustomerAddress());
            statement.setInt(4, entity.getCustomerId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<CustomerEntity> getAll() {
        String query = "SELECT * FROM customers";
        List<CustomerEntity> customers = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                CustomerEntity entity = new CustomerEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                customers.add(entity);
            }
        } catch (SQLException e) {
            return null;
        }
        return customers;
    }
}

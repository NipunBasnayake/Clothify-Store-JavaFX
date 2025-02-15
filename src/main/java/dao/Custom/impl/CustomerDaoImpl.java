package dao.Custom.impl;

import dao.Custom.CustomerDao;
import db.DBConnection;
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
        String query = "INSERT INTO customer (name, mobileNumber, address) VALUES (?,?,?)";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
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
        String query = "SELECT customerId, name, mobileNumber, address FROM customer WHERE customerId = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new CustomerEntity(
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
        return null;
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM customer WHERE customerId = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(CustomerEntity entity) {
        String query = "UPDATE customer SET name = ?, mobileNumber = ?, address = ? WHERE customerId = ?";
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
        String query = "SELECT customerId, name, mobileNumber, address FROM customer";
        List<CustomerEntity> customers = new ArrayList<>();
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(new CustomerEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return customers;
    }
}

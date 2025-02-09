package service.custom.impl;

import db.DBConnection;
import model.Customer;
import service.custom.CustomerServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerControllerImpl implements CustomerServices {
    private static CustomerControllerImpl customerControllerImpl;

    public static CustomerControllerImpl getInstance() {
        if (customerControllerImpl == null) {
            customerControllerImpl = new CustomerControllerImpl();
        }
        return customerControllerImpl;
    }

    @Override
    public List<Customer> getCustomers() {
        String query = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            return null;
        }
        return customers;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO customers (name, MobileNumber, Address) VALUES (?,?,?)";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getCustomerMobile());
            statement.setString(3, customer.getCustomerAddress());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}

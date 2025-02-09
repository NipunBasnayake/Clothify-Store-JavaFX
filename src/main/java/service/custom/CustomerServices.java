package service.custom;

import model.Customer;

import java.util.List;

public interface CustomerServices {
    List<Customer> getCustomers();
    boolean addCustomer(Customer customer);
}

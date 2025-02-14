package dao.Custom;

import dao.SuperDao;
import model.Customer;

import java.util.List;

public interface CustomerDao extends SuperDao {
    List<Customer> getCustomers();
    boolean addCustomer(Customer customer);
    Customer getCustomerById(int id);
    public boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int customerId);
}

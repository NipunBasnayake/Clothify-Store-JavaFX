package service.custom.impl;

import dao.Custom.CustomerDao;
import dao.DaoFactory;
import model.Customer;
import service.custom.CustomerService;
import util.DaoType;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private static CustomerServiceImpl customerControllerImpl;

    public static CustomerServiceImpl getInstance() {
        if (customerControllerImpl == null) {
            customerControllerImpl = new CustomerServiceImpl();
        }
        return customerControllerImpl;
    }

    CustomerDao dao = DaoFactory.getInstance().getDao(DaoType.CUSTOMERS);

    @Override
    public List<Customer> getCustomers() {
        return dao.getCustomers();
    }

    @Override
    public boolean addCustomer(Customer customer) {
        return dao.addCustomer(customer);
    }

    @Override
    public Customer getCustomerById(int id) {
        return dao.getCustomerById(id);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return dao.updateCustomer(customer);
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        return dao.deleteCustomer(customerId);
    }

}

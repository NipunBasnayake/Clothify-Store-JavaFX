package service.custom.impl;

import dao.Custom.CustomerDao;
import dao.DaoFactory;
import dto.Customer;
import entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import service.custom.CustomerService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private static CustomerServiceImpl customerServiceImpl;
    private final CustomerDao dao;
    private final ModelMapper modelMapper;

    private CustomerServiceImpl() {
        dao = DaoFactory.getInstance().getDao(DaoType.CUSTOMERS);
        modelMapper = new ModelMapper();
    }

    public static CustomerServiceImpl getInstance() {
        if (customerServiceImpl == null) {
            customerServiceImpl = new CustomerServiceImpl();
        }
        return customerServiceImpl;
    }

    @Override
    public List<Customer> getCustomers() {
        List<CustomerEntity> customerEntities = dao.getAll();
        List<Customer> customers = new ArrayList<>();
        for (CustomerEntity entity : customerEntities) {
            customers.add(modelMapper.map(entity, Customer.class));
        }
        return customers;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        try {
            CustomerEntity customerEntity = modelMapper.map(customer, CustomerEntity.class);
            return dao.save(customerEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        try {
            CustomerEntity entity = dao.search(String.valueOf(id));
            if (entity != null) {
                return modelMapper.map(entity, Customer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            CustomerEntity customerEntity = modelMapper.map(customer, CustomerEntity.class);
            return dao.update(customerEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        try {
            return dao.delete(String.valueOf(customerId));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
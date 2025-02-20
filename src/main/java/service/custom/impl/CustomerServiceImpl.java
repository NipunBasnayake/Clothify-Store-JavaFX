package service.custom.impl;

import com.google.inject.Inject;
import dao.Custom.CustomerDao;
import dao.Custom.impl.CustomerDaoImpl;
import dto.Customer;
import entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import service.custom.CustomerService;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    ModelMapper modelMapper = new ModelMapper();

    CustomerDao dao = new CustomerDaoImpl();

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
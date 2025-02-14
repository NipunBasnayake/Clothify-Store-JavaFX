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
        List<CustomerEntity> customerEntities = dao.getAll();
        ArrayList<Customer> customersArray = new ArrayList<>();
        customerEntities.forEach(customerEntity -> {
            customersArray.add(new ModelMapper().map(customerEntity, Customer.class));
        });
        return customersArray;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        CustomerEntity customerEntity = new ModelMapper().map(customer, CustomerEntity.class);
        return dao.save(customerEntity);
    }

    @Override
    public Customer getCustomerById(int id) {
        CustomerEntity entity = dao.search(String.valueOf(id));
        return new ModelMapper().map(entity, Customer.class);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        CustomerEntity customerEntity = new ModelMapper().map(customer, CustomerEntity.class);
        return dao.update(customerEntity);
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        return dao.delete(String.valueOf(customerId));
    }

}

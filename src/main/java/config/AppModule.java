package config;

import com.google.inject.AbstractModule;
import dao.Custom.*;
import dao.Custom.impl.*;
import service.custom.*;
import service.custom.impl.*;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CustomerService.class).to(CustomerServiceImpl.class);
        bind(CustomerDao.class).to(CustomerDaoImpl.class);

        bind(EmployeeService.class).to(EmployeeServiceImpl.class);
        bind(EmployeeDao.class).to(EmployeeDaoImpl.class);

        bind(LoginSignupService.class).to(LoginSignUpServiceImpl.class);
        bind(LoginSignUpDao.class).to(LoginSignupDaoImpl.class);

        bind(OrderDetailsService.class).to(OrderDetailsServiceImpl.class);
        bind(OrderDetailsDao.class).to(OrderDetailsDaoImpl.class);

        bind(OrderService.class).to(OrderServiceImpl.class);
        bind(OrderDao.class).to(OrderDaoImpl.class);

        bind(ProductService.class).to(ProductServiceImpl.class);
        bind(ProductDao.class).to(ProductDaoImpl.class);

        bind(SupplierService.class).to(SupplierServiceImpl.class);
        bind(SupplierDao.class).to(SupplierDaoImpl.class);
    }

}

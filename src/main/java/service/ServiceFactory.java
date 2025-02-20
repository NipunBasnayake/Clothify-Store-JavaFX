package service;

import service.custom.impl.*;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceType serviceType) {
        switch (serviceType) {
            case CUSTOMERS: return (T) new CustomerServiceImpl();
            case EMPLOYEE: return (T) new EmployeeServiceImpl();
            case ORDERPRODUCT: return (T) new OrderDetailsServiceImpl();
            case ORDERS: return (T) new OrderServiceImpl();
            case PRODUCT: return (T) new ProductServiceImpl();
            case SUPPLIER: return (T) new SupplierServiceImpl();
            case USER: return (T) new LoginSignUpServiceImpl();
            default: return null;
        }
    }



}

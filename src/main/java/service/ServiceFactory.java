package service;

import service.custom.impl.*;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    private ServiceFactory() {}

    public <T extends SuperService> T getService(ServiceType serviceType) {
        switch (serviceType) {
            case CUSTOMERS: return (T) CustomerServiceImpl.getInstance();
            case EMPLOYEE: return (T) EmployeeServiceImpl.getInstance();
            case ORDERPRODUCT: return (T) OrderDetailsServiceImpl.getInstance();
            case ORDERS: return (T) OrderServiceImpl.getInstance();
            case PRODUCT: return (T) ProductServiceImpl.getInstance();
            case SUPPLIER: return (T) SupplierServiceImpl.getInstance();
            case USER: return (T) LoginSignUpServiceImpl.getInstance();
            default: return null;
        }
    }



}

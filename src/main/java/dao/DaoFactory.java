package dao;

import dao.Custom.impl.*;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    protected DaoFactory() {}

    public <T extends SuperDao> T getDao(DaoType daoType) {
        switch (daoType) {
            case CUSTOMERS: return (T) new CustomerDaoImpl();
            case EMPLOYEE: return (T) new EmployeeDaoImpl();
            case ORDERPRODUCT: return (T) new OrderDetailsDaoImpl();
            case ORDERS: return (T) new OrderDaoImpl();
            case PRODUCT: return (T) new ProductDaoImpl();
            case SUPPLIER: return (T) new SupplierDaoImpl();
            case USER: return (T) new LoginSignupDaoImpl();
            default: return null;
        }
    }
}

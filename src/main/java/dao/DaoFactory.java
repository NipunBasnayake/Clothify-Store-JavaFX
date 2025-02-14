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
            case CUSTOMERS: return (T) CustomerDaoImpl.getInstance();
            case EMPLOYEE: return (T) EmployeeDaoImpl.getInstance();
            case ORDERPRODUCT: return (T) OrderProductDaoImpl.getInstance();
            case ORDERS: return (T) OrderDaoImpl.getInstance();
            case PRODUCT: return (T) ProductDaoImpl.getInstance();
            case SUPPLIER: return (T) SupplierDaoImpl.getInstance();
            case USER: return (T) LoginSignupDaoImpl.getInstance();
            default: return null;
        }
    }
}

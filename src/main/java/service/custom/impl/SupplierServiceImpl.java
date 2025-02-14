package service.custom.impl;

import dao.Custom.SupplierDao;
import dao.DaoFactory;
import db.DBConnection;
import model.Supplier;
import service.custom.SupplierService;
import util.DaoType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    private static SupplierServiceImpl supplierController;

    public static SupplierServiceImpl getInstance() {
        if (supplierController == null) {
            supplierController = new SupplierServiceImpl();
        }
        return supplierController;
    }

    SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    @Override
    public List<Supplier> getSuppliers() {
        return supplierDao.getSuppliers();
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        return supplierDao.addSupplier(supplier);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        return supplierDao.updateSupplier(supplier);
    }

    @Override
    public boolean deleteSupplier(int supplierId) {
        return supplierDao.deleteSupplier(supplierId);
    }

}

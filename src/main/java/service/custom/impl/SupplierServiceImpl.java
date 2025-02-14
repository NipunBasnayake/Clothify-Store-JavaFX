package service.custom.impl;

import dao.Custom.SupplierDao;
import dao.DaoFactory;
import dto.Supplier;
import service.custom.SupplierService;
import util.DaoType;

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

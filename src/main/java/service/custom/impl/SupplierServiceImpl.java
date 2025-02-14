package service.custom.impl;

import dao.Custom.SupplierDao;
import dao.DaoFactory;
import dto.Supplier;
import entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import service.custom.SupplierService;
import util.DaoType;

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
        List<SupplierEntity> supplierEntities = supplierDao.getAll();
        List<Supplier> suppliersArray = new ArrayList<>();
        supplierEntities.forEach(supplier -> {
            suppliersArray.add(new ModelMapper().map(supplier, Supplier.class));
        });
        return suppliersArray;
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        SupplierEntity supplierEntity = new ModelMapper().map(supplier, SupplierEntity.class);
        return supplierDao.save(supplierEntity);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        SupplierEntity supplierEntity = new ModelMapper().map(supplier, SupplierEntity.class);
        return supplierDao.update(supplierEntity);
    }

    @Override
    public boolean deleteSupplier(int supplierId) {
        return supplierDao.delete(supplierId);
    }

}

package service.custom.impl;

import com.google.inject.Inject;
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

    ModelMapper modelMapper = new ModelMapper();

    @Inject
    SupplierDao supplierDao;

    @Override
    public List<Supplier> getSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            List<SupplierEntity> supplierEntities = supplierDao.getAll();
            for (SupplierEntity entity : supplierEntities) {
                suppliers.add(modelMapper.map(entity, Supplier.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        try {
            SupplierEntity supplierEntity = modelMapper.map(supplier, SupplierEntity.class);
            return supplierDao.save(supplierEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        try {
            SupplierEntity supplierEntity = modelMapper.map(supplier, SupplierEntity.class);
            return supplierDao.update(supplierEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteSupplier(int supplierId) {
        try {
            return supplierDao.delete(supplierId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
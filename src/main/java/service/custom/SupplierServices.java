package service.custom;

import model.Supplier;

import java.util.List;

public interface SupplierServices {
    List<Supplier> getSuppliers();
    boolean addSupplier(Supplier supplier);
    boolean updateSupplier(Supplier supplier);
    boolean deleteSupplier(int supplierId);
}

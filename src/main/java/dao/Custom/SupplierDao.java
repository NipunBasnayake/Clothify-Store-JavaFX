package dao.Custom;

import dao.SuperDao;
import dto.Supplier;

import java.util.List;

public interface SupplierDao extends SuperDao {
    List<Supplier> getSuppliers();
    boolean addSupplier(Supplier supplier);
    boolean updateSupplier(Supplier supplier);
    boolean deleteSupplier(int supplierId);
}

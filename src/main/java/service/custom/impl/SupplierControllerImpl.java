package service.custom.impl;

import db.DBConnection;
import model.Supplier;
import service.custom.SupplierServices;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierControllerImpl implements SupplierServices {
    private static SupplierControllerImpl supplierController;

    public static SupplierControllerImpl getInstance() {
        if (supplierController == null) {
            supplierController = new SupplierControllerImpl();
        }
        return supplierController;
    }

    @Override
    public List<Supplier> getSuppliers() {
        String query = "SELECT * FROM supplier";
        List<Supplier> suppliers = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            return null;
        }
        return suppliers;
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        String query = "INSERT INTO supplier (Name, Company, Email, Item) VALUES (?,?,?,?)";
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }
}

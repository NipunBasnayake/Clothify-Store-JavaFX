package dao.Custom.impl;

import dao.Custom.SupplierDao;
import db.DBConnection;
import model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    private static SupplierDaoImpl supplierDaoImpl;

    public static SupplierDaoImpl getInstance() {
        if (supplierDaoImpl == null) {
            supplierDaoImpl = new SupplierDaoImpl();
        }
        return supplierDaoImpl;
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
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getSupplierCompany());
            statement.setString(3, supplier.getSupplierEmail());
            statement.setString(4, supplier.getSupplyItem());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        String query = "Update supplier SET Name = ?, Company = ?, Email = ?, Item = ? WHERE SupplierID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, supplier.getSupplierName());
            statement.setString(2, supplier.getSupplierCompany());
            statement.setString(3, supplier.getSupplierEmail());
            statement.setString(4, supplier.getSupplyItem());
            statement.setInt(5, supplier.getSupplierId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteSupplier(int supplierId) {
        String query = "DELETE FROM supplier WHERE SupplierID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, supplierId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

}

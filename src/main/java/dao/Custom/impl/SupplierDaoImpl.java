package dao.Custom.impl;

import dao.Custom.SupplierDao;
import db.DBConnection;
import entity.SupplierEntity;

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
    public boolean save(SupplierEntity entity) {
        String query = "INSERT INTO supplier (Name, Company, Email, Item) VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, entity.getSupplierName());
            statement.setString(2, entity.getSupplierCompany());
            statement.setString(3, entity.getSupplierEmail());
            statement.setString(4, entity.getSupplyItem());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public SupplierEntity search(Integer integer) {
        return null;
    }

    @Override
    public boolean delete(Integer supplierId) {
        String query = "DELETE FROM supplier WHERE SupplierID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, supplierId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(SupplierEntity entity) {
        String query = "Update supplier SET Name = ?, Company = ?, Email = ?, Item = ? WHERE SupplierID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, entity.getSupplierName());
            statement.setString(2, entity.getSupplierCompany());
            statement.setString(3, entity.getSupplierEmail());
            statement.setString(4, entity.getSupplyItem());
            statement.setInt(5, entity.getSupplierId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<SupplierEntity> getAll() {
        String query = "SELECT * FROM supplier";
        List<SupplierEntity> suppliers = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                SupplierEntity supplier = new SupplierEntity(
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
}

package dao.Custom.impl;

import dao.Custom.EmployeeDao;
import db.DBConnection;
import entity.EmployeeEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    private static EmployeeDaoImpl employeeDao;

    public static EmployeeDao getInstance() {
        if (employeeDao == null) {
            employeeDao = new EmployeeDaoImpl();
        }
        return employeeDao;
    }

    @Override
    public boolean save(EmployeeEntity entity) {
        String query = "INSERT INTO employee (name, email, role) VALUES (?,?,?)";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, entity.getEmployeeName());
            statement.setString(2, entity.getEmployeeEmail());
            statement.setString(3, entity.getEmployeeRole());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public EmployeeEntity search(String id) {
        String query = "SELECT employeeId, name, email, role FROM employee WHERE employeeId = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new EmployeeEntity(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    );
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM employee WHERE employeeId = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(EmployeeEntity entity) {
        String query = "UPDATE employee SET name = ?, email = ?, role = ? WHERE employeeId = ?";
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, entity.getEmployeeName());
            statement.setString(2, entity.getEmployeeEmail());
            statement.setString(3, entity.getEmployeeRole());
            statement.setInt(4, entity.getEmployeeId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<EmployeeEntity> getAll() {
        String query = "SELECT employeeId, name, email, role FROM employee";
        List<EmployeeEntity> employees = new ArrayList<>();
        try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employees.add(new EmployeeEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return employees;
    }
}

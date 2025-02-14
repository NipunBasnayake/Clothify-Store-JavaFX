package dao.Custom.impl;

import dao.Custom.EmployeeDao;
import db.DBConnection;
import dto.Employee;
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
        try {
            String query = "INSERT INTO employee (Name, Email, Role) VALUES (?,?,?)";
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, entity.getEmployeeName());
            statement.setString(2, entity.getEmployeeEmail());
            statement.setString(3, entity.getEmployeeRole());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public EmployeeEntity search(String id) {
        for (EmployeeEntity employee : getAll()) {
            if (String.valueOf(employee.getEmployeeId()).equals(id)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        String query = "DELETE FROM employee WHERE EmployeeID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(EmployeeEntity entity) {
        String query = "Update employee set Name = ?, Email = ?, Role = ? where EmployeeID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, entity.getEmployeeName());
            statement.setString(2, entity.getEmployeeEmail());
            statement.setString(3, entity.getEmployeeRole());
            statement.setInt(4, entity.getEmployeeId());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<EmployeeEntity> getAll() {
        String query = "SELECT * FROM employee";
        List<EmployeeEntity> employees = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                EmployeeEntity employee = new EmployeeEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                employees.add(employee);
            }
            return employees;
        } catch (SQLException e) {
            return null;
        }
    }
}

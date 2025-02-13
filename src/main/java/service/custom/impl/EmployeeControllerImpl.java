package service.custom.impl;

import db.DBConnection;
import model.Employee;
import service.custom.EmployeeServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeControllerImpl implements EmployeeServices {
    private static EmployeeServices employeeService;

    public static EmployeeServices getInstance() {
        if (employeeService == null) {
            employeeService = new EmployeeControllerImpl();
        }
        return employeeService;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        try {
            String query = "INSERT INTO employee (Name, Email, Role) VALUES (?,?,?)";
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, employee.getEmployeeName());
            statement.setString(2, employee.getEmployeeEmail());
            statement.setString(3, employee.getEmployeeRole());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        String query = "Update employee set Name = ?, Email = ?, Role = ? where EmployeeID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setString(1, employee.getEmployeeName());
            statement.setString(2, employee.getEmployeeEmail());
            statement.setString(3, employee.getEmployeeRole());
            statement.setInt(4, employee.getEmployeeId());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Employee> getEmployees() {
        String query = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                Employee employee = new Employee(
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

    @Override
    public boolean deleteEmployee(int employeeId) {
        String query = "DELETE FROM employee WHERE EmployeeID = ?";
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, employeeId);
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        for (Employee employee : getEmployees()) {
            if (employee.getEmployeeId() == employeeId) {
                return employee;
            }
        }
        return null;
    }
}

package service.custom.impl;

import db.DBConnection;
import model.Employee;
import service.custom.EmployeeServices;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}

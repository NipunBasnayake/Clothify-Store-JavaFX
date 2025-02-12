package service.custom;

import model.Employee;

import java.util.List;

public interface EmployeeServices {
    boolean addEmployee (Employee employee);
    boolean updateEmployee (Employee employee);
    List<Employee> getEmployees();
    boolean deleteEmployee (int employeeId);
}

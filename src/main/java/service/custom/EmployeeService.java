package service.custom;

import model.Employee;
import service.SuperService;

import java.util.List;

public interface EmployeeService extends SuperService {
    boolean addEmployee (Employee employee);
    boolean updateEmployee (Employee employee);
    List<Employee> getEmployees();
    boolean deleteEmployee (int employeeId);
    Employee getEmployeeById (int employeeId);
}

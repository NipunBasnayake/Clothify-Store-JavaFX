package dao.Custom;

import dao.SuperDao;
import dto.Employee;

import java.util.List;

public interface EmployeeDao extends SuperDao {
    boolean addEmployee (Employee employee);
    boolean updateEmployee (Employee employee);
    List<Employee> getEmployees();
    boolean deleteEmployee (int employeeId);
    Employee getEmployeeById (int employeeId);
}

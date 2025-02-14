package service.custom.impl;

import dao.Custom.EmployeeDao;
import dao.DaoFactory;
import dto.Employee;
import service.custom.EmployeeService;
import util.DaoType;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private static EmployeeService employeeService;

    public static EmployeeService getInstance() {
        if (employeeService == null) {
            employeeService = new EmployeeServiceImpl();
        }
        return employeeService;
    }

    EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean addEmployee(Employee employee) {
        return employeeDao.addEmployee(employee);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return employeeDao.updateEmployee(employee);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }

    @Override
    public boolean deleteEmployee(int employeeId) {
        return employeeDao.deleteEmployee(employeeId);
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }
}

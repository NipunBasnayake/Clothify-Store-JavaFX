package service.custom.impl;

import dao.Custom.EmployeeDao;
import dao.DaoFactory;
import dto.Employee;
import entity.EmployeeEntity;
import org.modelmapper.ModelMapper;
import service.custom.EmployeeService;
import util.DaoType;

import java.util.ArrayList;
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
        EmployeeEntity employeeEntity = new ModelMapper().map(employee, EmployeeEntity.class);
        return employeeDao.save(employeeEntity);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new ModelMapper().map(employee, EmployeeEntity.class);
        return employeeDao.update(employeeEntity);
    }

    @Override
    public List<Employee> getEmployees() {
        List<EmployeeEntity> employeeEntities = employeeDao.getAll();
        ArrayList<Employee> employees = new ArrayList<>();
        employeeEntities.forEach(employee -> {
            employees.add(new ModelMapper().map(employee, Employee.class));
        });
        return employees;
    }

    @Override
    public boolean deleteEmployee(int employeeId) {
        return employeeDao.delete(String.valueOf(employeeId));
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        EmployeeEntity employeeEntity = employeeDao.search(String.valueOf(employeeId));
        return new ModelMapper().map(employeeEntity, Employee.class);
    }
}

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
    private static EmployeeServiceImpl employeeServiceImpl;
    private final EmployeeDao employeeDao;
    private final ModelMapper modelMapper;

    private EmployeeServiceImpl() {
        employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
        modelMapper = new ModelMapper();
    }

    public static EmployeeServiceImpl getInstance() {
        if (employeeServiceImpl == null) {
            employeeServiceImpl = new EmployeeServiceImpl();
        }
        return employeeServiceImpl;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        try {
            EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
            return employeeDao.save(employeeEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        try {
            EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
            return employeeDao.update(employeeEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Employee> getEmployees() {
        List<EmployeeEntity> employeeEntities = employeeDao.getAll();
        List<Employee> employees = new ArrayList<>();
        for (EmployeeEntity entity : employeeEntities) {
            employees.add(modelMapper.map(entity, Employee.class));
        }
        return employees;
    }

    @Override
    public boolean deleteEmployee(int employeeId) {
        try {
            return employeeDao.delete(String.valueOf(employeeId));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        try {
            EmployeeEntity employeeEntity = employeeDao.search(String.valueOf(employeeId));
            if (employeeEntity != null) {
                return modelMapper.map(employeeEntity, Employee.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
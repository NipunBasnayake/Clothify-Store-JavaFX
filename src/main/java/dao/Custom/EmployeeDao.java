package dao.Custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.Employee;
import entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends CrudDao<EmployeeEntity, String> {

}

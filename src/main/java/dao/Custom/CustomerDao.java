package dao.Custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.Customer;
import entity.CustomerEntity;

import java.util.List;

public interface CustomerDao extends CrudDao<CustomerEntity,String> {

}

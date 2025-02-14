package dao.Custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.Order;
import entity.OrderEntity;

import java.util.List;

public interface OrderDao extends CrudDao<OrderEntity,String> {
    int getLastOrderId();
}

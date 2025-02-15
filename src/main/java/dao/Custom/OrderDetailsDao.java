package dao.Custom;

import dao.CrudDao;
import dao.SuperDao;
import entity.OrderProductEntity;

import java.util.List;

public interface OrderDetailsDao extends SuperDao {
    boolean save(List<OrderProductEntity> orderProductEntities);
}

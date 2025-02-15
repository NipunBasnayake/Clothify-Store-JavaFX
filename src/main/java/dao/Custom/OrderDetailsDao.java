package dao.Custom;

import dao.SuperDao;
import entity.OrderDetailEntity;

import java.util.List;

public interface OrderDetailsDao extends SuperDao {
    boolean save(List<OrderDetailEntity> orderProductEntities);
    List<OrderDetailEntity> getAll();
}

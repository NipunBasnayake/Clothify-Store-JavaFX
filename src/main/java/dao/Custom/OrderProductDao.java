package dao.Custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.OrderProduct;
import entity.OrderProductEntity;

import java.util.List;

public interface OrderProductDao extends CrudDao<OrderProductEntity, Integer> {
}

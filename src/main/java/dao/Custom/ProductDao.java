package dao.Custom;

import dao.CrudDao;
import dao.SuperDao;
import dto.Product;
import entity.OrderDetailEntity;
import entity.ProductEntity;

import java.util.List;

public interface ProductDao extends CrudDao<ProductEntity, Integer> {
    boolean updateQuantity(List<OrderDetailEntity> entities);
}

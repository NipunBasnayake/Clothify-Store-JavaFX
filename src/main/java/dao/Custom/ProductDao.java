package dao.Custom;

import dao.SuperDao;
import model.Product;

import java.util.List;

public interface ProductDao extends SuperDao {
    List<Product> getProducts();
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(Integer productId);
    Product getProductById(Integer productId);
}

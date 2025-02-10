package service.custom;

import model.Product;

import java.util.List;

public interface ProductServices {
    List<Product> getProducts();
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(Integer productId);
}

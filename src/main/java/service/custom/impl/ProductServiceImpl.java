package service.custom.impl;

import dao.Custom.ProductDao;
import dao.DaoFactory;
import dto.Product;
import entity.ProductEntity;
import org.modelmapper.ModelMapper;
import service.custom.ProductService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static ProductServiceImpl productControllerImpl;

    public static ProductServiceImpl getInstance() {
        if (productControllerImpl == null) {
            productControllerImpl = new ProductServiceImpl();
        }
        return productControllerImpl;
    }

    ProductDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public List<Product> getProducts() {
        List<ProductEntity> productEntities = dao.getAll();
        List<Product> productsArray = new ArrayList<>();
        productEntities.forEach(productEntity -> {
            productsArray.add(new ModelMapper().map(productEntity, Product.class));
        });
        return productsArray;
    }

    @Override
    public boolean addProduct(Product product) {
        ProductEntity productEntity = new ModelMapper().map(product, ProductEntity.class);
        return dao.save(productEntity);
    }

    @Override
    public boolean updateProduct(Product product) {
        ProductEntity productEntity = new ModelMapper().map(product, ProductEntity.class);
        return dao.update(productEntity);
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        return dao.delete(productId);
    }

    @Override
    public Product getProductById(Integer productId) {
        ProductEntity productEntity = dao.search(productId);
        return new ModelMapper().map(productEntity, Product.class);
    }
}

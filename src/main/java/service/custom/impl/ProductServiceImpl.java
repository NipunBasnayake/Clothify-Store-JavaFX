package service.custom.impl;

import com.google.inject.Inject;
import dao.Custom.ProductDao;
import dao.Custom.impl.ProductDaoImpl;
import dao.DaoFactory;
import dto.Product;
import entity.ProductEntity;
import org.modelmapper.ModelMapper;
import service.custom.ProductService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    ModelMapper modelMapper = new ModelMapper();

    ProductDao dao = new ProductDaoImpl();

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try {
            List<ProductEntity> productEntities = dao.getAll();
            for (ProductEntity entity : productEntities) {
                products.add(modelMapper.map(entity, Product.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public boolean addProduct(Product product) {
        try {
            ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
            return dao.save(productEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        try {
            ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
            return dao.update(productEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        try {
            return dao.delete(productId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product getProductById(Integer productId) {
        ProductEntity productEntity = dao.search(productId);
        return modelMapper.map(productEntity, Product.class);
    }
}
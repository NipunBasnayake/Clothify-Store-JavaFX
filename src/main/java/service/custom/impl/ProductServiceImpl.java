package service.custom.impl;

import dao.Custom.ProductDao;
import dao.DaoFactory;
import db.DBConnection;
import model.Product;
import service.custom.ProductService;
import util.DaoType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return dao.getProducts();
    }

    @Override
    public boolean addProduct(Product product) {
        return dao.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        return dao.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(Integer productId) {
        return dao.deleteProduct(productId);
    }

    @Override
    public Product getProductById(Integer productId) {
        return dao.getProductById(productId);
    }
}

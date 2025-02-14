package service.custom.impl;

import dao.Custom.OrderDao;
import dao.Custom.OrderProductDao;
import dao.DaoFactory;
import db.DBConnection;
import model.OrderProduct;
import service.custom.OrderProductService;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductServiceImpl implements OrderProductService {

    private static OrderProductServiceImpl orderProductControllerImpl;

    public static OrderProductServiceImpl getInstance() {
        if (orderProductControllerImpl == null) {
            orderProductControllerImpl = new OrderProductServiceImpl();
        }
        return orderProductControllerImpl;
    }

    OrderProductDao orderProductDao = DaoFactory.getInstance().getDao(DaoType.ORDERPRODUCT);

    @Override
    public List<OrderProduct> getOrderProducts() {
        return orderProductDao.getOrderProducts();
    }


}

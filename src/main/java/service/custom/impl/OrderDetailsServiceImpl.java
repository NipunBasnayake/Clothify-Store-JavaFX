package service.custom.impl;

import dao.Custom.OrderDetailsDao;
import dao.DaoFactory;
import dto.OrderDetails;
import entity.OrderDetailEntity;
import org.modelmapper.ModelMapper;
import service.custom.OrderDetailService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsServiceImpl implements OrderDetailService {
    private static OrderDetailsServiceImpl orderDetailsServiceImpl;
    private final OrderDetailsDao orderDetailsDao;
    private final ModelMapper modelMapper;

    private OrderDetailsServiceImpl() {
        orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDERPRODUCT);
        modelMapper = new ModelMapper();
    }

    public static OrderDetailsServiceImpl getInstance() {
        if (orderDetailsServiceImpl == null) {
            orderDetailsServiceImpl = new OrderDetailsServiceImpl();
        }
        return orderDetailsServiceImpl;
    }

    @Override
    public List<OrderDetails> getOrderProducts() {
        List<OrderDetails> orderDetails = new ArrayList<>();
        try {
            List<OrderDetailEntity> orderDetailEntities = orderDetailsDao.getAll();
            for (OrderDetailEntity entity : orderDetailEntities) {
                orderDetails.add(modelMapper.map(entity, OrderDetails.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
}
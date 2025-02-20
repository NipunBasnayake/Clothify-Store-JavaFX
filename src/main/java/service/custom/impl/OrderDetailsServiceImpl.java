package service.custom.impl;

import com.google.inject.Inject;
import dao.Custom.OrderDetailsDao;
import dao.DaoFactory;
import dto.OrderDetails;
import entity.OrderDetailEntity;
import org.modelmapper.ModelMapper;
import service.custom.OrderDetailsService;
import util.DaoType;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsServiceImpl implements OrderDetailsService {

    ModelMapper modelMapper = new ModelMapper();

    @Inject
    OrderDetailsDao orderDetailsDao;

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
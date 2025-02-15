package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEntity {
    private int orderId;
    private Date orderDate;
    private Double totalPrice;
    private String paymentMethod;
    private int userId;
    private int customerId;
    private List<OrderDetailEntity> orderDetailEntityList;
}

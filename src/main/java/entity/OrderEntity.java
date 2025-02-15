package entity;

import dto.Product;
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
    private int employeeId;
    private int customerId;
    private List<ProductEntity> productEntities;
}
